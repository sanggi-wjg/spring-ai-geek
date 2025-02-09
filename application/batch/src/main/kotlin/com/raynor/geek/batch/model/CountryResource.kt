package com.raynor.geek.batch.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CountryResource(
    val name: String,

    @JsonProperty("country-code")
    val countryCode: String,

    @JsonProperty("alpha-2")
    val alpha2: String,

    @JsonProperty("is-syncable")
    val isSyncable: Boolean,
)
