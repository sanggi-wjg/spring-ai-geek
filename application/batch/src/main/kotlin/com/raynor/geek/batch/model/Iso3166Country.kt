package com.raynor.geek.batch.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Iso3166Country(
    val name: String,
    @JsonProperty("country-code")
    val countryCode: String,
    @JsonProperty("alpha-2")
    val alpha2: String,
)
