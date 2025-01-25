package com.raynor.geek.llmservice.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.raynor.geek.client.naver.NaverOpenClient
import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.client.naver.dto.NaverSearchResponseDto
import com.raynor.geek.client.naver.exception.NaverOpenAPIException
import com.raynor.geek.llmservice.service.document.DocumentConverter
import com.raynor.geek.rds.entity.DocumentEntity
import com.raynor.geek.rds.entity.SearchAPIHistoryEntity
import com.raynor.geek.rds.repository.SearchHistoryRdsRepository
import com.raynor.geek.shared.enums.SearchAPIType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class NaverDocumentService(
    private val naverOpenClient: NaverOpenClient,
    private val searchHistoryRdsRepository: SearchHistoryRdsRepository,
    private val objectMapper: ObjectMapper,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = [NaverOpenAPIException::class])
    fun loadWeb(query: String): List<DocumentEntity> {
        return naverOpenClient.searchWeb(query).getOrThrow().let {
            saveSearchResponse(query, it)
            DocumentConverter.convert(it)
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = [NaverOpenAPIException::class])
    fun loadNews(query: String): List<DocumentEntity> {
        return naverOpenClient.searchNews(query).getOrThrow().let {
            saveSearchResponse(query, it)
            DocumentConverter.convert(it)
        }
    }

    private fun saveSearchResponse(query: String, searchResponse: NaverSearchResponseDto) {
        searchHistoryRdsRepository.save(
            SearchAPIHistoryEntity(
                query = query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchAPIType = SearchAPIType.NAVER_OPEN_API,
                createdAt = Instant.now(),
            )
        )
    }

    private fun saveSearchResponse(query: String, searchResponse: NaverNewsResponseDto) {
        searchHistoryRdsRepository.save(
            SearchAPIHistoryEntity(
                query = query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchAPIType = SearchAPIType.NAVER_OPEN_API,
                createdAt = Instant.now(),
            )
        )
    }
}