package com.raynor.geek.client.datagokr.exception

class DataGoKrAPIException(
    message: String = "DataGoKr API failed",
    cause: Throwable,
) : RuntimeException(message, cause)
