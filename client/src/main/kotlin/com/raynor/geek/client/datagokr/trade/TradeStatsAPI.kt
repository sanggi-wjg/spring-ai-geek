package com.raynor.geek.client.datagokr.trade

import com.raynor.geek.client.config.FeignXmlConfig
import com.raynor.geek.client.datagokr.trade.dto.TradeStatsResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    value = "datagokr-trade-stats",
    url = "\${api.datagokr.trade-stats.url}",
    configuration = [FeignXmlConfig::class]
)
interface TradeStatsAPI {
    // https://www.data.go.kr/data/15100475/openapi.do

    @RequestMapping(
        method = [RequestMethod.GET],
        value = [""],
        produces = [MediaType.APPLICATION_XML_VALUE],
        consumes = [MediaType.APPLICATION_XML_VALUE],
    )
    fun getTradeStats(
        @RequestParam("serviceKey") serviceKey: String,
        @RequestParam("strtYymm") startMonth: String,
        @RequestParam("endYymm") endMonth: String,
        @RequestParam("cntyCd") countryCode: String,
    ): TradeStatsResponseDto
}
