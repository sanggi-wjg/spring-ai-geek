package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.response.PaginationItems
import com.raynor.geek.api.controller.dto.response.TradeStatsRequestResponseDto
import com.raynor.geek.api.controller.dto.response.toPageResponseDto
import com.raynor.geek.api.service.TradeStatsRequestService
import com.raynor.geek.rds.condition.PaginationRequest
import com.raynor.geek.rds.condition.TradeStatsRequestSearchCondition
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/trade-stats-requests")
class TradeStatsRequestController(
    private val tradeStatsRequestService: TradeStatsRequestService,
) {
    @GetMapping("")
    fun getTradeStatsRequests(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "DESC") sortDirection: String,
        @RequestParam("countryCode", required = false) countryCode: String?,
        @RequestParam("countryName", required = false) countryName: String?,
        @RequestParam("countryAlpha2Code", required = false) countryAlpha2Code: String?,
    ): ResponseEntity<PaginationItems<TradeStatsRequestResponseDto>> {
        return tradeStatsRequestService.getTradeStatsRequests(
            TradeStatsRequestSearchCondition(
                paginationRequest = PaginationRequest(page, size, sortBy, sortDirection),
                countryCode = countryCode,
                countryName = countryName,
                countryAlpha2Code = countryAlpha2Code,
            )
        ).let {
            ResponseEntity.ok(it.toPageResponseDto())
        }
    }
}
