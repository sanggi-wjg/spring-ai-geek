package com.raynor.geek.client.slack.dto

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object SlackWebHookMessageFactory {
    // https://app.slack.com/block-kit-builder/

    fun newsLetter(text: String): SlackWebHookRequestDto {
        // todo: to DateUtils
        val now = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))
        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formattedTime = now.format(formatter)

        val texts = text.split("\n")
            .filter { it.isNotBlank() }
            .map { it.replace("**", "*") }

        return SlackWebHookRequestDto.builder()
            .addHeader(type = SlackMessageType.PLAIN_TEXT, text = "🤖  AI-Geek NewsLetter  🤖", emoji = true)
            .addContext(type = SlackMessageType.MARKDOWN, text = "*$formattedTime  | Spring-AI-Geek*")
            .addDivider()
            .apply {
                texts.forEach {
                    addSection(type = SlackMessageType.MARKDOWN, text = it)
                }
            }
            .addDivider()
            .build()
    }
}