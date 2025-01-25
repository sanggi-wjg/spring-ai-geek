package com.raynor.geek.llmservice.service

import com.raynor.geek.llmservice.advisor.RdsChatMemory
import com.raynor.geek.llmservice.model.LlmParameter
import com.raynor.geek.llmservice.model.toOllamaOptions
import com.raynor.geek.llmservice.service.factory.PromptFactory
import com.raynor.geek.rds.repository.ConversationHistoryRdsRepository
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ChattingService(
//    private val chatClientBuilder: ChatClient.Builder,
    private val chatModel: OllamaChatModel,
    private val repository: ConversationHistoryRdsRepository,
) {
    @Value("classpath:prompts/global/system-basic.st")
    lateinit var systemBasicTemplate: Resource

    fun chat(
        userInput: String,
        conversationId: String,
        llmParameter: LlmParameter
    ): String {
        val prompt = PromptFactory.create(
            ollamaOptions = llmParameter.toOllamaOptions(),
            systemResource = systemBasicTemplate,
            promptTemplate = PromptTemplate(userInput),
        )
//        return chatClientBuilder
        return ChatClient.builder(chatModel)
            .defaultAdvisors(
                MessageChatMemoryAdvisor(RdsChatMemory(repository), conversationId, 50)
//                MessageChatMemoryAdvisor(InMemoryChatMemory(), conversationId, 50)
            )
            .build().prompt(prompt).call()
            .chatResponse()!!.results.first().output.text
    }

    fun chatStream(
        userInput: String,
        conversationId: String,
        llmParameter: LlmParameter
    ): Flux<String> {
        val prompt = PromptFactory.create(
            ollamaOptions = llmParameter.toOllamaOptions(),
            systemResource = systemBasicTemplate,
            promptTemplate = PromptTemplate(userInput),
        )
        return ChatClient.builder(chatModel)
            .defaultAdvisors(
                MessageChatMemoryAdvisor(RdsChatMemory(repository), conversationId, 50)
            )
            .build().prompt(prompt).stream().chatResponse()
            .map { it.results.first().output.text }
    }


    fun chatStream11(
        userInput: String,
        conversationId: String,
        llmParameter: LlmParameter
    ): Flux<String> {
        val prompt = PromptFactory.create(
            ollamaOptions = llmParameter.toOllamaOptions(),
            systemResource = systemBasicTemplate,
            promptTemplate = PromptTemplate(userInput),
        )
//        return ChatClient.builder(llm)
        return ChatClient.builder(chatModel)
            .defaultAdvisors(
                MessageChatMemoryAdvisor(InMemoryChatMemory(), conversationId, 50)
            )
            .build().prompt(prompt).stream().chatResponse()
            .map { it.results.first().output.text }
    }
}
