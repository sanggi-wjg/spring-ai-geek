package com.raynor.geek.api.controller

import com.raynor.geek.api.service.TradeStatsRequestService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/trade-stats-requests")
class TradeStatsRequestController(
    private val tradeStatsRequestService: TradeStatsRequestService,
) {
}
