package com.raynor.geek.api.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.raynor.geek.api.repository.GeekVectorRepository
import com.raynor.geek.api.service.document.TavilyDocumentConverter
import com.raynor.geek.api.service.document.toPrompt
import com.raynor.geek.api.service.factory.OllamaOptionsFactory
import com.raynor.geek.client.tavily.TavilyClient
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import com.raynor.geek.rds.entity.SearchEntity
import com.raynor.geek.rds.enum.SearchFrom
import com.raynor.geek.rds.repository.SearchRdsRepository
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
    private val searchRdsRepository: SearchRdsRepository,
    private val objectMapper: ObjectMapper,
    private val geekVectorRepository: GeekVectorRepository,
    private val tavilyDocumentConverter: TavilyDocumentConverter,
    private val tavilyClient: TavilyClient,
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/rag-search-summarize.st")
    lateinit var summarizePrompt: Resource

    @Transactional
    fun search(query: String): ChatResponse {
        val searchResponse = tavilyClient.search(query).getOrThrow()
        saveSearchResponse(searchResponse)

        val documents = tavilyDocumentConverter.convert(searchResponse)
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

    private fun saveSearchResponse(searchResponse: TavilySearchResponseDto) {
        searchRdsRepository.save(
            SearchEntity(
                query = searchResponse.query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchFrom = SearchFrom.TAVILY,
                createdAt = Instant.now(),
            )
        )
    }
}
