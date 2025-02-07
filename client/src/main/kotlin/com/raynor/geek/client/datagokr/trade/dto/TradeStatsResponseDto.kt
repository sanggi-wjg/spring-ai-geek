package com.raynor.geek.client.datagokr.trade.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "response")
data class TradeStatsResponseDto(
    @field:JacksonXmlProperty(localName = "header")
    val header: Header,

    @field:JacksonXmlProperty(localName = "body")
    val body: Body
) {
    data class Header(
        @field:JacksonXmlProperty(localName = "resultCode")
        val resultCode: String,

        @field:JacksonXmlProperty(localName = "resultMsg")
        val resultMsg: String
    )

    data class Body(
        @field:JacksonXmlElementWrapper(useWrapping = true) // 리스트 매핑을 위해 설정
        @field:JacksonXmlProperty(localName = "items")
        val items: List<Item>,
    ) {
        data class Item(
            @field:JacksonXmlProperty(localName = "balPayments")
            val balPayments: String,

            @field:JacksonXmlProperty(localName = "expDlr")
            val expDlr: String,

            @field:JacksonXmlProperty(localName = "expWgt")
            val expWgt: String,

            @field:JacksonXmlProperty(localName = "hsCd")
            val hsCd: String,

            @field:JacksonXmlProperty(localName = "impDlr")
            val impDlr: String,

            @field:JacksonXmlProperty(localName = "impWgt")
            val impWgt: String,

            @field:JacksonXmlProperty(localName = "statCd")
            val statCd: String,

            @field:JacksonXmlProperty(localName = "statCdCntnKor1")
            val statCdCntnKor1: String,

            @field:JacksonXmlProperty(localName = "statKor")
            val statKor: String,

            @field:JacksonXmlProperty(localName = "year")
            val year: String,
        ) {
            val hsCdFirst2Digit by lazy { hsCd.substring(0, 2) }
            val hsCdFirst4Digit by lazy { hsCd.substring(0, 4) }
            val hsCdFirst6Digit by lazy { hsCd.substring(0, 6) }
        }
    }
}
