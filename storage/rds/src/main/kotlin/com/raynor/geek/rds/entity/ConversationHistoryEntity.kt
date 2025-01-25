package com.raynor.geek.rds.entity

import com.raynor.geek.shared.enums.ConversationMessageType
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(
    name = "conversation_history", schema = "public",
    indexes = [
        Index(name = "idx_conversation_history_001", columnList = "conversation_id"),
    ],
    uniqueConstraints = [
        UniqueConstraint(name = "unq_conversation_history_001", columnNames = ["conversation_id"]),
    ]
)
class ConversationHistoryEntity(
    conversationId: String,
    messageType: ConversationMessageType,
    message: String,
    createdAt: Instant,
) : PrimaryKey() {

    @NotNull
    @Column(name = "conversation_id", nullable = false, length = 256, unique = true)
    var conversationId: String = conversationId
        private set

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false, length = 64)
    var messageType: ConversationMessageType = messageType
        private set

    @NotNull
    @Column(name = "message", nullable = false, length = 4096)
    var message: String = message
        private set

    @NotNull
    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = createdAt
        private set

    @NotNull
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = createdAt
        private set

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
        private set

    @NotNull
    @Column(name = "is_deleted")
    var isDeleted: Boolean = false
        private set

    fun delete() {
        val now = Instant.now()
        this.updatedAt = now
        this.deletedAt = now
        this.isDeleted = true
    }
}
