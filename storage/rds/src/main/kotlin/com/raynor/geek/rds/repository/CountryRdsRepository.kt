package com.raynor.geek.rds.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.raynor.geek.rds.condition.CountrySearchCondition
import com.raynor.geek.rds.entity.trade.CountryEntity
import com.raynor.geek.rds.entity.trade.QCountryEntity
import com.raynor.geek.rds.repository.helper.orderDirection
import com.raynor.geek.shared.enums.CountrySortBy
import org.hibernate.jpa.HibernateHints
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CountryRdsRepository : JpaRepository<CountryEntity, UUID>, CountryQueryDslRepository

interface CountryQueryDslRepository {
    fun findPageByCondition(condition: CountrySearchCondition): Page<CountryEntity>
}

class CountryQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : CountryQueryDslRepository {

    private val country = QCountryEntity.countryEntity

    override fun findPageByCondition(condition: CountrySearchCondition): Page<CountryEntity> {
        val pageRequest = condition.paginationRequest.toPageRequest()

        val result = jpaQueryFactory
            .selectFrom(country)
            .where(
                condition.code?.let { country.code.eq(it) },
                condition.name?.let { country.name.containsIgnoreCase(it) },
                condition.alpha2?.let { country.alpha2.eq(it) },
                condition.alpha2s?.let { country.alpha2.`in`(it) },
            )
            .orderBy(
                when (condition.sortBy) {
                    CountrySortBy.ID -> country.id
                    CountrySortBy.CODE -> country.code
                    CountrySortBy.NAME -> country.name
                    CountrySortBy.ALPHA2 -> country.alpha2
                }.orderDirection(
                    condition.sortDirection
                )
            )
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .setHint(HibernateHints.HINT_COMMENT, "${this::class.java}::findPageByCondition}")
            .fetch()

        val count = jpaQueryFactory
            .select(country.id.count())
            .from(country)
            .where(
                condition.code?.let { country.code.eq(it) },
                condition.name?.let { country.name.contains(it) },
                condition.alpha2?.let { country.alpha2.eq(it) },
                condition.alpha2s?.let { country.alpha2.`in`(it) },
            )
            .setHint(HibernateHints.HINT_COMMENT, "${this::class.java}::findPageByCondition}")
            .fetchFirst()

        return PageImpl(result, pageRequest, count)
    }
}
