package com.raynor.geek.api.controller.dto.request

import com.raynor.geek.llmservice.model.LlmParameter
import jakarta.validation.constraints.NotBlank
import java.util.*

data class ChatRequestDto(
    @field:NotBlank
    val userInput: String,
    @field:NotBlank
    val conversationId: String = UUID.randomUUID().toString(),
    val llmParameter: LlmParameter,
)
