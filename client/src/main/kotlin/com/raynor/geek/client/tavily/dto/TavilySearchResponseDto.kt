package com.raynor.geek.client.tavily.dto

data class TavilySearchResponseDto(
    val answer: String,
    val query: String,
    val responseTime: Float,
    val images: List<String>,
    val results: List<Result>
) {
    data class Result(
        val title: String,
        val url: String,
        val content: String,
        val rawContent: String,
        val score: Float,
        val publishedDate: String?
    )
}
