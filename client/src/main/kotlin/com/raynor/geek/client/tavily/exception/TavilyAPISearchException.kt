package com.raynor.geek.client.tavily.exception

class TavilyAPISearchException(
    message: String = "Tavily api search failed",
    cause: Throwable,
) : RuntimeException(message, cause)
