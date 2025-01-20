package com.raynor.geek.llm.service.factory

import com.raynor.geek.llm.model.OllamaLLMArgument
import org.springframework.ai.ollama.api.OllamaOptions

object OllamaFactory {

    fun create(llmArgument: OllamaLLMArgument): OllamaOptions {
        val builder = OllamaOptions.builder()
            .model(llmArgument.model.name)

        llmArgument.temperature?.let { builder.temperature(it) }

        return builder.build()
    }
}
