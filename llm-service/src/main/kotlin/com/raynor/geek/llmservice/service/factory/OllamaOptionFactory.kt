package com.raynor.geek.llmservice.service.factory

import com.raynor.geek.llmservice.model.OllamaLLMArgument
import org.springframework.ai.ollama.api.OllamaOptions

object OllamaOptionFactory {

    fun create(llmArgument: OllamaLLMArgument): OllamaOptions {
        val builder = OllamaOptions.builder()
            .model(llmArgument.model.modelName)

        llmArgument.temperature?.let { builder.temperature(it) }
        llmArgument.useNUMA?.let { builder.useNUMA(it) }
        llmArgument.numCtx?.let { builder.numCtx(it) }
        llmArgument.numBatch?.let { builder.numBatch(it) }
        llmArgument.topP?.let { builder.topP(it) }
        llmArgument.topK?.let { builder.topK(it) }

        return builder.build()
    }
}
