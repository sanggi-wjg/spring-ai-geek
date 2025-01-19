package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.SearchHistoryEntity
import com.raynor.geek.shared.enums.SearchFrom
import java.time.Instant
import java.util.*

data class SearchHistoryResponseDto(
    val id: UUID,
    val query: String,
    val searchFrom: SearchFrom,
    val responseData: Map<String, Any>,
    val createdAt: Instant,
) {
    companion object {
        fun toResponseDto(searchHistoryEntity: SearchHistoryEntity) = SearchHistoryResponseDto(
            id = searchHistoryEntity.id,
            query = searchHistoryEntity.query,
            searchFrom = searchHistoryEntity.searchFrom,
            responseData = searchHistoryEntity.responseData,
            createdAt = searchHistoryEntity.createdAt,
        )
    }
}
