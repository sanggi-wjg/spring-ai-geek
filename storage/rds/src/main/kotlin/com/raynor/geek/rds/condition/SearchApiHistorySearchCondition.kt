package com.raynor.geek.rds.condition

data class SearchApiHistorySearchCondition(
    val paginationRequest: PaginationRequest,
    val query: String?,
)
