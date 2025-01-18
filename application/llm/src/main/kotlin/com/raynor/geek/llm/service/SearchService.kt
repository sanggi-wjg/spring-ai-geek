package com.raynor.geek.llm.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.raynor.geek.client.naver.NaverOpenClient
import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.client.tavily.TavilyClient
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import com.raynor.geek.llm.repository.GeekVectorRepository
import com.raynor.geek.llm.service.document.NaverNewsDocumentConverter
import com.raynor.geek.llm.service.document.TavilyDocumentConverter
import com.raynor.geek.llm.service.document.toPrompt
import com.raynor.geek.llm.service.factory.OllamaOptionsFactory
import com.raynor.geek.rds.entity.SearchEntity
import com.raynor.geek.rds.repository.SearchRdsRepository
import com.raynor.geek.shared.enums.SearchFrom
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class SearchService(
    private val objectMapper: ObjectMapper,
    private val searchRdsRepository: SearchRdsRepository,
    private val geekVectorRepository: GeekVectorRepository,
    private val tavilyDocumentConverter: TavilyDocumentConverter,
    private val naverNewsDocumentConverter: NaverNewsDocumentConverter,
    private val tavilyClient: TavilyClient,
    private val naverOpenClient: NaverOpenClient,
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/rag-search-summarize.st")
    lateinit var summarizePrompt: Resource

    @Transactional
    fun search(query: String): ChatResponse {
        val tavilySearchResponse = tavilyClient.search(query).getOrThrow()
        saveTavilySearchResponse(tavilySearchResponse)

        val naverSearchResponse = naverOpenClient.searchNews(query).getOrThrow()
        saveNaverSearchResponse(query, naverSearchResponse)

        val naverDocuments = naverNewsDocumentConverter.convert(naverSearchResponse)
        val tavilyDocuments = tavilyDocumentConverter.convert(tavilySearchResponse)
        val documents = naverDocuments + tavilyDocuments
        geekVectorRepository.addDocuments(documents)

        val prompt = PromptTemplate(
            summarizePrompt,
            mapOf(
                "documents" to documents.toPrompt(),
                "question" to query
            ),
        ).create(
            OllamaOptionsFactory.ragWithExaone35()
        )
        return llm.call(prompt)
    }

    fun searchFromVector(query: String): ChatResponse {
        val documents = geekVectorRepository.similaritySearch(query)

        val prompt = PromptTemplate(
            summarizePrompt,
            mapOf("documents" to documents.toPrompt())
        ).create()
        return llm.call(prompt)
    }

    private fun saveTavilySearchResponse(
        searchResponse: TavilySearchResponseDto,
    ): SearchEntity {
        return searchRdsRepository.save(
            SearchEntity(
                query = searchResponse.query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchFrom = SearchFrom.TAVILY_API,
                createdAt = Instant.now(),
            )
        )
    }

    private fun saveNaverSearchResponse(
        query: String,
        searchResponse: NaverNewsResponseDto,
    ): SearchEntity {
        return searchRdsRepository.save(
            SearchEntity(
                query = query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchFrom = SearchFrom.NAVER_OPEN_API,
                createdAt = Instant.now(),
            )
        )
    }
}
