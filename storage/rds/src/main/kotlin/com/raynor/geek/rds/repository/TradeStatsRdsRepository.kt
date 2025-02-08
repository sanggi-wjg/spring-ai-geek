package com.raynor.geek.rds.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.raynor.geek.rds.entity.trade.QCountryEntity
import com.raynor.geek.rds.entity.trade.QTradeStatsEntity
import com.raynor.geek.rds.entity.trade.QTradeStatsRequestEntity
import com.raynor.geek.rds.entity.trade.TradeStatsEntity
import com.raynor.geek.rds.projection.TradeStatsProjection
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TradeStatsRdsRepository : JpaRepository<TradeStatsEntity, UUID>, TradeStatsQueryDslRepository

interface TradeStatsQueryDslRepository {
    fun findSomething(): List<TradeStatsProjection>

    fun deleteByTradeStatsRequestId(tradeStatsRequestId: UUID): Long
}

class TradeStatsQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : TradeStatsQueryDslRepository {

    private val country = QCountryEntity.countryEntity
    private val tradeStats = QTradeStatsEntity.tradeStatsEntity
    private val tradeStatsRequest = QTradeStatsRequestEntity.tradeStatsRequestEntity

    override fun findSomething(): List<TradeStatsProjection> {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    TradeStatsProjection::class.java,
                    country,
                    tradeStats,
                    tradeStatsRequest,
                )
            )
            .from(tradeStats)
            .join(country).on(country.id.eq(tradeStats.country.id))
            .join(tradeStatsRequest).on(tradeStatsRequest.id.eq(tradeStats.tradeStatsRequest.id))
            .where(
                country.alpha2.`in`("DK"),
                tradeStats.hsCode2.eq("57")
            )
            .orderBy(
                country.id.asc(),
                tradeStats.month.asc(),
                tradeStats.hsCode.asc(),
            )
            .fetch()
    }

    override fun deleteByTradeStatsRequestId(tradeStatsRequestId: UUID): Long {
        return jpaQueryFactory
            .delete(tradeStats)
            .where(tradeStatsRequest.id.eq(tradeStatsRequestId))
            .execute()
    }
}
