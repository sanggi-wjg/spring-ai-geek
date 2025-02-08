package com.raynor.geek.llmservice.service

import com.raynor.geek.llmservice.model.LlmParameter
import com.raynor.geek.llmservice.model.toOllamaOptions
import com.raynor.geek.llmservice.service.factory.PromptFactory
import com.raynor.geek.rds.repository.TradeStatsRdsRepository
import com.raynor.geek.shared.enums.OllamaCustomModel
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux


@Service
class AnalyzeTradeStatsService(
    private val llm: OllamaChatModel,
    private val tradeStatsRdsRepository: TradeStatsRdsRepository,
) {
    @Value("classpath:prompts/searching/system-basic.st")
    lateinit var systemBasicTemplate: Resource

    @Value("classpath:prompts/searching/user-basic.st")
    lateinit var userBasicTemplate: Resource

    @Transactional(readOnly = true)
    fun analyzeTradeStats(): Flux<String> {
        val tradeStats = tradeStatsRdsRepository.findSomething()
        val documents = tradeStats.map {
            "Country:${it.country.name} Month:${it.tradeStats.month} HsCode:${it.tradeStats.hsCode} ItemDescription${it.tradeStats.description} " +
                    "ImportAmount:${it.tradeStats.importAmount} ImportWeight:${it.tradeStats.importWeight} ExportAmount:${it.tradeStats.exportAmount} ExportWeight:${it.tradeStats.exportWeight}"
        }

        val prompt = PromptFactory.create(
            ollamaOptions = LlmParameter(model = OllamaCustomModel.EXAONE_3_5_8b).toOllamaOptions(),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf(
                "documents" to documents,
            )
        )
        return llm.stream(prompt).map {
            it.results.first().output.text
        }
    }
}