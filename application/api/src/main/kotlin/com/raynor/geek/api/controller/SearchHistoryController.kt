package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.response.PaginationItems
import com.raynor.geek.api.controller.dto.response.SearchHistoryResponseDto
import com.raynor.geek.api.controller.dto.response.toPageResponseDto
import com.raynor.geek.api.service.SearchHistoryService
import com.raynor.geek.rds.condition.PaginationRequest
import com.raynor.geek.rds.condition.SearchHistorySearchCondition
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/search-histories")
class SearchHistoryController(
    private val searchHistoryService: SearchHistoryService,
) {
    @GetMapping("")
    fun getSearchHistories(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "DESC") sortDirection: String,
        @RequestParam("query", required = false) query: String?,
    ): ResponseEntity<PaginationItems<SearchHistoryResponseDto>> {
        return searchHistoryService.getSearchHistories(
            SearchHistorySearchCondition(
                query = query,
                paginationRequest = PaginationRequest(page, size, sortBy, sortDirection)
            )
        ).let {
            ResponseEntity.ok(it.toPageResponseDto())
        }
    }
}