package com.raynor.geek.api.controller.general

import com.raynor.geek.api.controller.dto.response.CountryResponseDto
import com.raynor.geek.api.controller.dto.response.PaginationItems
import com.raynor.geek.api.controller.dto.response.TradeStatsRequestResponseDto
import com.raynor.geek.api.controller.dto.response.toPageResponseDto
import com.raynor.geek.api.service.CountryService
import com.raynor.geek.api.service.TradeStatsRequestService
import com.raynor.geek.rds.condition.CountrySearchCondition
import com.raynor.geek.rds.condition.PaginationRequest
import com.raynor.geek.rds.condition.TradeStatsRequestSearchCondition
import com.raynor.geek.shared.enums.CountrySortBy
import com.raynor.geek.shared.enums.SortDirection
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/countries")
class CountryController(
    private val countryService: CountryService,
    private val tradeStatsRequestService: TradeStatsRequestService,
) {
    @GetMapping("")
    fun getCountries(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "ID") sortBy: String,
        @RequestParam(defaultValue = "DESC") sortDirection: String,
        @RequestParam("code", required = false) code: String?,
        @RequestParam("name", required = false) name: String?,
        @RequestParam("alpha2", required = false) alpha2: String?,
        @RequestParam("alpha2s", required = false) alpha2s: List<String>?,
    ): ResponseEntity<PaginationItems<CountryResponseDto>> {
        return countryService.getCountries(
            CountrySearchCondition(
                paginationRequest = PaginationRequest(page - 1, size),
                sortBy = CountrySortBy.valueOf(sortBy.uppercase()),
                sortDirection = SortDirection.valueOf(sortDirection),
                code = code,
                name = name,
                alpha2 = alpha2,
                alpha2s = alpha2s,
            )
        ).let {
            ResponseEntity.ok(it.toPageResponseDto())
        }
    }

    @GetMapping("/{id}/trade-stats-requests")
    fun getCountryTradeStatsRequests(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "100") size: Int,
        @PathVariable("id") id: UUID,
        @RequestParam("isSynced", required = false) isSynced: Boolean,
    ): ResponseEntity<PaginationItems<TradeStatsRequestResponseDto>> {
        return tradeStatsRequestService.getTradeStatsRequests(
            condition = TradeStatsRequestSearchCondition(
                paginationRequest = PaginationRequest(page - 1, size),
                countryId = id,
                isSynced = isSynced,
            )
        ).let {
            ResponseEntity.ok(it.toPageResponseDto())
        }
    }
}
