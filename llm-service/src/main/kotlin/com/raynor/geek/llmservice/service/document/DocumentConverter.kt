package com.raynor.geek.llmservice.service.document

import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.client.naver.dto.NaverSearchResponseDto
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import com.raynor.geek.rds.entity.DocumentEntity
import com.raynor.geek.shared.enums.DocumentMetadataKey
import java.time.Instant

object DocumentConverter {

    fun convert(searchResponse: NaverSearchResponseDto): List<DocumentEntity> {
        val now = Instant.now()
        return searchResponse.items.map { item ->
            DocumentEntity(
                content = item.description,
                metadata = item.buildMetadata(),
                createdAt = now
            )
        }
    }

    fun convert(searchResponse: NaverNewsResponseDto): List<DocumentEntity> {
        val now = Instant.now()
        return searchResponse.items.map { item ->
            DocumentEntity(
                content = item.description,
                metadata = item.buildMetadata(),
                createdAt = now
            )
        }
    }

    fun convert(searchResponse: TavilySearchResponseDto): List<DocumentEntity> {
        val now = Instant.now()
        return searchResponse.results.map { item ->
            DocumentEntity(
                content = item.content,
                metadata = item.buildMetadata(),
                createdAt = now
            )
        }
    }

    private fun NaverSearchResponseDto.Item.buildMetadata(): Map<String, Any> {
        return buildMap {
            put(DocumentMetadataKey.URL.value, link)
            put(DocumentMetadataKey.TITLE.value, title)
        }
    }

    private fun NaverNewsResponseDto.Item.buildMetadata(): Map<String, Any> {
        return buildMap {
            put(DocumentMetadataKey.URL.value, link)
            put(DocumentMetadataKey.TITLE.value, title)
            put(DocumentMetadataKey.PUBLISHED_DATE.value, pubDate)
        }
    }

    private fun TavilySearchResponseDto.Result.buildMetadata(): Map<String, Any> {
        return buildMap {
            put(DocumentMetadataKey.URL.value, url)
            put(DocumentMetadataKey.TITLE.value, title)
            publishedDate?.let { put(DocumentMetadataKey.PUBLISHED_DATE.value, it) }
        }
    }
}