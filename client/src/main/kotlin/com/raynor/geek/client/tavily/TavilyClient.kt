package com.raynor.geek.client.tavily

import com.raynor.geek.client.tavily.dto.TavilySearchRequestDto
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import com.raynor.geek.client.tavily.exception.TavilyAPIException
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@EnableConfigurationProperties(TavilyProperty::class)
@Component
class TavilyClient(
    private val tavilyProperty: TavilyProperty,
    private val tavilyAPI: TavilyAPI,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun searchWeb(query: String): Result<TavilySearchResponseDto> {
        assert(query.isNotBlank()) { "query must not be blank" }

        return runCatching {
            val request = TavilySearchRequestDto(
                apiKey = tavilyProperty.key,
                query = query,
                topic = "general",
                maxResults = 5,
                includeImages = false,
                includeImagesDescriptions = false,
            )
            tavilyAPI.search(request)

        }.onSuccess {
            logger.debug("Tavily api searchWeb: {}", it)
        }.onFailure {
            logger.error("Tavily api searchWeb failed", it)
            throw TavilyAPIException(cause = it)
        }
    }

    fun searchNews(query: String): Result<TavilySearchResponseDto> {
        assert(query.isNotBlank()) { "query must not be blank" }

        return runCatching {
            val request = TavilySearchRequestDto(
                apiKey = tavilyProperty.key,
                query = query,
                topic = "news",
                maxResults = 5,
                includeImages = false,
                includeImagesDescriptions = false,
            )
            tavilyAPI.search(request)

        }.onSuccess {
            logger.debug("Tavily api searchNews: {}", it)
        }.onFailure {
            logger.error("Tavily api searchNews failed", it)
            throw TavilyAPIException(cause = it)
        }
    }
}