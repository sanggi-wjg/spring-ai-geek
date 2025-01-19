package com.raynor.geek.api.controller.dto.request

import jakarta.validation.constraints.NotBlank

data class WritingRequestDto(
    @field:NotBlank
    val text: String,
)
