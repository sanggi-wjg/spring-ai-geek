package com.raynor.geek.api.controller.dto.request

import jakarta.validation.constraints.NotEmpty

data class SearchRequestDto(
    @NotEmpty
    val query: String,
)
