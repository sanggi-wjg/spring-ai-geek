package com.raynor.geek.api.controller

import com.raynor.geek.api.service.ChatService
import com.raynor.geek.api.service.RagService
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/assistant")
class AssistantController(
    private val chatService: ChatService,
    private val ragService: RagService,
) {
    @PostMapping("/chat")
    fun chat(): ChatResponse {
        return chatService.chat()
    }

    @PostMapping("/chat-stream", produces = ["text/event-stream"])
    fun chatStream(): Flux<ChatResponse> {
        return chatService.chatStream()
    }

    @PostMapping("/rag")
    fun rag(): ChatResponse {
        return ragService.simple()
    }
}