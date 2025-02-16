package com.raynor.geek.rds.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.raynor.geek.rds.condition.SearchApiHistorySearchCondition
import com.raynor.geek.rds.entity.QSearchApiHistoryEntity
import com.raynor.geek.rds.entity.SearchApiHistoryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SearchApiHistoryRdsRepository : JpaRepository<SearchApiHistoryEntity, UUID>, SearchApiHistoryQueryDslRepository

interface SearchApiHistoryQueryDslRepository {
    fun findPageByCondition(condition: SearchApiHistorySearchCondition): Page<SearchApiHistoryEntity>
}

class SearchApiHistoryQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : SearchApiHistoryQueryDslRepository {

    private val searchApiHistory = QSearchApiHistoryEntity.searchApiHistoryEntity

    override fun findPageByCondition(
        condition: SearchApiHistorySearchCondition,
    ): Page<SearchApiHistoryEntity> {
        val pageRequest = condition.paginationRequest.toPageRequest()

        val result = jpaQueryFactory
            .selectFrom(searchApiHistory)
            .where(
                condition.query?.let { searchApiHistory.query.containsIgnoreCase(it) },
            )
            .orderBy(searchApiHistory.id.desc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .setHint(this::class.java.simpleName, "findPageByCondition")
            .fetch()

        val count = jpaQueryFactory
            .select(searchApiHistory.id.count())
            .from(searchApiHistory)
            .where(
                condition.query?.let { searchApiHistory.query.containsIgnoreCase(it) },
            )
            .setHint(this::class.java.simpleName, "findPageByCondition")
            .fetchFirst()

        return PageImpl(result, pageRequest, count)
    }
}