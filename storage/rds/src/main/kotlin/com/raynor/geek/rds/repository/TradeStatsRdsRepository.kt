package com.raynor.geek.rds.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.raynor.geek.rds.condition.TradeStatsSearchCondition
import com.raynor.geek.rds.entity.trade.QCountryEntity
import com.raynor.geek.rds.entity.trade.QTradeStatsEntity
import com.raynor.geek.rds.entity.trade.QTradeStatsRequestEntity
import com.raynor.geek.rds.entity.trade.TradeStatsEntity
import com.raynor.geek.rds.projection.TradeStatsProjection
import org.hibernate.jpa.HibernateHints
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TradeStatsRdsRepository : JpaRepository<TradeStatsEntity, UUID>, TradeStatsQueryDslRepository {
    fun deleteByTradeStatsRequestId(tradeStatsRequestId: UUID): Long
}

interface TradeStatsQueryDslRepository {
    fun findAllByCondition(searchCondition: TradeStatsSearchCondition): List<TradeStatsProjection>
}

class TradeStatsQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : TradeStatsQueryDslRepository {

    private val country = QCountryEntity.countryEntity
    private val tradeStats = QTradeStatsEntity.tradeStatsEntity
    private val tradeStatsRequest = QTradeStatsRequestEntity.tradeStatsRequestEntity

    override fun findAllByCondition(
        searchCondition: TradeStatsSearchCondition
    ): List<TradeStatsProjection> {
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
                searchCondition.countryAlpha2?.let { country.alpha2.eq(it) },
                searchCondition.hsCode2?.let { tradeStats.hsCode.eq(it) },
                searchCondition.hsCode4?.let { tradeStats.hsCode4.eq(it) },
            )
            .orderBy(
                tradeStats.month.asc(),
                tradeStats.hsCode.asc(),
            )
            .setHint(HibernateHints.HINT_COMMENT, "${this::class.java}::findAllByCondition}")
            .fetch()
    }
}
