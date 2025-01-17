package com.raynor.geek.client.tavily

import com.raynor.geek.client.tavily.dto.TavilySearchRequestDto
import com.raynor.geek.client.tavily.dto.TavilySearchResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "tavily-api", url = "\${api.tavily.url}")
interface TavilyAPI {
    /* https://docs.tavily.com/docs/rest-api/api-reference */

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/search"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun search(@RequestBody request: TavilySearchRequestDto): TavilySearchResponseDto
}
