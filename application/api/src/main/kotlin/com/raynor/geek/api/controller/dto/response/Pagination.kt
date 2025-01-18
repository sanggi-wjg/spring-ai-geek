package com.raynor.geek.api.controller.dto.response

import org.springframework.data.domain.Page

data class Pagination(
    val page: Int,
    val size: Int,
    val totalPages: Int,
    val totalElements: Long,
    val numberOfElements: Int,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
) {
    companion object {
        fun <T> valueOf(page: Page<T>) = Pagination(
            page = page.number,
            size = page.size,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            numberOfElements = page.numberOfElements,
            hasPrevious = !page.isFirst,
            hasNext = !page.isLast,
        )
    }
}
