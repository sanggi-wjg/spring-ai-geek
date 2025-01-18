package com.raynor.geek.api.controller.dto.response

data class PaginationItems<T>(
    val page: Pagination,
    val items: List<T>
)
