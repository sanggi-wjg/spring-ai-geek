package com.raynor.geek.api.controller

import com.raynor.geek.api.service.SearchHistoryService
import com.raynor.geek.rds.condition.PaginationRequest
import com.raynor.geek.rds.condition.SearchHistorySearchCondition
import com.raynor.geek.rds.entity.SearchHistoryEntity
import org.springframework.data.domain.Page
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
    ): ResponseEntity<Page<SearchHistoryEntity>> {
        return searchHistoryService.getSearchHistories(
            SearchHistorySearchCondition(
                query = query,
                paginationRequest = PaginationRequest(page, size, sortBy, sortDirection)
            )
        ).let {
            ResponseEntity.ok(it)
        }
    }
}