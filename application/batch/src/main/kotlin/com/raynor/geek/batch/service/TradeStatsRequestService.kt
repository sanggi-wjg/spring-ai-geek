package com.raynor.geek.batch.service

import com.raynor.geek.rds.entity.trade.CountryEntity
import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import com.raynor.geek.rds.repository.CountryRdsRepository
import com.raynor.geek.rds.repository.TradeStatsRequestRdsRepository
import com.raynor.geek.shared.utils.DateUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class TradeStatsRequestService(
    private val countryRdsRepository: CountryRdsRepository,
    private val tradeStatsRequestRdsRepository: TradeStatsRequestRdsRepository,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun syncTradeStatsRequestIfNotExists() {
        val yearMonthList = DateUtil.getYearMonthList()
        val countries = countryRdsRepository.findAll()
        val requests = tradeStatsRequestRdsRepository.findAll()
        val requestByCountry = requests.groupBy { it.country }

        countries.forEach { country ->
            val countryRequests = requestByCountry[country].orEmpty()
            val missingMonths = yearMonthList.minus(countryRequests.map { it.startMonth }.toSet())

            if (missingMonths.isNotEmpty()) {
                createTradeStatsRequestAll(country, missingMonths)
            }
        }
    }

    private fun createTradeStatsRequestAll(
        country: CountryEntity,
        yearMonthList: Set<String>
    ): List<TradeStatsRequestEntity> {
        val now = Instant.now()

        return yearMonthList.map { month ->
            TradeStatsRequestEntity(
                country = country,
                startMonth = month,
                endMonth = month,
                createdAt = now,
            )
        }.let {
            tradeStatsRequestRdsRepository.saveAll(it)
        }
    }
}
