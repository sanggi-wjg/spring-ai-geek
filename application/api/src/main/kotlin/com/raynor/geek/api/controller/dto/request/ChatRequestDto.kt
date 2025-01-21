package com.raynor.geek.api.controller.dto.request

import com.raynor.geek.llmservice.model.OllamaLLMArgument
import jakarta.validation.constraints.NotBlank

data class ChatRequestDto(
    @field:NotBlank
    val userInput: String,
    val llmArgument: OllamaLLMArgument,
)
