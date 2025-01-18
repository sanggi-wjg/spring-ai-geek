package com.raynor.geek.llm.repository

import com.raynor.geek.shared.annotations.VectorRepository
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore


@VectorRepository
abstract class BaseVectorRepository(
    private val vectorStore: VectorStore,
) {
    fun addDocuments(documents: List<Document>) {
        vectorStore.add(documents)
    }

    fun removeDocuments(ids: List<String>): Boolean {
        return vectorStore.delete(ids)?.get() ?: false
    }

    fun similaritySearch(
        query: String,
        topK: Int = 5
    ): List<Document> {
        val request = SearchRequest.builder()
            .query(query)
            .topK(topK)
            .build()
        return vectorStore.similaritySearch(request) ?: emptyList()
    }
}
