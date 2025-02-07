package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.trade.HsCodeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HsCodeRdsRepository : JpaRepository<HsCodeEntity, UUID>
