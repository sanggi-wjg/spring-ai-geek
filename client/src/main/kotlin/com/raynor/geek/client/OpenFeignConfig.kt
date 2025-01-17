package com.raynor.geek.client

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.raynor.geek.client"])
class OpenFeignConfig
