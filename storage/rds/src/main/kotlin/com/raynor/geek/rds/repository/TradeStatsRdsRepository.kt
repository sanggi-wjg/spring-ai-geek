package com.raynor.geek.rds.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.raynor.geek.rds.entity.trade.QTradeStatsEntity
import com.raynor.geek.rds.entity.trade.QTradeStatsRequestEntity
import com.raynor.geek.rds.entity.trade.TradeStatsEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TradeStatsRdsRepository : JpaRepository<TradeStatsEntity, UUID>, TradeStatsQueryDslRepository

interface TradeStatsQueryDslRepository {
    fun deleteByTradeStatsRequestId(tradeStatsRequestId: UUID): Long
}

class TradeStatsQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : TradeStatsQueryDslRepository {

    private val tradeStatsRequest = QTradeStatsRequestEntity.tradeStatsRequestEntity
    private val tradeStats = QTradeStatsEntity.tradeStatsEntity

    override fun deleteByTradeStatsRequestId(tradeStatsRequestId: UUID): Long {
        return jpaQueryFactory
            .delete(tradeStats)
            .where(tradeStatsRequest.id.eq(tradeStatsRequestId))
            .execute()
    }
}
