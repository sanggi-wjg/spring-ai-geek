package com.raynor.geek.client.naver.dto

data class NaverSearchResponseDto(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<Item>
) {
    data class Item(
        val title: String,
        val link: String,
        val description: String,
    )
}
