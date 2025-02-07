package com.raynor.geek.batch.service

import com.opencsv.bean.CsvToBeanBuilder
import com.raynor.geek.batch.model.HsCodeResource
import com.raynor.geek.rds.entity.trade.HsCodeEntity
import com.raynor.geek.rds.repository.HsCodeRdsRepository
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.io.InputStreamReader

@Service
class HsCodeService(
    private val hsCodeRdsRepository: HsCodeRdsRepository,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun syncHsCodeIfNotExists() {
        val hsCodeResource = loadHsCodeResource()
        val hsCodes = hsCodeRdsRepository.findAll()
        val codes = hsCodes.map { it.code }
        val newHsCodes = hsCodeResource.filter { it.code !in codes }

        if (newHsCodes.isNotEmpty()) {
            logger.info("Insert new hsCodes: {}", newHsCodes)
            hsCodeRdsRepository.saveAll(newHsCodes.toHsCodeEntity())
        } else {
            emptyList()
        }
    }

    private fun loadHsCodeResource(): List<HsCodeResource> {
        val resource = ClassPathResource("hscode/hscode.csv")

        return resource.inputStream.use {
            CsvToBeanBuilder<HsCodeResource>(InputStreamReader(it))
                .withType(HsCodeResource::class.java)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse()
        }
    }

    private fun List<HsCodeResource>.toHsCodeEntity(): List<HsCodeEntity> {
        return this.map {
            HsCodeEntity(
                section = it.section,
                code = it.code,
                description = it.description,
                parent = it.parent,
                level = it.level,
            )
        }
    }
}