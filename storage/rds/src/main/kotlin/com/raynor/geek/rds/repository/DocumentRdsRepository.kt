package com.raynor.geek.rds.repository

import com.raynor.geek.rds.entity.DocumentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DocumentRdsRepository : JpaRepository<DocumentEntity, UUID>
