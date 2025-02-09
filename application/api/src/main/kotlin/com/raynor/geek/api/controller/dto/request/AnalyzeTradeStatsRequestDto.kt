package com.raynor.geek.api.controller.dto.request

import com.raynor.geek.llmservice.model.LlmParameter
import jakarta.validation.constraints.NotBlank

data class AnalyzeTradeStatsRequestDto(
    val llmParameter: LlmParameter,
    @field:NotBlank
    val countryAlpha2: String,
    @field:NotBlank
    val hsCode4: String,
    val monthGte: String?,
    val monthLte: String?,
) {
    init {

    }
}
