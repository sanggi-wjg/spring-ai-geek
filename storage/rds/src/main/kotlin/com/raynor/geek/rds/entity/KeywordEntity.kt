package com.raynor.geek.rds.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(
    name = "keyword", schema = "public",
    indexes = [
        Index(name = "idx_keyword_001", columnList = "keyword", unique = true)
    ],
)
class KeywordEntity(
    keyword: String,
) : PrimaryKey() {

    @NotNull
    @Column(name = "keyword", nullable = false, length = 64, unique = true)
    var keyword: String = keyword
        private set
}
