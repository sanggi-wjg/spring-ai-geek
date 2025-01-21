package com.raynor.geek.client.naver

import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.client.naver.exception.NaverOpenAPISearchNewsException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NaverOpenClient(
    private val naverOpenAPIProperty: NaverOpenAPIProperty,
    private val naverOpenAPI: NaverOpenAPI,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun searchNews(
        query: String,
        display: Int = 10,
        start: Int = 1,
        sort: String = "sim",
    ): Result<NaverNewsResponseDto> {
        assert(query.isNotBlank()) { "query must not be blank" }
        assert(display in 10..100) { "display must be between 10 and 100" }
        assert(start in 1..100) { "start must be between 1 and 100" }
        assert(sort in setOf("sim", "date")) { "sort must be sim or date" }

        return runCatching {
            naverOpenAPI.searchNews(
                clientId = naverOpenAPIProperty.clientId,
                clientSecret = naverOpenAPIProperty.clientSecret,
                query = query
            )
        }.onSuccess {
            logger.debug("Naver api search: {}", it)
        }.onFailure {
            logger.error("Naver api search failed", it)
            throw NaverOpenAPISearchNewsException(cause = it)
        }
    }
}