package com.raynor.geek.api.service

import com.raynor.geek.api.repository.GeekVectorRepository
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.ai.document.Document
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class RagService(
    private val geekVectorRepository: GeekVectorRepository,
    private val ollamaChatModel: OllamaChatModel,
) {
    @Value("classpath:prompts/qa-1.st")
    lateinit var qaPrompt: Resource

    fun simple(): ChatResponse {
        val documents = listOf(
            Document("그제 날씨는 봄이였음"),
            Document("어제 날씨는 여름이였음"),
            Document("오늘 날씨는 가을이었음"),
            Document("내일 날씨는 겨울임"),
        )
        geekVectorRepository.addDocuments(documents)

        val userInput = "오늘 날씨"
        val foundDocuments = geekVectorRepository.similaritySearch(userInput, 3)

        val systemMessage = SystemPromptTemplate(qaPrompt).createMessage(
            mapOf("documents" to foundDocuments.joinToString { "\n" })
        )

        val prompt = Prompt(
            listOf(systemMessage, UserMessage(userInput))
        )
        return ollamaChatModel.call(prompt)
    }
}