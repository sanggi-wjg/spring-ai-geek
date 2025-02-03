package com.raynor.geek.client.naver

import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.client.naver.dto.NaverSearchResponseDto
import com.raynor.geek.client.naver.exception.NaverOpenAPIException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NaverOpenClient(
    private val naverOpenAPIProperty: NaverOpenAPIProperty,
    private val naverOpenAPI: NaverOpenAPI,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun searchWeb(
        query: String,
        display: Int = 5,
        start: Int = 1,
    ): Result<NaverSearchResponseDto> {
        check(query.isNotBlank()) { "query must not be blank" }
        check(display in 10..100) { "display must be between 10 and 100" }
        check(start in 1..100) { "start must be between 1 and 100" }

        return runCatching {
            naverOpenAPI.searchGeneral(
                clientId = naverOpenAPIProperty.clientId,
                clientSecret = naverOpenAPIProperty.clientSecret,
                query = query
            )
        }.onSuccess {
            logger.debug("Naver api searchWeb: {}", it)
        }.onFailure {
            logger.error("Naver api searchWeb failed", it)
            throw NaverOpenAPIException(cause = it)
        }
    }

    fun searchNews(
        query: String,
        display: Int = 5,
        start: Int = 1,
        sort: String = "sim",
    ): Result<NaverNewsResponseDto> {
        check(query.isNotBlank()) { "query must not be blank" }
        check(display in 10..100) { "display must be between 10 and 100" }
        check(start in 1..100) { "start must be between 1 and 100" }
        check(sort in setOf("sim", "date")) { "sort must be sim or date" }

        return runCatching {
            naverOpenAPI.searchNews(
                clientId = naverOpenAPIProperty.clientId,
                clientSecret = naverOpenAPIProperty.clientSecret,
                query = query
            )
        }.onSuccess {
            logger.debug("Naver api searchNews: {}", it)
        }.onFailure {
            logger.error("Naver api searchNews failed", it)
            throw NaverOpenAPIException(cause = it)
        }
    }
}