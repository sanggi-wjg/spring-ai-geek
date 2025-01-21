package com.raynor.geek.client.naver.exception


class NaverOpenAPISearchNewsException(
    message: String = "Naver api search failed",
    cause: Throwable,
) : RuntimeException(message, cause)
