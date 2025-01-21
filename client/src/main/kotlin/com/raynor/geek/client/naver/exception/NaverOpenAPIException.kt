package com.raynor.geek.client.naver.exception


class NaverOpenAPIException(
    message: String = "Naver Open API failed",
    cause: Throwable,
) : RuntimeException(message, cause)
