package com.raynor.geek.rds.condition

import org.springframework.data.domain.PageRequest

data class PaginationRequest(
    val page: Int,
    val size: Int,
) {
    fun toPageRequest(): PageRequest {
        return PageRequest.of(page, size)
    }
}
