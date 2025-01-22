package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.SearchAPIHistoryEntity
import com.raynor.geek.shared.enums.SearchAPIType
import java.time.Instant
import java.util.*

data class SearchHistoryResponseDto(
    val id: UUID,
    val query: String,
    val searchAPIType: SearchAPIType,
    val responseData: Map<String, Any>,
    val createdAt: Instant,
) {
    companion object {
        fun toResponseDto(searchAPIHistoryEntity: SearchAPIHistoryEntity) = SearchHistoryResponseDto(
            id = searchAPIHistoryEntity.id,
            query = searchAPIHistoryEntity.query,
            searchAPIType = searchAPIHistoryEntity.searchAPIType,
            responseData = searchAPIHistoryEntity.responseData,
            createdAt = searchAPIHistoryEntity.createdAt,
        )
    }
}
