package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import org.springframework.data.domain.Page
import java.time.Instant
import java.util.*

data class TradeStatsRequestResponseDto(
    val id: UUID,
    val startMonth: String,
    val endMonth: String,
    val isPulled: Boolean,
    val pulledAt: Instant?,
) {
    companion object {
        fun valueOf(tradeStatsRequestEntity: TradeStatsRequestEntity) =
            TradeStatsRequestResponseDto(
                id = tradeStatsRequestEntity.id,
                startMonth = tradeStatsRequestEntity.startMonth,
                endMonth = tradeStatsRequestEntity.endMonth,
                isPulled = tradeStatsRequestEntity.isPulled,
                pulledAt = tradeStatsRequestEntity.pulledAt,
            )
    }
}

fun Page<TradeStatsRequestEntity>.toPageResponseDto() =
    PaginationItems(
        page = Pagination.valueOf(this),
        items = this.content.map { TradeStatsRequestResponseDto.valueOf(it) },
    )
