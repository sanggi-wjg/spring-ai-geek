package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.SearchHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SearchHistoryRdsRepository : JpaRepository<SearchHistoryEntity, UUID>,
    JpaSpecificationExecutor<SearchHistoryEntity>
