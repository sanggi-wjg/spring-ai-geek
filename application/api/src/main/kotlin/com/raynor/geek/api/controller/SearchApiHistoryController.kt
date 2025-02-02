package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.response.PaginationItems
import com.raynor.geek.api.controller.dto.response.SearchApiHistoryResponseDto
import com.raynor.geek.api.controller.dto.response.toPageResponseDto
import com.raynor.geek.api.service.SearchApiHistoryService
import com.raynor.geek.rds.condition.PaginationRequest
import com.raynor.geek.rds.condition.SearchApiHistorySearchCondition
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/search-api-histories")
class SearchApiHistoryController(
    private val searchApiHistoryService: SearchApiHistoryService,
) {

    @GetMapping("")
    fun getSearchApiHistories(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "DESC") sortDirection: String,
        @RequestParam("query", required = false) query: String?,
    ): ResponseEntity<PaginationItems<SearchApiHistoryResponseDto>> {
        return searchApiHistoryService.getSearchApiHistories(
            SearchApiHistorySearchCondition(
                paginationRequest = PaginationRequest(page, size, sortBy, sortDirection),
                query = query,
            )
        ).let {
            ResponseEntity.ok(it.toPageResponseDto())
        }
    }
}
