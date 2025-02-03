package com.raynor.geek.rds.condition

import java.util.*

data class TradeStatsRequestSearchCondition(
    val paginationRequest: PaginationRequest,
    val countryId: UUID?,
)
