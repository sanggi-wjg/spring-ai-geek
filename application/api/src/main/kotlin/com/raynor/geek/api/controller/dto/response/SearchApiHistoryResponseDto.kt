package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.SearchApiHistoryEntity
import com.raynor.geek.shared.enums.SearchAPIType
import org.springframework.data.domain.Page
import java.time.Instant
import java.util.*

data class SearchApiHistoryResponseDto(
    val id: UUID,
    val query: String,
    val searchAPIType: SearchAPIType,
    val responseData: Map<String, Any>,
    val createdAt: Instant,
) {
    companion object {
        fun valueOf(searchApiHistoryEntity: SearchApiHistoryEntity) =
            SearchApiHistoryResponseDto(
                id = searchApiHistoryEntity.id,
                query = searchApiHistoryEntity.query,
                searchAPIType = searchApiHistoryEntity.searchAPIType,
                responseData = searchApiHistoryEntity.responseData,
                createdAt = searchApiHistoryEntity.createdAt,
            )
    }
}

fun Page<SearchApiHistoryEntity>.toPageResponseDto() =
    PaginationItems(
        page = Pagination.valueOf(this),
        items = this.content.map { SearchApiHistoryResponseDto.valueOf(it) }
    )