package com.raynor.geek.batch.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynor.geek.batch.model.CountryResource
import com.raynor.geek.rds.entity.trade.CountryEntity
import com.raynor.geek.rds.repository.CountryRdsRepository
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class CountryService(
    private val objectMapper: ObjectMapper,
    private val countryRdsRepository: CountryRdsRepository,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun syncCountryIfNotExists(): List<CountryEntity> {
        val countryResource = loadCountryResource()
        val countries = countryRdsRepository.findAll()
        val codes = countries.map { it.code }
        val newCountries = countryResource.filter { it.countryCode !in codes }

        return if (newCountries.isNotEmpty()) {
            logger.info("Insert new countries: {}", newCountries)
            countryRdsRepository.saveAll(newCountries.toCountryEntity())
        } else {
            emptyList()
        }
    }

    private fun loadCountryResource(): List<CountryResource> {
        val resource = ClassPathResource("iso3166/slim-2.json")

        return resource.inputStream.use {
            objectMapper.readValue(it, Array<CountryResource>::class.java).toList()
        }
    }

    private fun List<CountryResource>.toCountryEntity(): List<CountryEntity> {
        return this.map {
            CountryEntity(
                name = it.name,
                code = it.countryCode,
                alpha2 = it.alpha2,
            )
        }
    }
}