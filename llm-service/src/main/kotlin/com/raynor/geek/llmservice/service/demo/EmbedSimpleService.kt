package com.raynor.geek.llmservice.service.demo

import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.stereotype.Service

@Service
class EmbedSimpleService(
    private val embeddingModel: EmbeddingModel,
) {
    fun embed(): FloatArray {
        return this.embeddingModel.embed("안녕? 자기 소개 해줘")
    }
}
