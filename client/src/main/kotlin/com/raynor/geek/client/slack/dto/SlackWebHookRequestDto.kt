package com.raynor.geek.client.slack.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SlackWebHookRequestDto(
    val channel: String? = null,
    val text: String? = null,
    val blocks: List<Block>
) {
    companion object {
        fun builder() = Builder()
    }

    sealed class Block {
        data class HeaderBlock(
            val type: String = "header",
            val text: Text,
        ) : Block() {
            data class Text(
                val type: String,
                val text: String,
                val emoji: Boolean,
            )
        }

        data class ContextBlock(
            val type: String = "context",
            val elements: List<Element>,
        ) : Block() {
            data class Element(
                val type: String,
                val text: String,
            )
        }

        data class SectionBlock(
            val type: String = "section",
            val text: Text,
        ) : Block() {
            data class Text(
                val type: String,
                val text: String,
            )
        }

        data class DividerBlock(
            val type: String = "divider"
        ) : Block()
    }

    class Builder {
        private var channel: String? = null
        private var text: String? = null
        private val blocks: MutableList<Block> = mutableListOf()

        fun channel(channel: String) = apply { this.channel = channel }

        fun text(text: String) = apply { this.text = text }

        fun addHeader(type: SlackMessageType, text: String, emoji: Boolean) = apply {
            blocks.add(
                Block.HeaderBlock(
                    text = Block.HeaderBlock.Text(
                        type = type.type,
                        text = text,
                        emoji = emoji
                    )
                )
            )
        }

        fun addContext(type: SlackMessageType, text: String) = apply {
            blocks.add(
                Block.ContextBlock(
                    elements = listOf(
                        Block.ContextBlock.Element(
                            type = type.type,
                            text = text
                        )
                    )
                )
            )
        }

        fun addSection(type: SlackMessageType, text: String) = apply {
            blocks.add(
                Block.SectionBlock(
                    text = Block.SectionBlock.Text(
                        type = type.type,
                        text = text
                    )
                )
            )
        }

        fun addDivider() = apply {
            blocks.add(
                Block.DividerBlock()
            )
        }

        fun build() = SlackWebHookRequestDto(
            channel = channel,
            text = text,
            blocks = blocks
        )
    }
}
