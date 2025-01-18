package com.raynor.geek.llm.repository

import com.raynor.geek.llm.vectordb.MilvusConfig
import com.raynor.geek.shared.annotations.VectorRepository
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Qualifier

@VectorRepository
class GeekVectorRepository(
    @Qualifier(MilvusConfig.GEEK_STORE) private val vectorStore: VectorStore
) : BaseVectorRepository(vectorStore)
