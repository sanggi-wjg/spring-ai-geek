package com.raynor.geek.client.tavily.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TavilySearchResponseDto(
    val answer: String?,
    val query: String,
    val responseTime: Float,
    val images: List<String>,
    val results: List<Result>
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Result(
        val title: String,
        val url: String,
        val content: String,
        val rawContent: String?,
        val score: Float,
        val publishedDate: String?
    )
}
