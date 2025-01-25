package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.ConversationHistoryEntity
import org.springframework.data.domain.Limit
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ConversationHistoryRdsRepository : JpaRepository<ConversationHistoryEntity, UUID> {
    fun findByConversationId(conversationId: String): ConversationHistoryEntity?
    fun findAllByConversationId(conversationId: String): List<ConversationHistoryEntity>
    fun findByConversationIdOrderByCreatedAt(conversationId: String, limit: Limit): List<ConversationHistoryEntity>
    fun findFirstByConversationIdOrderByIdDesc(conversationId: String): ConversationHistoryEntity?
}