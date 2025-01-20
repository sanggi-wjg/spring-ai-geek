package com.raynor.geek.llmservice.service.factory

import com.raynor.geek.llmservice.model.OllamaLLMArgument
import org.springframework.ai.ollama.api.OllamaOptions

object OllamaFactory {

    fun create(llmArgument: OllamaLLMArgument): OllamaOptions {
        val builder = OllamaOptions.builder()
            .model(llmArgument.model.modelName)

        llmArgument.temperature?.let { builder.temperature(it) }

        return builder.build()
    }
}
