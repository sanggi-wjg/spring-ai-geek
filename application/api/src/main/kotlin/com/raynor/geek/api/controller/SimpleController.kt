package com.raynor.geek.api.controller

import com.raynor.geek.api.service.ChatSimpleService
import com.raynor.geek.api.service.RagSimpleService
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/simple")
class SimpleController(
    private val chatSimpleService: ChatSimpleService,
    private val ragSimpleService: RagSimpleService,
) {
    @PostMapping("/chat")
    fun chat(): ChatResponse {
        return chatSimpleService.chat()
    }

    @PostMapping("/chat-stream", produces = ["text/event-stream"])
    fun chatStream(): Flux<ChatResponse> {
        return chatSimpleService.chatStream()
    }

    @PostMapping("/rag")
    fun rag(): ChatResponse {
        return ragSimpleService.simple()
    }
}