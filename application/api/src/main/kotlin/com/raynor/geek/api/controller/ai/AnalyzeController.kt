package com.raynor.geek.api.controller.ai

import com.raynor.geek.api.controller.dto.request.AnalyzeTradeStatsRequestDto
import com.raynor.geek.llmservice.service.AnalyzeTradeStatsService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/analyze-assistant")
class AnalyzeController(
    private val analyzeTradeStatsService: AnalyzeTradeStatsService,
) {
    @PostMapping("/trade-stats", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun analyzeTradeStats(
        @Valid @RequestBody requestDto: AnalyzeTradeStatsRequestDto,
    ): Flux<String> {
        return analyzeTradeStatsService.analyzeTradeStats(
            llmParameter = requestDto.llmParameter,
            countryAlpha2 = requestDto.countryAlpha2,
            hsCode2 = requestDto.hsCode2,
            hsCode4 = requestDto.hsCode4,
        ).map { it }
    }
}