package com.raynor.geek.api.service

import com.raynor.geek.api.enum.OllamaMyModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.api.OllamaOptions
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ChatSimpleService(
    private val llm: OllamaChatModel,
    private val embedSimpleService: EmbedSimpleService,
) {
    fun chat(): ChatResponse {
        val prompt = Prompt(
            "안녕? 자기 소개 해줘",
            OllamaOptions.builder().model(OllamaMyModel.EXAONE_3_5_8b.model).build()
        )
        return llm.call(prompt)
    }

    fun chatStream(): Flux<ChatResponse> {
        val prompt = Prompt("안녕? 자기 소개 해줘")
        return llm.stream(prompt)
    }
}
