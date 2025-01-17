package com.raynor.geek.client.tavily

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties

@ConditionalOnProperty(prefix = "secret.tavily", name = ["key"])
@ConfigurationProperties(prefix = "secret.tavily")
data class TavilyProperty(
    val key: String,
)
