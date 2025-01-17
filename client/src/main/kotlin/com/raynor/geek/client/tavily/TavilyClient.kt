package com.raynor.geek.client.tavily

import com.raynor.geek.client.tavily.dto.TavilySearchRequestDto
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
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

    fun search(query: String): Result<TavilySearchResponseDto> {
        return runCatching {
            val request = TavilySearchRequestDto(
                apiKey = tavilyProperty.key,
                query = query,
                maxResults = 10,
                includeImages = false,
                includeImagesDescriptions = false,
            )
            tavilyAPI.search(request)

        }.onFailure {
            logger.error("Tavily api search failed", it)
        }
    }
}