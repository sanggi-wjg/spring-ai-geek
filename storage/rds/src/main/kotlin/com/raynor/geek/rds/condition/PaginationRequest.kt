package com.raynor.geek.rds.condition

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class PaginationRequest(
    val page: Int,
    val size: Int,
    val sortBy: String,
    val sortDirection: String,
) {
    fun toPageRequest(): PageRequest {
        val sortBy = Sort.by(Sort.Direction.valueOf(sortDirection), sortBy)
        return PageRequest.of(page, size, sortBy)
    }
}
