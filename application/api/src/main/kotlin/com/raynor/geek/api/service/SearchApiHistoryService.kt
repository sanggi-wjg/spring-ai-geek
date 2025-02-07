package com.raynor.geek.api.service

import com.raynor.geek.rds.condition.SearchApiHistorySearchCondition
import com.raynor.geek.rds.entity.SearchApiHistoryEntity
import com.raynor.geek.rds.repository.SearchApiHistoryRdsRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchApiHistoryService(
    private val searchApiHistoryRdsRepository: SearchApiHistoryRdsRepository,
) {
    @Transactional(readOnly = true)
    fun getSearchApiHistories(condition: SearchApiHistorySearchCondition): Page<SearchApiHistoryEntity> {
        return searchApiHistoryRdsRepository.findPageByCondition(condition)
    }
}
