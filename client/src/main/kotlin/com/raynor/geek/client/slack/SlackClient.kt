package com.raynor.geek.client.slack

import com.raynor.geek.client.slack.dto.SlackWebHookRequestDto
import com.raynor.geek.client.slack.exception.SlackAPIException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SlackClient(
    private val slackAPI: SlackAPI,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendWebHook(message: SlackWebHookRequestDto) {
        kotlin.runCatching {
            slackAPI.sendWebHook(message)
        }.onSuccess {
            logger.debug("Slack api sent webhook with this message: {}", message)
        }.onFailure {
            throw SlackAPIException("Slack api sendWebHook failed", it)
        }
    }
}
