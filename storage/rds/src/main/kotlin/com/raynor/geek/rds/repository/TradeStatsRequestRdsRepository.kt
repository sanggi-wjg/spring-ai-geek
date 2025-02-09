package com.raynor.geek.rds.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.raynor.geek.rds.condition.TradeStatsRequestSearchCondition
import com.raynor.geek.rds.entity.trade.QCountryEntity
import com.raynor.geek.rds.entity.trade.QTradeStatsRequestEntity
import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import org.hibernate.jpa.HibernateHints
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TradeStatsRequestRdsRepository : JpaRepository<TradeStatsRequestEntity, UUID>, TradeStatsRequestQueryDslRepository

interface TradeStatsRequestQueryDslRepository {
    fun findPageWithCondition(condition: TradeStatsRequestSearchCondition): Page<TradeStatsRequestEntity>
}

class TradeStatsRequestQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : TradeStatsRequestQueryDslRepository {

    private val country = QCountryEntity.countryEntity
    private val tradeStatsRequest = QTradeStatsRequestEntity.tradeStatsRequestEntity

    override fun findPageWithCondition(condition: TradeStatsRequestSearchCondition): Page<TradeStatsRequestEntity> {
        val pageRequest = condition.paginationRequest.toPageRequest()

        val result = jpaQueryFactory
            .selectFrom(tradeStatsRequest)
            .innerJoin(country).on(tradeStatsRequest.country.id.eq(country.id))
            .where(
                condition.countryId?.let { tradeStatsRequest.country.id.eq(it) },
                condition.isSynced?.let { tradeStatsRequest.isSynced.eq(it) },
            )
            .orderBy(tradeStatsRequest.startMonth.asc(), tradeStatsRequest.endMonth.asc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .setHint(HibernateHints.HINT_COMMENT, "${this::class.java}::findPageByCondition}")
            .fetch()

        val count = jpaQueryFactory
            .select(tradeStatsRequest.id.count())
            .from(tradeStatsRequest)
            .innerJoin(country).on(tradeStatsRequest.country.id.eq(country.id))
            .where(
                condition.countryId?.let { tradeStatsRequest.country.id.eq(it) },
                condition.isSynced?.let { tradeStatsRequest.isSynced.eq(it) },
            )
            .setHint(HibernateHints.HINT_COMMENT, "${this::class.java}::findPageByCondition}")
            .fetchFirst()

        return PageImpl(result, pageRequest, count)
    }
}
