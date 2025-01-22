package com.raynor.geek.llmservice.service

import com.raynor.geek.llmservice.model.OllamaLLMArgument
import com.raynor.geek.llmservice.service.factory.OllamaOptionFactory
import com.raynor.geek.llmservice.service.factory.PromptFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ChattingService(
    private val chatClientBuilder: ChatClient.Builder,
) {
    @Value("classpath:prompts/global/system-basic.st")
    lateinit var systemBasicTemplate: Resource


    fun chat(userInput: String, llmArgument: OllamaLLMArgument): ChatResponse {
        val prompt = PromptFactory.create(
            ollamaOptions = OllamaOptionFactory.create(llmArgument),
            systemResource = systemBasicTemplate,
            promptTemplate = PromptTemplate(userInput),
        )

        return chatClientBuilder
            .defaultAdvisors(
                MessageChatMemoryAdvisor(InMemoryChatMemory(), "uniqueId", 50)
            )
            .build().prompt(prompt).call().chatResponse()!!
    }

    fun chatStream(userInput: String, llmArgument: OllamaLLMArgument): Flux<ChatResponse> {
        val prompt = PromptFactory.create(
            ollamaOptions = OllamaOptionFactory.create(llmArgument),
            systemResource = systemBasicTemplate,
            promptTemplate = PromptTemplate(userInput),
        )
        return chatClientBuilder
            .defaultAdvisors(
                MessageChatMemoryAdvisor(InMemoryChatMemory(), "uniqueId", 50)
            )
            .build().prompt(prompt).stream().chatResponse()
    }
}
