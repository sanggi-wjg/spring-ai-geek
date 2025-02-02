package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import org.springframework.data.domain.Page
import java.time.Instant
import java.util.*

data class TradeStatsRequestResponseDto(
    val countryId: UUID,
    val countryName: String,
    val countryCode: String,
    val countryAlpha2Code: String,
    val requests: List<TradeStatsRequest>,
) {
    data class TradeStatsRequest(
        val id: UUID,
        val startMonth: String,
        val endMonth: String,
        val createdAt: Instant,
        val isPulled: Boolean,
        val pulledAt: Instant?,
    )

    companion object {
        fun valueOf(tradeStatsRequestEntities: List<TradeStatsRequestEntity>) =
            tradeStatsRequestEntities.groupBy { it.country }.map { (country, requests) ->
                TradeStatsRequestResponseDto(
                    countryId = country.id,
                    countryCode = country.code,
                    countryName = country.name,
                    countryAlpha2Code = country.alpha2,
                    requests = requests.map {
                        TradeStatsRequestResponseDto.TradeStatsRequest(
                            id = it.id,
                            startMonth = it.startMonth,
                            endMonth = it.endMonth,
                            createdAt = it.createdAt,
                            isPulled = it.isPulled,
                            pulledAt = it.pulledAt,
                        )
                    },
                )
            }
    }
}

fun Page<TradeStatsRequestEntity>.toPageResponseDto() =
    PaginationItems(
        page = Pagination.valueOf(this),
        items = TradeStatsRequestResponseDto.valueOf(this.content),
    )
