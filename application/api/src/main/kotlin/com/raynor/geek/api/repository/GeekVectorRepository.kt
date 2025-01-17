package com.raynor.geek.api.repository

import com.raynor.geek.api.annotation.VectorRepository
import com.raynor.geek.api.vectordb.MilvusConfig
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Qualifier

@VectorRepository
class GeekVectorRepository(
    @Qualifier(MilvusConfig.GEEK_STORE) private val vectorStore: VectorStore
) {

    fun addDocuments(documents: List<Document>) {
        vectorStore.add(documents)
    }

    fun removeDocuments(ids: List<String>): Boolean {
        return vectorStore.delete(ids)?.get() ?: false
    }

    fun similaritySearch(query: String, topK: Int): List<Document> {
        val request = SearchRequest.builder().query(query).topK(topK).build()
        return vectorStore.similaritySearch(request)
            ?: emptyList()
    }

    fun getQaAdvisor(similarityThreshold: Double = 0.8, topK: Int = 5): QuestionAnswerAdvisor {
        val request = SearchRequest.builder()
            .similarityThreshold(similarityThreshold)
            .topK(topK)
            .build()
        return QuestionAnswerAdvisor(vectorStore, request)
    }
}