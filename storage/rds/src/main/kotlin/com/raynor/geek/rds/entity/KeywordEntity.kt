package com.raynor.geek.rds.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(
    name = "keyword", schema = "public",
    indexes = [
        Index(name = "idx_keyword_001", columnList = "keyword")
    ],
    uniqueConstraints = [
        UniqueConstraint(name = "unq_keyword_001", columnNames = ["keyword"]),
    ],
)
class KeywordEntity(
    keyword: String,
) : PrimaryKey() {

    @NotNull
    @Column(name = "keyword", nullable = false, length = 64)
    var keyword: String = keyword
        private set
}
