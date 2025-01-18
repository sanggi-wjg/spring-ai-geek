package com.raynor.geek.api.controller.dto.request

// todo implement pagination using this
data class Pagination(
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Int,
    val numberOfElements: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
)
