package com.raynor.geek.llmservice.service.demo

import com.raynor.geek.llmservice.model.OllamaLLMArgument
import com.raynor.geek.llmservice.service.factory.OllamaFactory
import com.raynor.geek.shared.enums.OllamaMyModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
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
            OllamaFactory.create(
                OllamaLLMArgument(OllamaMyModel.EXAONE_3_5_8b)
            ),
        )
        return llm.call(prompt)
    }

    fun chatStream(): Flux<ChatResponse> {
        val prompt = Prompt(
            "안녕? 자기 소개 해줘",
            OllamaFactory.create(
                OllamaLLMArgument(OllamaMyModel.EXAONE_3_5_8b)
            ),
        )
        return llm.stream(prompt)
    }
}
