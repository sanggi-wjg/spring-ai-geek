package com.raynor.geek.llm.service.document

import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import com.raynor.geek.shared.enums.DocumentMetadataKey
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
            put(DocumentMetadataKey.TITLE.value, title)
            put(DocumentMetadataKey.URL.value, url)
            publishedDate?.let { put(DocumentMetadataKey.PUBLISHED_DATE.value, it) }
        }
    }
}
