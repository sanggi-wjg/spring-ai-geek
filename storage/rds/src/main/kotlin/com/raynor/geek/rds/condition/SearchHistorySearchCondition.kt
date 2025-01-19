package com.raynor.geek.rds.condition

data class SearchHistorySearchCondition(
    val paginationRequest: PaginationRequest,
    val query: String?,
)
