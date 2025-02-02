package com.raynor.geek.client.slack.dto

import com.raynor.geek.shared.enums.DateTimePattern
import com.raynor.geek.shared.utils.DateUtil

object SlackWebHookMessageFactory {
    // https://app.slack.com/block-kit-builder/

    fun newsLetter(text: String): SlackWebHookRequestDto {
        val time = DateUtil.getCurrentDateTimeTextWith(DateTimePattern.YYYY_MM_DD_HH_MM)
        val texts = text.split("\n")
            .filter { it.isNotBlank() }
            .map { it.replace("**", "*") }

        return SlackWebHookRequestDto.builder()
            .addHeader(type = SlackMessageType.PLAIN_TEXT, text = "🤖  AI-Geek NewsLetter  🤖", emoji = true)
            .addContext(type = SlackMessageType.MARKDOWN, text = "*$time  | Spring-AI-Geek*")
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
