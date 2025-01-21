package com.raynor.geek.client.slack

import com.raynor.geek.client.slack.dto.SlackWebHookRequestDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@ConditionalOnProperty(prefix = "api.slack.webhook", name = ["own-channel"])
@FeignClient(value = "slack-api", url = "\${api.slack.webhook.own-channel}")
interface SlackAPI {
    // https://api.slack.com/apps

    @RequestMapping(
        method = [RequestMethod.POST],
        value = [""],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun sendWebHook(@RequestBody request: SlackWebHookRequestDto)
}
