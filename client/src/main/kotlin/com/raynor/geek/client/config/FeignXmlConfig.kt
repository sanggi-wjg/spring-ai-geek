package com.raynor.geek.client.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule
import feign.jackson.JacksonDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FeignXmlConfig {

    fun customXmlObjectMapper(): ObjectMapper =
        ObjectMapper().registerModules(
            JaxbAnnotationModule(),
        )

    @Bean
    fun feignDecoder() = JacksonDecoder(customXmlObjectMapper())
}
