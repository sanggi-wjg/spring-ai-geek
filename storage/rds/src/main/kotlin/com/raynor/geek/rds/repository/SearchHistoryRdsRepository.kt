package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.SearchAPIHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SearchHistoryRdsRepository : JpaRepository<SearchAPIHistoryEntity, UUID>,
    JpaSpecificationExecutor<SearchAPIHistoryEntity>