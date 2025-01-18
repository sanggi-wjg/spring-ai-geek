package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.SearchHistoryEntity
import org.springframework.data.domain.Page

fun Page<SearchHistoryEntity>.toPageResponseDto(): PaginationItems<SearchHistoryResponseDto> {
    return PaginationItems(
        page = Pagination.valueOf(this),
        items = this.content.map { SearchHistoryResponseDto.toResponseDto(it) }
    )
}
