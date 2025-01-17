package com.raynor.geek.api.controller.dto.request

import jakarta.validation.constraints.NotBlank

data class SearchRequestDto(
    @field:NotBlank
    val query: String,
)
