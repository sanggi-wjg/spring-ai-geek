package com.raynor.geek.llm.service

import com.raynor.geek.llm.service.factory.OllamaOptionsFactory
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class WritingService(
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/writing/system-basic.st")
    lateinit var systemBasicTemplate: Resource

    @Value("classpath:prompts/writing/user-basic.st")
    lateinit var userBasicTemplate: Resource

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun helpWriting(text: String): ChatResponse {
        val systemPrompt = SystemPromptTemplate(systemBasicTemplate).createMessage()
        val userPrompt = PromptTemplate(userBasicTemplate, mapOf("text" to text)).createMessage()
        val prompt = Prompt(
            listOf(systemPrompt, userPrompt),
            OllamaOptionsFactory.exaone35(),
        )
        return llm.call(prompt)
    }

    fun helpWritingStream(text: String): Flux<ChatResponse> {
        val systemPrompt = SystemPromptTemplate(systemBasicTemplate).createMessage()
        val userPrompt = PromptTemplate(userBasicTemplate, mapOf("text" to text)).createMessage()
        val prompt = Prompt(
            listOf(systemPrompt, userPrompt),
            OllamaOptionsFactory.exaone35WithTemperatureZero(),
        )
        return llm.stream(prompt)
    }
}
