package com.raynor.geek.api.service

import com.raynor.geek.rds.condition.CountrySearchCondition
import com.raynor.geek.rds.entity.trade.CountryEntity
import com.raynor.geek.rds.repository.CountryRdsRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CountryService(
    private val countryRdsRepository: CountryRdsRepository,
) {
    @Transactional(readOnly = true)
    fun getCountries(condition: CountrySearchCondition): Page<CountryEntity> {
        return countryRdsRepository.findPageByCondition(condition)
    }
}
