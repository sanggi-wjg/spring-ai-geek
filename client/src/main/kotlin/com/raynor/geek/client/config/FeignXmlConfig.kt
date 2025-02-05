package com.raynor.geek.client.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FeignXmlConfig {

    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper().registerModules(
            JaxbAnnotationModule(),
        )
}
