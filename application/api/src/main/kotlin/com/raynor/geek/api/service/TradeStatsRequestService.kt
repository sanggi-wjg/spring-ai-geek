package com.raynor.geek.api.service

import com.raynor.geek.client.datagokr.trade.TradeStatsClient
import com.raynor.geek.rds.condition.TradeStatsRequestSearchCondition
import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import com.raynor.geek.rds.repository.TradeStatsRdsRepository
import com.raynor.geek.rds.repository.TradeStatsRequestRdsRepository
import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TradeStatsRequestService(
    private val tradeStatsRequestRdsRepository: TradeStatsRequestRdsRepository,
    private val tradeStatsRdsRepository: TradeStatsRdsRepository,
    private val tradeStatsClient: TradeStatsClient,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(readOnly = true)
    fun getTradeStatsRequests(condition: TradeStatsRequestSearchCondition): Page<TradeStatsRequestEntity> {
        return tradeStatsRequestRdsRepository.findPageWithCondition(condition)
    }

    @Transactional
    fun syncTradeStats(tradeStatsRequestId: UUID) {
        val request = tradeStatsRequestRdsRepository.findByIdOrNull(tradeStatsRequestId)
            ?: throw EntityNotFoundException("TradeStatsRequest not found. id: $tradeStatsRequestId")
        tradeStatsRdsRepository.deleteByTradeStatsRequestId(tradeStatsRequestId)

        val tradeStats = tradeStatsClient.getTradeStats(
            request.startMonth,
            request.endMonth,
            request.country.alpha2,
        ).getOrThrow()
    }
}
