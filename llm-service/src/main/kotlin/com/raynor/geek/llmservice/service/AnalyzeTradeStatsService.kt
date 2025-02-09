package com.raynor.geek.llmservice.service

import com.raynor.geek.client.slack.SlackClient
import com.raynor.geek.client.slack.dto.SlackWebHookMessageFactory
import com.raynor.geek.llmservice.model.LlmParameter
import com.raynor.geek.llmservice.model.toOllamaOptions
import com.raynor.geek.llmservice.service.factory.PromptFactory
import com.raynor.geek.rds.condition.TradeStatsSearchCondition
import com.raynor.geek.rds.repository.TradeStatsRdsRepository
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class AnalyzeTradeStatsService(
    private val llm: OllamaChatModel,
    private val tradeStatsRdsRepository: TradeStatsRdsRepository,
    private val slackClient: SlackClient,
) {
    @Value("classpath:prompts/searching/system-basic.st")
    lateinit var systemBasicTemplate: Resource

    @Value("classpath:prompts/searching/user-basic.st")
    lateinit var userBasicTemplate: Resource

    @Transactional(readOnly = true)
    fun analyzeTradeStats(
        llmParameter: LlmParameter,
        countryAlpha2: String,
        hsCode2: String?,
        hsCode4: String?,
    ) {
        val tradeStats = tradeStatsRdsRepository.findAllByCondition(
            searchCondition = TradeStatsSearchCondition(
                countryAlpha2 = countryAlpha2,
                hsCode2 = hsCode2,
                hsCode4 = hsCode4,
            )
        )
        val documents = tradeStats.map {
            "Country:${it.country.name} Month:${it.tradeStats.month} HsCode:${it.tradeStats.hsCode} ItemDescription${it.tradeStats.description} " +
                    "ImportAmount:${it.tradeStats.importAmount} ImportWeight:${it.tradeStats.importWeight} ExportAmount:${it.tradeStats.exportAmount} ExportWeight:${it.tradeStats.exportWeight}\n"
        }

        val prompt = PromptFactory.create(
            ollamaOptions = llmParameter.toOllamaOptions(),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf(
                "documents" to documents,
            )
        )
        val chatResponse = llm.call(prompt)
        val message = SlackWebHookMessageFactory.tradeStats(chatResponse.result.output.text)
        slackClient.sendWebHook(message)
    }
}