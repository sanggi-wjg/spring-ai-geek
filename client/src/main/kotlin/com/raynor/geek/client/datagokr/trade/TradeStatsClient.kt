package com.raynor.geek.client.datagokr.trade

import com.raynor.geek.client.datagokr.DataGoKrAPIProperty
import com.raynor.geek.client.datagokr.exception.DataGoKrAPIException
import com.raynor.geek.client.datagokr.trade.dto.TradeStatsResponseDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TradeStatsClient(
    private val dataGoKrAPIProperty: DataGoKrAPIProperty,
    private val tradeStatsAPI: TradeStatsAPI,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getTradeStats(startMonth: String, endMonth: String, countryCode: String): Result<TradeStatsResponseDto> {
        check(startMonth.isNotBlank()) { "startMonth must not be blank" }
        check(endMonth.isNotBlank()) { "endMonth must not be blank" }
        check(countryCode.isNotBlank()) { "countryCode must not be blank" }
        check(startMonth.length == 6) { "startMonth must be 6 digits" } // YYYYMM
        check(endMonth.length == 6) { "endMonth must be 6 digits" }
        check(countryCode.length == 2) { "countryCode must be 2 digits" }
        // 요청 가능 범위는 1년 까지, 필요시 validation

        return runCatching {
            tradeStatsAPI.getTradeStats(
                serviceKey = dataGoKrAPIProperty.key,
                startMonth = startMonth,
                endMonth = endMonth,
                countryCode = countryCode,
            )
        }.onSuccess {
            logger.debug("DataGoKr api getTradeStats: {}", it)
        }.onFailure {
            logger.error("DataGoKr api getTradeStats failed", it)
            throw DataGoKrAPIException(cause = it)
        }
    }
}
