package com.raynor.geek.llmservice.service

import com.raynor.geek.llmservice.model.OllamaLLMArgument
import com.raynor.geek.llmservice.service.factory.OllamaFactory
import com.raynor.geek.llmservice.service.factory.PromptFactory
import com.raynor.geek.shared.enums.OllamaMyModel
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.model.ChatResponse
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
        val prompt = PromptFactory.create(
            llmArguments = OllamaFactory.create(
                OllamaLLMArgument(
                    OllamaMyModel.EXAONE_3_5_8b,
                )
            ),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("text" to text),
        )
        return llm.call(prompt)
    }

    fun helpWritingStream(text: String): Flux<ChatResponse> {
        val prompt = PromptFactory.create(
            llmArguments = OllamaFactory.create(
                OllamaLLMArgument(
                    OllamaMyModel.EXAONE_3_5_8b,
                )
            ),
            systemResource = systemBasicTemplate,
            userResource = userBasicTemplate,
            ragModel = mapOf("text" to text),
        )
        return llm.stream(prompt)
    }
}
