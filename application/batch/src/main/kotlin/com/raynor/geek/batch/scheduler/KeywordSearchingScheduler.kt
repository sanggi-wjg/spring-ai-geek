package com.raynor.geek.batch.scheduler

import com.raynor.geek.client.slack.SlackClient
import com.raynor.geek.client.slack.dto.SlackWebHookMessageFactory
import com.raynor.geek.llmservice.model.OllamaLLMArgument
import com.raynor.geek.llmservice.service.SearchingService
import com.raynor.geek.shared.enums.OllamaMyModel
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class KeywordSearchingScheduler(
    private val slackClient: SlackClient,
    private val searchingService: SearchingService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(initialDelay = 10_000, fixedDelay = 600_000)
    fun run() {
        logger.info("[SearchHistoryScheduler] Start")

        val chatResponse = searchingService.searchWeb(
            query = "애플 주식",
            llmArgument = OllamaLLMArgument(model = OllamaMyModel.EXAONE_3_5_8b, temperature = 0.1)
        )
        val message = SlackWebHookMessageFactory.newsLetter(chatResponse.result.output.text)
        slackClient.sendWebHook(message)

        logger.info("[SearchHistoryScheduler] Finished")
    }
}