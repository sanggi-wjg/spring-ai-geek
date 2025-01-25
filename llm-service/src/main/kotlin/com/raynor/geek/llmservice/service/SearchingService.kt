package com.raynor.geek.llmservice.service

import com.raynor.geek.llmservice.model.LlmParameter
import com.raynor.geek.llmservice.model.toOllamaOptions
import com.raynor.geek.llmservice.repository.GeekVectorRepository
import com.raynor.geek.llmservice.service.document.toDocument
import com.raynor.geek.llmservice.service.document.toFlattenString
import com.raynor.geek.llmservice.service.factory.PromptFactory
import com.raynor.geek.rds.repository.DocumentRdsRepository
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchingService(
    private val documentRdsRepository: DocumentRdsRepository,
    private val geekVectorRepository: GeekVectorRepository,
    private val naverDocumentService: NaverDocumentService,
    private val tavilyDocumentService: TavilyDocumentService,
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/searching/system-basic.st")
    lateinit var systemBasicTemplate: Resource

    @Value("classpath:prompts/searching/user-basic.st")
    lateinit var userBasicTemplate: Resource

    @Transactional
    fun searchWeb(query: String, llmParameter: LlmParameter): ChatResponse {
        val documents = tavilyDocumentService.loadWeb(query).let {
            documentRdsRepository.saveAll(it).toDocument()
        } + naverDocumentService.loadWeb(query).let {
            documentRdsRepository.saveAll(it).toDocument()
        }
        geekVectorRepository.addDocuments(documents)

        val prompt = PromptFactory.create(
            ollamaOptions = llmParameter.toOllamaOptions(),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("documents" to documents.toFlattenString()),
        )
        return llm.call(prompt)
    }

    @Transactional
    fun searchNews(query: String, llmParameter: LlmParameter): ChatResponse {
        val documents = tavilyDocumentService.loadNews(query).let {
            documentRdsRepository.saveAll(it).toDocument()
        } + naverDocumentService.loadNews(query).let {
            documentRdsRepository.saveAll(it).toDocument()
        }
        geekVectorRepository.addDocuments(documents)

        val prompt = PromptFactory.create(
            ollamaOptions = llmParameter.toOllamaOptions(),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("documents" to documents.toFlattenString()),
        )
        return llm.call(prompt)
    }

    fun searchVector(query: String, llmParameter: LlmParameter): ChatResponse {
        val documents = geekVectorRepository.similaritySearch(query, topK = 5)
        val prompt = PromptFactory.create(
            ollamaOptions = llmParameter.toOllamaOptions(),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("documents" to documents.toFlattenString()),
        )
        return llm.call(prompt)
    }
}
