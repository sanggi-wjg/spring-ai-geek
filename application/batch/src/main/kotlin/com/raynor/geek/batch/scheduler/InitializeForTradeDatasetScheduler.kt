package com.raynor.geek.batch.scheduler

import com.raynor.geek.batch.service.CountryService
import com.raynor.geek.batch.service.TradeStatsRequestService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class InitializeForTradeDatasetScheduler(
    private val countryService: CountryService,
    private val tradeStatsRequestService: TradeStatsRequestService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(initialDelay = 10_000, fixedDelay = 600_000_000)
    fun run() {
        logger.info("[InitializeForTradeDatasetScheduler] Start")

        countryService.createCountryIfNotExists()
        tradeStatsRequestService.createTradeStatsRequest()

        logger.info("[InitializeForTradeDatasetScheduler] Finished")
    }
}
