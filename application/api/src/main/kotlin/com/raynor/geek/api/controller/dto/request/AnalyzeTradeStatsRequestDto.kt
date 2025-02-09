package com.raynor.geek.api.controller.dto.request

import com.raynor.geek.llmservice.model.LlmParameter
import jakarta.validation.constraints.NotBlank

data class AnalyzeTradeStatsRequestDto(
    @field:NotBlank
    val countryAlpha2: String,
    val hsCode2: String?,
    val hsCode4: String?,
    val llmParameter: LlmParameter,
) {
    init {
        check(hsCode2 != null || hsCode4 != null) {
            "hsCode2 or hsCode4 must be not null"
        }
    }
}
