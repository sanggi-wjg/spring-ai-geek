package com.raynor.geek.client.naver

import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "naver-open-api", url = "\${api.naver.url}")
interface NaverOpenAPI {
    /* https://developers.naver.com/docs/serviceapi/search/news/news.md#%EB%89%B4%EC%8A%A4 */

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/v1/search/news.json"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun searchNews(
        @RequestHeader("X-Naver-Client-Id") clientId: String,
        @RequestHeader("X-Naver-Client-Secret") clientSecret: String,
        @RequestParam("query") query: String,
        @RequestParam("display") display: Int = 10,
        @RequestParam("start") start: Int = 1,
        @RequestParam("sort") sort: String = "sim",
    ): NaverNewsResponseDto
}