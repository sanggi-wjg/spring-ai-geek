package com.raynor.geek.llmservice.advisor

import com.raynor.geek.rds.entity.ConversationHistoryEntity
import com.raynor.geek.rds.repository.ConversationHistoryRdsRepository
import com.raynor.geek.shared.enums.ConversationMessageType
import jakarta.persistence.EntityNotFoundException
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.data.domain.Limit
import java.time.Instant

class RdsChatMemory(
    private val repository: ConversationHistoryRdsRepository,
) : ChatMemory {

    override fun add(conversationId: String, messages: List<Message>) {
        messages.forEach {
            val messageType = when (it) {
                is SystemMessage -> ConversationMessageType.SYSTEM
                is UserMessage -> ConversationMessageType.USER
                is AssistantMessage -> ConversationMessageType.SYSTEM
                else -> TODO("not implemented")
            }

            repository.save(
                ConversationHistoryEntity(
                    conversationId = conversationId,
                    messageType = messageType,
                    message = it.text,
                    createdAt = Instant.now(),
                )
            )
        }
    }

    override fun get(conversationId: String, lastN: Int): List<Message> {
        return repository.findByConversationIdOrderByCreatedAt(conversationId, Limit.of(lastN)).map {
            when (it.messageType) {
                ConversationMessageType.SYSTEM -> SystemMessage(it.message)
                ConversationMessageType.USER -> UserMessage(it.message)
                ConversationMessageType.ASSISTANT -> AssistantMessage(it.message)
            }
        }
    }

    override fun clear(conversationId: String) {
        throw NotImplementedError("When did u use this?")
        repository.findByConversationId(conversationId)?.also {
            it.delete()
        } ?: throw EntityNotFoundException("history not found")
    }
}
