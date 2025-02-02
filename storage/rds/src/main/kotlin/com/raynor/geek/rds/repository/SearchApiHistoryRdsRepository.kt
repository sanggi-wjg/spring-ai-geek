package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.SearchApiHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SearchApiHistoryRdsRepository : JpaRepository<SearchApiHistoryEntity, UUID>,
    JpaSpecificationExecutor<SearchApiHistoryEntity>