package com.raynor.geek.api.service

import com.raynor.geek.api.document.TavilyDocumentConverter
import com.raynor.geek.api.document.toPrompt
import com.raynor.geek.api.repository.GeekVectorRepository
import com.raynor.geek.client.tavily.TavilyClient
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val geekVectorRepository: GeekVectorRepository,
    private val tavilyDocumentConverter: TavilyDocumentConverter,
    private val tavilyClient: TavilyClient,
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/rag-search-summarize.st")
    lateinit var summarizePrompt: Resource

    fun search(query: String): ChatResponse {
        val searchResponse = tavilyClient.search(query).getOrThrow()
        val documents = tavilyDocumentConverter.convert(searchResponse)
        geekVectorRepository.addDocuments(documents)

        val prompt = PromptTemplate(
            summarizePrompt,
            mapOf(
                "documents" to documents.toPrompt(),
                "question" to query
            )
        ).create()
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
}
