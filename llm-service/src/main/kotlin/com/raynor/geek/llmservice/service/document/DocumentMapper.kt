package com.raynor.geek.llmservice.service.document

import org.springframework.ai.document.Document

fun List<Document>.toFlattenString(): String {
    return this.joinToString("\n\n") {
        val metadataString = it.metadata.entries.joinToString(", ") { (key, value) ->
            "$key:$value"
        }
        "${it.text}\n<Metadata $metadataString>"
    }
}
