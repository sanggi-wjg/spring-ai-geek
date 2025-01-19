package com.raynor.geek.llm.service

import com.raynor.geek.llm.service.factory.OllamaOptionsFactory
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class WritingService(
    private val llm: OllamaChatModel,
) {
    @Value("classpath:prompts/writing-assistant-simple.st")
    lateinit var writingAssistantSimple: Resource

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun helpWriting(text: String): ChatResponse {
        val prompt = PromptTemplate(
            writingAssistantSimple,
            mapOf("text" to text),
        ).create(
            OllamaOptionsFactory.exaone35WithTemperatureZero(),
        )
        return llm.call(prompt)
    }

    fun helpWritingStream(text: String): Flux<ChatResponse> {
        val prompt = PromptTemplate(
            writingAssistantSimple,
            mapOf("text" to text),
        ).create(
            OllamaOptionsFactory.exaone35WithTemperatureZero(),
        )
        return llm.stream(prompt)
    }
}
