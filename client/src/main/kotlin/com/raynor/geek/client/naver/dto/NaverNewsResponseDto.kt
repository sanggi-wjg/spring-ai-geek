package com.raynor.geek.client.naver.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverNewsResponseDto(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverNewsItem>
) {
    data class NaverNewsItem(
        val title: String,
        @JsonProperty("originallink")
        val originalLink: String,
        val link: String,
        val description: String,
        val pubDate: String,
    )
}
