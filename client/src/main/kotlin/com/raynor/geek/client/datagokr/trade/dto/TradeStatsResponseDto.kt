package com.raynor.geek.client.datagokr.trade.dto

data class TradeStatsResponseDto(
    val response: Response
) {
    data class Response(
        val header: Header,
        val body: Body,
    ) {
        data class Header(
            val resultCode: String,
            val resultMsg: String,
        )

        data class Body(
            val items: ItemList,
        ) {
            data class ItemList(
                val item: List<Item>,
            ) {
                data class Item(
                    val balPayments: String,
                    val expDlr: String,
                    val hsCd: String,
                    val impDlr: String,
                    val impWgt: String,
                    val statCd: String,
                    val statCdCntnKor1: String,
                    val statKor: String,
                    val year: String,
                )
            }
        }
    }
}
