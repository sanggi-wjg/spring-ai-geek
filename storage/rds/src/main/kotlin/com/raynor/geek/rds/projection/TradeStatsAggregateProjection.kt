package com.raynor.geek.rds.projection

import com.raynor.geek.shared.annotations.NoArg
import java.math.BigDecimal

@NoArg
data class TradeStatsAggregateProjection(
    val month: String,
    val hsCode: String,
    val description: String,
    val importAmount: BigDecimal,
    val importWeight: BigDecimal,
    val exportAmount: BigDecimal,
    val exportWeight: BigDecimal,
    val tradeBalance: BigDecimal,
)
