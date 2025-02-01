package com.raynor.geek.batch.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.raynor.geek.batch.model.Iso3166Country
import com.raynor.geek.rds.entity.trade.CountryEntity
import com.raynor.geek.rds.repository.CountryRdsRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class ApplicationEventHandler(
    private val objectMapper: ObjectMapper,
    private val countryRdsRepository: CountryRdsRepository,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventListener(ApplicationStartedEvent::class)
    fun onApplicationStartedEvent() {
        logger.info("[{}] Load countries", this::class.java.simpleName)
        upsertCountry()
    }

    private fun upsertCountry() {
        val isoCountries = loadIso3166Country()
        val count = countryRdsRepository.count()

        if (count == 0L) {
            countryRdsRepository.saveAll(isoCountries.toCountryEntity())
        } else if (count != isoCountries.size.toLong()) {
            logger.warn("[{}] iso3166 country count mismatch: {} != {}. Load new countries", this::class.java.simpleName, count, isoCountries.size)
            val countries = countryRdsRepository.findAll()
            val codes = countries.map { it.code }

            isoCountries.filter { it.countryCode !in codes }.also { newCountries ->
                countryRdsRepository.saveAll(newCountries.toCountryEntity())
            }
        }
    }

    private fun loadIso3166Country(): List<Iso3166Country> {
        val resource = ClassPathResource("iso3166/slim-2.json")

        return resource.inputStream.use {
            objectMapper.readValue(it, Array<Iso3166Country>::class.java).toList()
        }
    }

    private fun List<Iso3166Country>.toCountryEntity(): List<CountryEntity> {
        return this.map {
            CountryEntity(
                name = it.name,
                code = it.countryCode,
                alpha2 = it.alpha2,
            )
        }
    }
}
