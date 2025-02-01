package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.trade.CountryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CountryRdsRepository : JpaRepository<CountryEntity, UUID>
