package com.raynor.geek.api.service.factory

import com.raynor.geek.api.enum.OllamaMyModel
import org.springframework.ai.ollama.api.OllamaOptions

object OllamaOptionsFactory {

    fun exaone35(): OllamaOptions =
        OllamaOptions.builder()
            .model(OllamaMyModel.EXAONE_3_5_8b.model)
            .build()

    fun ragWithExaone35(): OllamaOptions =
        OllamaOptions.builder()
            .model(OllamaMyModel.EXAONE_3_5_8b.model)
            .temperature(0.0)
            .build()
}