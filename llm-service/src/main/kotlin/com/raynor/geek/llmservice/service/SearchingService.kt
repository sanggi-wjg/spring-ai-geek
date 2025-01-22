package com.raynor.geek.llmservice.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.raynor.geek.client.naver.NaverOpenClient
import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.client.naver.dto.NaverSearchResponseDto
import com.raynor.geek.client.tavily.TavilyClient
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import com.raynor.geek.llmservice.model.OllamaLLMArgument
import com.raynor.geek.llmservice.repository.GeekVectorRepository
import com.raynor.geek.llmservice.service.document.NaverNewsDocumentConverter
import com.raynor.geek.llmservice.service.document.TavilyDocumentConverter
import com.raynor.geek.llmservice.service.document.toFlattenString
import com.raynor.geek.llmservice.service.factory.OllamaOptionFactory
import com.raynor.geek.llmservice.service.factory.PromptFactory
import com.raynor.geek.rds.entity.SearchAPIHistoryEntity
import com.raynor.geek.rds.repository.SearchHistoryRdsRepository
import com.raynor.geek.shared.enums.SearchAPIType
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class SearchingService(
    private val objectMapper: ObjectMapper,
    private val searchHistoryRdsRepository: SearchHistoryRdsRepository,
    private val geekVectorRepository: GeekVectorRepository,
    private val tavilyDocumentConverter: TavilyDocumentConverter,
    private val naverNewsDocumentConverter: NaverNewsDocumentConverter,
    private val tavilyClient: TavilyClient,
    private val naverOpenClient: NaverOpenClient,
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/searching/system-basic.st")
    lateinit var systemBasicTemplate: Resource

    @Value("classpath:prompts/searching/user-basic.st")
    lateinit var userBasicTemplate: Resource

    @Transactional
    fun searchWeb(query: String, llmArgument: OllamaLLMArgument): ChatResponse {
        // todo refactor
        val tavilySearchResponse = tavilyClient.searchWeb(query).getOrThrow()
        saveTavilySearchResponse(tavilySearchResponse)

        val naverSearchResponse = naverOpenClient.searchWeb(query).getOrThrow()
        saveNaverSearchResponse(query, naverSearchResponse)

        val naverDocuments = naverNewsDocumentConverter.convert(naverSearchResponse)
        val tavilyDocuments = tavilyDocumentConverter.convert(tavilySearchResponse)
        val documents = naverDocuments + tavilyDocuments
        geekVectorRepository.addDocuments(documents)

        val prompt = PromptFactory.create(
            ollamaOptions = OllamaOptionFactory.create(llmArgument),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("documents" to documents.toFlattenString()),
        )
        return llm.call(prompt)
    }

    @Transactional
    fun searchNews(query: String, llmArgument: OllamaLLMArgument): ChatResponse {
        val tavilySearchResponse = tavilyClient.searchNews(query).getOrThrow()
        saveTavilySearchResponse(tavilySearchResponse)

        val naverSearchResponse = naverOpenClient.searchNews(query).getOrThrow()
        saveNaverSearchResponse(query, naverSearchResponse)

        val naverDocuments = naverNewsDocumentConverter.convert(naverSearchResponse)
        val tavilyDocuments = tavilyDocumentConverter.convert(tavilySearchResponse)
        val documents = naverDocuments + tavilyDocuments
        geekVectorRepository.addDocuments(documents)

        val prompt = PromptFactory.create(
            ollamaOptions = OllamaOptionFactory.create(llmArgument),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("documents" to documents.toFlattenString()),
        )
        return llm.call(prompt)
    }

    fun searchVector(query: String, llmArgument: OllamaLLMArgument): ChatResponse {
        val documents = geekVectorRepository.similaritySearch(query, topK = 5)
        val prompt = PromptFactory.create(
            ollamaOptions = OllamaOptionFactory.create(llmArgument),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("documents" to documents.toFlattenString()),
        )
        return llm.call(prompt)
    }

    private fun saveTavilySearchResponse(searchResponse: TavilySearchResponseDto): SearchAPIHistoryEntity {
        return searchHistoryRdsRepository.save(
            SearchAPIHistoryEntity(
                query = searchResponse.query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchAPIType = SearchAPIType.TAVILY_API,
                createdAt = Instant.now(),
            )
        )
    }

    private fun saveNaverSearchResponse(query: String, searchResponse: NaverNewsResponseDto): SearchAPIHistoryEntity {
        return searchHistoryRdsRepository.save(
            SearchAPIHistoryEntity(
                query = query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchAPIType = SearchAPIType.NAVER_OPEN_API,
                createdAt = Instant.now(),
            )
        )
    }

    private fun saveNaverSearchResponse(query: String, searchResponse: NaverSearchResponseDto): SearchAPIHistoryEntity {
        return searchHistoryRdsRepository.save(
            SearchAPIHistoryEntity(
                query = query,
                responseData = objectMapper.convertValue(searchResponse, object : TypeReference<Map<String, Any>>() {}),
                searchAPIType = SearchAPIType.NAVER_OPEN_API,
                createdAt = Instant.now(),
            )
        )
    }
}
