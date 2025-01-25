package com.raynor.geek.api.controller.dto.request

import com.raynor.geek.llmservice.model.LlmParameter
import jakarta.validation.constraints.NotBlank

data class SearchRequestDto(
    @field:NotBlank
    val query: String,
    val llmParameter: LlmParameter,
)
