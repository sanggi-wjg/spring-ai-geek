package com.raynor.geek.rds.condition

data class TradeStatsRequestSearchCondition(
    val paginationRequest: PaginationRequest,
    val countryCode: String?,
    val countryName: String?,
    val countryAlpha2Code: String?,
)
