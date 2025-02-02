package com.raynor.geek.rds.entity.trade

import com.raynor.geek.rds.entity.PrimaryKey
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.jetbrains.annotations.NotNull

@Entity
@Table(
    name = "country", schema = "public",
    uniqueConstraints = [
        UniqueConstraint(name = "unq_country_001", columnNames = ["code"]),
        UniqueConstraint(name = "unq_country_002", columnNames = ["alpha2"]),
    ]
)
class CountryEntity(
    name: String,
    code: String,
    alpha2: String,
) : PrimaryKey() {

    @NotNull
    @Column(name = "name", nullable = false, length = 250, updatable = false)
    var name: String = name
        private set

    @NotNull
    @Column(name = "code", nullable = false, length = 3, updatable = false)
    var code: String = code
        private set

    @NotNull
    @Column(name = "alpha2", nullable = false, length = 2, updatable = false)
    var alpha2: String = alpha2
        private set
}
