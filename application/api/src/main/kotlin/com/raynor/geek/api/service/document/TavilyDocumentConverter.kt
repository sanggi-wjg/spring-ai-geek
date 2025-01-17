package com.raynor.geek.api.service.document

import com.raynor.geek.api.enum.DocumentMetadata
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.stereotype.Component

@Component
class TavilyDocumentConverter {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun convert(searchResponse: TavilySearchResponseDto): List<Document> {
        return searchResponse.results.map { result ->
            Document.builder()
                .text(result.content)
                .score(result.score.toDouble())
                .metadata(result.buildMetadata())
                .build()
        }
    }

    private fun TavilySearchResponseDto.Result.buildMetadata(): Map<String, Any> {
        return buildMap {
            put(DocumentMetadata.TITLE.key, title)
            put(DocumentMetadata.URL.key, url)
            publishedDate?.let { put(DocumentMetadata.PUBLISHED_DATE.key, it) }
        }
    }
}
