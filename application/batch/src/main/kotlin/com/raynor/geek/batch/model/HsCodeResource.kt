package com.raynor.geek.batch.model

import com.opencsv.bean.CsvBindByName
import com.raynor.geek.shared.annotations.NoArg

@NoArg
data class HsCodeResource(
    @CsvBindByName(column = "section", required = true)
    val section: String,

    @CsvBindByName(column = "hscode", required = true)
    val code: String,

    @CsvBindByName(column = "description", required = true)
    val description: String,

    @CsvBindByName(column = "parent", required = true)
    val parent: String,

    @CsvBindByName(column = "level", required = true)
    val level: Int,
)