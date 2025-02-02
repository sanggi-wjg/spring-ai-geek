package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.trade.TradeStatsRequestEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TradeStatsRequestRdsRepository : JpaRepository<TradeStatsRequestEntity, UUID> {

    @Query(
        "SELECT t " +
                "FROM TradeStatsRequestEntity t INNER JOIN FETCH CountryEntity c ON t.country.id = c.id " +
                "ORDER BY c.id"
    )
    fun findAllWithCountry(page: Pageable): Page<TradeStatsRequestEntity>
}
