package com.raynor.geek.llm.service.document

import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.shared.enums.DocumentMetadataKey
import org.springframework.ai.document.Document
import org.springframework.stereotype.Component

@Component
class NaverNewsDocumentConverter {

    fun convert(searchResponse: NaverNewsResponseDto): List<Document> {
        return searchResponse.items.map { item ->
            Document.builder()
                .text(item.description)
                .metadata(item.buildMetadata())
                .build()
        }
    }

    private fun NaverNewsResponseDto.NaverNewsItem.buildMetadata(): Map<String, Any> {
        return buildMap {
            put(DocumentMetadataKey.URL.value, link)
            put(DocumentMetadataKey.TITLE.value, title)
            put(DocumentMetadataKey.PUBLISHED_DATE.value, pubDate)
        }
    }
}