package com.raynor.geek.client.slack.dto

enum class SlackMessageType(val type: String) {
    PLAIN_TEXT("plain_text"),
    MARKDOWN("mrkdwn");
}