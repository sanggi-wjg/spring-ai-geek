package com.raynor.geek.llm.service.factory

import com.raynor.geek.shared.enums.OllamaMyModel
import org.springframework.ai.ollama.api.OllamaOptions

object OllamaOptionsFactory {

    fun exaone35(): OllamaOptions =
        OllamaOptions.builder()
            .model(OllamaMyModel.EXAONE_3_5_8b.value)
            .build()

    fun exaone35WithTemperatureZero(): OllamaOptions =
        OllamaOptions.builder()
            .model(OllamaMyModel.EXAONE_3_5_8b.value)
            .temperature(0.0)
            .build()
}