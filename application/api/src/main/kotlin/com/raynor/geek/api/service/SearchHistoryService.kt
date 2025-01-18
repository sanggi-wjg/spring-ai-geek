package com.raynor.geek.api.service

import com.raynor.geek.rds.condition.SearchHistorySearchCondition
import com.raynor.geek.rds.entity.SearchHistoryEntity
import com.raynor.geek.rds.repository.SearchHistoryRdsRepository
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class SearchHistoryService(
    private val searchHistoryRdsRepository: SearchHistoryRdsRepository,
) {

    fun getSearchHistories(
        condition: SearchHistorySearchCondition,
    ): Page<SearchHistoryEntity> {
        val spec = Specification<SearchHistoryEntity> { root, _, criteriaBuilder ->
            condition.query.let { criteriaBuilder.like(root.get("query"), "%${condition.query}%") }
        }

        return searchHistoryRdsRepository.findAll(spec, condition.paginationRequest.toPageRequest())
    }
}