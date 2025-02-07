package com.raynor.geek.client.datagokr

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties

@ConditionalOnProperty(prefix = "secret.datagokr", name = ["key"])
@ConfigurationProperties(prefix = "secret.datagokr")
data class DataGoKrAPIProperty(
    val key: String,
)
