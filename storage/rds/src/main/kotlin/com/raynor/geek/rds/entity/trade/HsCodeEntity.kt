package com.raynor.geek.rds.entity.trade

import com.raynor.geek.rds.entity.PrimaryKey
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.jetbrains.annotations.NotNull

@Entity
@Table(
    name = "hs_code", schema = "public",
    uniqueConstraints = [
        UniqueConstraint(name = "unq_hs_code_001", columnNames = ["code"]),
    ]
)
class HsCodeEntity(
    section: String,
    code: String,
    description: String,
    parent: String,
    level: Int
) : PrimaryKey() {

    @NotNull
    @Column(name = "section", nullable = false, length = 10, updatable = false)
    var section: String = section
        private set

    @NotNull
    @Column(name = "code", nullable = false, length = 10, updatable = false)
    var code: String = code
        private set

    @NotNull
    @Column(name = "description", nullable = false, length = 255, updatable = false)
    var description: String = description
        private set

    @NotNull
    @Column(name = "parent", nullable = false, length = 10, updatable = false)
    var parent: String = parent
        private set

    @NotNull
    @Column(name = "level", nullable = false, updatable = false)
    var level: Int = level
        private set
}
