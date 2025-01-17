package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.SearchEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SearchRdsRepository : JpaRepository<SearchEntity, UUID>
