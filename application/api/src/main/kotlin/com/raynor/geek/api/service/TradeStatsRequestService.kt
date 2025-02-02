package com.raynor.geek.api.service

import com.raynor.geek.rds.condition.TradeStatsRequestSearchCondition
import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import com.raynor.geek.rds.repository.TradeStatsRequestRdsRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TradeStatsRequestService(
    private val tradeStatsRequestRdsRepository: TradeStatsRequestRdsRepository,
) {

    @Transactional(readOnly = true)
    fun getTradeStatsRequests(
        condition: TradeStatsRequestSearchCondition,
    ): Page<TradeStatsRequestEntity> {
//        val spec = Specification<TradeStatsRequestEntity> { root, _, criteriaBuilder ->
//            condition.countryCode?.let { criteriaBuilder.equal(root.get("countryCode"), condition.countryCode) }
//            condition.countryName?.let { criteriaBuilder.like(root.get("countryName"), condition.countryName) }
//            condition.countryAlpha2Code?.let { criteriaBuilder.equal(root.get("countryAlpha2Code"), condition.countryAlpha2Code) }
//        }

        return tradeStatsRequestRdsRepository.findAllWithCountry(condition.paginationRequest.toPageRequest())
    }
}