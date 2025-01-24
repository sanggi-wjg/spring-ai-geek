package com.raynor.geek.llmservice.service.document

import com.raynor.geek.rds.entity.DocumentEntity
import org.springframework.ai.document.Document

fun List<Document>.toFlattenString(): String {
    return this.joinToString("\n\n") {
        val metadataString = it.metadata.entries.joinToString(", ") { (key, value) ->
            "$key:$value"
        }
        "${it.text}\n<Metadata $metadataString>"
    }
}

fun List<DocumentEntity>.toDocument(): List<Document> {
    return this.map { entity ->
        Document.builder()
            .id(entity.id.toString())
            .text(entity.content)
            .apply { entity.metadata?.let { this.metadata(it) } }
            .build()
    }
}
