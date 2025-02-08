package com.raynor.geek.rds.projection

import com.raynor.geek.rds.entity.trade.CountryEntity
import com.raynor.geek.rds.entity.trade.TradeStatsEntity
import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import com.raynor.geek.shared.annotations.NoArg

@NoArg
data class TradeStatsProjection(
    val country: CountryEntity,
    val tradeStats: TradeStatsEntity,
    val tradeStatsRequest: TradeStatsRequestEntity,
)
