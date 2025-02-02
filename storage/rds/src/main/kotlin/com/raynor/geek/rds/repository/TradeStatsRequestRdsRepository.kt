package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TradeStatsRequestRdsRepository : JpaRepository<TradeStatsRequestEntity, UUID>
