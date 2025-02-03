package com.raynor.geek.api.controller

import com.raynor.geek.api.service.TradeStatsRequestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/v1/trade-stats-requests")
class TradeStatsRequestController(
    private val tradeStatsRequestService: TradeStatsRequestService,
) {

    @PutMapping("/{id}/sync")
    fun syncTradeStats(
        @PathVariable("id") id: UUID,
    ): ResponseEntity<Boolean> {
        // todo: impl idempotency
        return tradeStatsRequestService.syncTradeStats(id).let {
            ResponseEntity.created(URI.create("/api/v1/trade-stats")).body(true)
        }
    }
}
