package com.raynor.geek.api.controller.dto.response

import com.raynor.geek.rds.entity.trade.CountryEntity
import org.springframework.data.domain.Page
import java.util.*

data class CountryResponseDto(
    val id: UUID,
    val code: String,
    val name: String,
    val alpha2: String,
) {
    companion object {
        fun valueOf(countryEntity: CountryEntity) =
            CountryResponseDto(
                id = countryEntity.id,
                code = countryEntity.code,
                name = countryEntity.name,
                alpha2 = countryEntity.alpha2,
            )
    }
}

fun Page<CountryEntity>.toPageResponseDto() =
    PaginationItems(
        page = Pagination.valueOf(this),
        items = this.content.map { CountryResponseDto.valueOf(it) }
    )
