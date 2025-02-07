package com.raynor.geek.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.xml.Jaxb2XmlDecoder


@Configuration
class FeignXmlConfig {

    @Bean
    fun feignDecoder() = Jaxb2XmlDecoder()
}
