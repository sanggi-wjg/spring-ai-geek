package com.raynor.geek.api.controller

import com.raynor.geek.api.controller.dto.request.ChatRequestDto
import com.raynor.geek.llmservice.service.ChattingService
import jakarta.validation.Valid
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class ChatController(
    private val chattingService: ChattingService,
) {
    @PostMapping("/chat")
    fun chat(
        @Valid @RequestBody chatRequestDto: ChatRequestDto,
    ): ChatResponse {
        return chattingService.chat(
            chatRequestDto.userInput,
            chatRequestDto.conversationId,
            chatRequestDto.llmArgument,
        )
    }

    @PostMapping("/chat-stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun chatStream(
        @Valid @RequestBody chatRequestDto: ChatRequestDto,
    ): Flux<String> {
        return chattingService.chatStream(
            chatRequestDto.userInput,
            chatRequestDto.conversationId,
            chatRequestDto.llmArgument,
        ).map {
            it.results.first().output.text
        }
    }
}
