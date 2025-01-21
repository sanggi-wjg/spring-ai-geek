package com.raynor.geek.client.tavily.exception

class TavilyAPIException(
    message: String = "Tavily API failed",
    cause: Throwable,
) : RuntimeException(message, cause)
