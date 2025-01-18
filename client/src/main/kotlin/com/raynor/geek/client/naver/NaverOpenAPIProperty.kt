package com.raynor.geek.client.naver

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties

@ConditionalOnProperty(prefix = "secret.naver.openapi", name = ["client-id", "client-secret"])
@ConfigurationProperties(prefix = "secret.naver.openapi")
data class NaverOpenAPIProperty(
    val clientId: String,
    val clientSecret: String,
)
