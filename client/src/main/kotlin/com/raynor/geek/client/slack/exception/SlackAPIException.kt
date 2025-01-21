package com.raynor.geek.client.slack.exception

class SlackAPIException(
    message: String = "Slack API failed",
    cause: Throwable,
) : RuntimeException(message, cause)
