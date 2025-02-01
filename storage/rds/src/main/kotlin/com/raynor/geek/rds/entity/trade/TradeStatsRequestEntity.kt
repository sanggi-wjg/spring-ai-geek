package com.raynor.geek.rds.entity.trade

import com.raynor.geek.rds.entity.PrimaryKey
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.Instant

@Entity
@Table(
    name = "trade_stats_request", schema = "public",
    uniqueConstraints = [
        UniqueConstraint(
            name = "unq_trade_stats_request_001",
            columnNames = ["country_id", "start_month", "end_month"],
        ),
    ]
)
class TradeStatsRequestEntity(
    countryCode: CountryEntity,
    startMonth: String,
    endMonth: String,
    createdAt: Instant,
) : PrimaryKey() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false, updatable = false)
    var countryEntity: CountryEntity = countryCode
        private set

    @NotNull
    @Column(name = "start_month", nullable = false, length = 6, updatable = false)
    var startMonth: String = startMonth
        private set

    @NotNull
    @Column(name = "end_month", nullable = false, length = 6, updatable = false)
    var endMonth: String = endMonth
        private set

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = createdAt
        private set

    @NotNull
    @Column(name = "is_pulled", nullable = false)
    var isPulled: Boolean = false
        private set

    @NotNull
    @Column(name = "pulled_at", nullable = false)
    var pulledAt: Instant = createdAt
        private set

    fun pulled() {
        this.isPulled = true
        this.pulledAt = Instant.now()
    }
}
