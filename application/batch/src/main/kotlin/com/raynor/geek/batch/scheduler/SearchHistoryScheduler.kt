package com.raynor.geek.batch.scheduler

import com.raynor.geek.rds.repository.SearchHistoryRdsRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class SearchHistoryScheduler(
    private val searchHistoryRdsRepository: SearchHistoryRdsRepository,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(initialDelay = 10_000, fixedDelay = 60_000)
    fun run() {
        logger.info("[SearchHistoryScheduler] Start")

        searchHistoryRdsRepository.findAll()

        logger.info("[SearchHistoryScheduler] Finished")
    }
}