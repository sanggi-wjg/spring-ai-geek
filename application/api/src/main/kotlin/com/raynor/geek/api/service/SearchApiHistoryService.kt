package com.raynor.geek.api.service

import com.raynor.geek.rds.condition.SearchApiHistorySearchCondition
import com.raynor.geek.rds.entity.SearchApiHistoryEntity
import com.raynor.geek.rds.repository.SearchApiHistoryRdsRepository
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SearchApiHistoryService(
    private val searchApiHistoryRdsRepository: SearchApiHistoryRdsRepository,
) {

    @Transactional(readOnly = true)
    fun getSearchApiHistories(
        condition: SearchApiHistorySearchCondition,
    ): Page<SearchApiHistoryEntity> {
        val spec = Specification<SearchApiHistoryEntity> { root, _, criteriaBuilder ->
            val predicate = mutableListOf<Predicate>()
            condition.query?.also { predicate.add(criteriaBuilder.like(root.get("query"), "%${it}%")) }
            criteriaBuilder.and(*predicate.toTypedArray())
        }

        return searchApiHistoryRdsRepository.findAll(spec, condition.paginationRequest.toPageRequest())
    }
}