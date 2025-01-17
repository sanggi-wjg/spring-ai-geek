package com.raynor.geek.api.document

import org.springframework.ai.document.Document

fun List<Document>.toPrompt(): String {
    return this.joinToString("\n\n") {
        val metadataString = it.metadata.entries.joinToString(", ") { (key, value) ->
            "$key:$value"
        }
        "${it.text}\n<Metadata $metadataString>"
    }
}
