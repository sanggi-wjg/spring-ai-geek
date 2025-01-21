package com.raynor.geek.llmservice.service.factory

import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.ai.ollama.api.OllamaOptions
import org.springframework.core.io.Resource

object PromptFactory {

    fun create(
        ollamaOptions: OllamaOptions,
        systemResource: Resource,
        userResource: Resource,
        ragModel: Map<String, Any>? = null
    ): Prompt {
        val systemPrompt = SystemPromptTemplate(systemResource).createMessage()
        val userPrompt = ragModel?.let {
            PromptTemplate(userResource, it).createMessage()
        } ?: PromptTemplate(userResource).createMessage()

        return Prompt(
            listOf(systemPrompt, userPrompt),
            ollamaOptions,
        )
    }

    fun createBasicSystemPromptTemplate(): SystemPromptTemplate {
        return SystemPromptTemplate("You are a helpful assistant.")
    }
}