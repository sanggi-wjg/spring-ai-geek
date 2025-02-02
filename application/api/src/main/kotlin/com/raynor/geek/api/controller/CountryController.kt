package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.response.CountryResponseDto
import com.raynor.geek.api.controller.dto.response.PaginationItems
import com.raynor.geek.api.controller.dto.response.toPageResponseDto
import com.raynor.geek.api.service.CountryService
import com.raynor.geek.rds.condition.CountrySearchCondition
import com.raynor.geek.rds.condition.PaginationRequest
import com.raynor.geek.shared.enums.CountrySortBy
import com.raynor.geek.shared.enums.SortDirection
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/countries")
class CountryController(
    private val countryService: CountryService
) {
    @GetMapping("")
    fun getCountries(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "DESC") sortDirection: String,
        @RequestParam("code", required = false) code: String?,
        @RequestParam("name", required = false) name: String?,
        @RequestParam("alpha2", required = false) alpha2: String?,
    ): ResponseEntity<PaginationItems<CountryResponseDto>> {
        return countryService.getCountries(
            CountrySearchCondition(
                paginationRequest = PaginationRequest(page, size, sortBy, sortDirection),
                sortBy = CountrySortBy.valueOf(sortBy.uppercase()),
                sortDirection = SortDirection.valueOf(sortDirection),
                code = code,
                name = name,
                alpha2 = alpha2,
            )
        ).let {
            ResponseEntity.ok(it.toPageResponseDto())
        }
    }
}
