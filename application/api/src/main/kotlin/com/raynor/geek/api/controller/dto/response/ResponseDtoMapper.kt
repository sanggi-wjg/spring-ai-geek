package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.SearchAPIHistoryEntity
import org.springframework.data.domain.Page

fun Page<SearchAPIHistoryEntity>.toPageResponseDto(): PaginationItems<SearchHistoryResponseDto> {
    return PaginationItems(
        page = Pagination.valueOf(this),
        items = this.content.map { SearchHistoryResponseDto.toResponseDto(it) }
    )
}
