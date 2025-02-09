package com.raynor.geek.api.controller.ai

import com.raynor.geek.api.controller.dto.request.AnalyzeTradeStatsRequestDto
import com.raynor.geek.llmservice.service.AnalyzeTradeStatsService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/analyze-assistant")
class AnalyzeController(
    private val analyzeTradeStatsService: AnalyzeTradeStatsService,
) {
    @PostMapping("/trade-stats")
    fun analyzeTradeStats(
        @Valid @RequestBody requestDto: AnalyzeTradeStatsRequestDto,
    ): ResponseEntity<Unit> {
        analyzeTradeStatsService.analyzeTradeStats(
            llmParameter = requestDto.llmParameter,
            countryAlpha2 = requestDto.countryAlpha2,
            hsCode2 = requestDto.hsCode2,
            hsCode4 = requestDto.hsCode4,
        )
        return ResponseEntity.noContent().build()
    }
}