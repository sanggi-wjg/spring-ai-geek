package com.raynor.geek.rds.entity.trade

import com.raynor.geek.rds.entity.PrimaryKey
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(
    name = "trade_stats", schema = "public",
    uniqueConstraints = [
        UniqueConstraint(name = "unq_trade_stats_001", columnNames = ["country_id", "month", "hs_code"]),
    ]
)
class TradeStatsEntity(
    country: CountryEntity,
    tradeStatsRequest: TradeStatsRequestEntity,
    month: String,
    hsCode: String,
    description: String,
    importWeight: BigDecimal,
    importAmount: BigDecimal,
    exportWeight: BigDecimal,
    exportAmount: BigDecimal,
    tradeBalance: BigDecimal,
    createdAt: Instant,
) : PrimaryKey() {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false, updatable = false)
    var countryEntity: CountryEntity = country
        private set

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_stats_request_id", nullable = false, updatable = false)
    var tradeStatsRequestEntity: TradeStatsRequestEntity = tradeStatsRequest
        private set

    @NotNull
    @Column(name = "month", nullable = false, length = 6, updatable = false)
    var month: String = month
        private set

    @NotNull
    @Column(name = "hs_code", nullable = false, length = 100, updatable = false)
    var hsCode: String = hsCode
        private set

    @NotNull
    @Column(name = "description", nullable = false, updatable = false)
    var description: String = description
        private set

    @NotNull
    @Column(name = "import_weight", nullable = false, updatable = false, scale = 4)
    var importWeight: BigDecimal = importWeight
        private set

    @Comment("달러 기준")
    @NotNull
    @Column(name = "import_amount", nullable = false, updatable = false, scale = 4)
    var importAmount: BigDecimal = importAmount
        private set

    @NotNull
    @Column(name = "export_weight", nullable = false, updatable = false, scale = 4)
    var exportWeight: BigDecimal = exportWeight
        private set

    @Comment("달러 기준")
    @NotNull
    @Column(name = "export_amount", nullable = false, updatable = false, scale = 4)
    var exportAmount: BigDecimal = exportAmount
        private set

    @Comment("무역 수지, 달러 기준")
    @NotNull
    @Column(name = "trade_balance", nullable = false, updatable = false, scale = 4)
    var tradeBalance: BigDecimal = tradeBalance
        private set

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = createdAt
        private set
}
