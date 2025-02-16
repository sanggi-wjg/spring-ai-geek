package com.raynor.geek.rds.entity

import com.raynor.geek.shared.enums.SearchAPIType
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(
    name = "search_api_history", schema = "public",
    indexes = [
        Index(name = "idx_search_api_history_001", columnList = "query, search_api_type"),
    ]
)
class SearchApiHistoryEntity(
    query: String,
    searchAPIType: SearchAPIType,
    responseData: Map<String, Any>,
    createdAt: Instant,
) : PrimaryKey() {

    @NotNull
    @Column(name = "query", nullable = false, length = 512)
    var query: String = query
        private set

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "search_api_type", nullable = false, length = 64)
    var searchAPIType: SearchAPIType = searchAPIType
        private set

    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "response_data", nullable = false, columnDefinition = "jsonb")
    var responseData: Map<String, Any> = responseData
        private set

//    @Column(name = "topic")
//    var topic: String? = null
//        private set
//
//    @Column(name = "keywords")
//    var keywords: String? = null
//        private set

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = createdAt
        private set

//    @NotNull
//    @Column(name = "updated_at", nullable = false)
//    var updatedAt: Instant = createdAt
//        private set
//
//    @Column(name = "deleted_at")
//    var deletedAt: Instant? = null
//        private set
//
//    @NotNull
//    @Column(name = "is_deleted")
//    var isDeleted: Boolean = false
//        private set
//
//    fun delete() {
//        deletedAt = Instant.now()
//        isDeleted = true
//    }
}
