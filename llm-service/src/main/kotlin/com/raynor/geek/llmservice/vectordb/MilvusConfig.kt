package com.raynor.geek.llmservice.vectordb

import io.milvus.client.MilvusServiceClient
import io.milvus.param.IndexType
import io.milvus.param.MetricType
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.milvus.MilvusVectorStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MilvusConfig {

    companion object {
        const val DEFAULT_DATABASE = "default"
        const val GEEK_COLLECTION = "geek"
    }

    @Bean(GEEK_COLLECTION)
    fun vectorStore(
        milvusClient: MilvusServiceClient,
        embeddingModel: EmbeddingModel,
    ): VectorStore {
        return MilvusVectorStore.builder(milvusClient, embeddingModel)
            .databaseName(DEFAULT_DATABASE)
            .collectionName(GEEK_COLLECTION)
            .initializeSchema(true)
            .autoId(false)
            .iDFieldName("doc_id")
            .contentFieldName("content")
            .metadataFieldName("metadata")
            .embeddingFieldName("embedding")
            .embeddingDimension(1024)
            .indexType(IndexType.IVF_FLAT)
            .metricType(MetricType.COSINE)
            .build()
    }
}