package com.raynor.geek.llmservice.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.raynor.geek.client.tavily.TavilyClient
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import com.raynor.geek.client.tavily.exception.TavilyAPIException
import com.raynor.geek.llmservice.service.document.DocumentConverter
import com.raynor.geek.rds.entity.DocumentEntity
import com.raynor.geek.rds.entity.SearchApiHistoryEntity
import com.raynor.geek.rds.repository.SearchApiHistoryRdsRepository
import com.raynor.geek.shared.enums.SearchAPIType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class TavilyDocumentService(
    private val tavilyClient: TavilyClient,
    private val searchApiHistoryRdsRepository: SearchApiHistoryRdsRepository,
    private val objectMapper: ObjectMapper,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = [TavilyAPIException::class])
    fun loadWeb(query: String): List<DocumentEntity> {
        return tavilyClient.searchWeb(query).getOrThrow().let {
            saveSearchResponse(it)
            DocumentConverter.convert(it)
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = [TavilyAPIException::class])
    fun loadNews(query: String): List<DocumentEntity> {
        return tavilyClient.searchNews(query).getOrThrow().let {
            saveSearchResponse(it)
            DocumentConverter.convert(it)
        }
    }

    private fun saveSearchResponse(searchResponse: TavilySearchResponseDto) {
        searchApiHistoryRdsRepository.save(
            SearchApiHistoryEntity(
                query = searchResponse.query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchAPIType = SearchAPIType.TAVILY_API,
                createdAt = Instant.now(),
            )
        )
    }
}