package com.raynor.geek.rds.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(name = "document", schema = "public")
class DocumentEntity(
    content: String,
    metadata: Map<String, Any>?,
    createdAt: Instant,
) : PrimaryKey() {

    @NotNull
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String = content
        private set

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    var metadata: Map<String, Any>? = metadata
        private set

    @NotNull
    @Column(name = "created_at", nullable = false)
    var createdAt: Instant = createdAt
        private set

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
        private set

    @NotNull
    @Column(name = "is_deleted")
    var isDeleted: Boolean = false
        private set
}