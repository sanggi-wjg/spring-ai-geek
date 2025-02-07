package com.raynor.geek.rds.condition

import com.raynor.geek.shared.enums.CountrySortBy
import com.raynor.geek.shared.enums.SortDirection

data class CountrySearchCondition(
    val paginationRequest: PaginationRequest,
    val sortBy: CountrySortBy = CountrySortBy.ID,
    val sortDirection: SortDirection = SortDirection.ASC,
    val code: String?,
    val name: String?,
    val alpha2: String?,
    val alpha2s: List<String>?
)
