package com.raynor.geek.shared.utils

import com.raynor.geek.shared.enums.DateTimePattern
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtil {

    fun getCurrentDateTimeTextWith(pattern: DateTimePattern): String {
        return Instant.now().toLocalDateTime()
            .format(DateTimeFormatter.ofPattern(pattern.value))
    }

    fun getYearMonthList(): Set<String> {
        val result = mutableListOf<String>()

        val currentDateTime = Instant.now().toLocalDateTime()
        val currentYear = currentDateTime.year
        val startYear = 2000

        for (year in startYear..currentYear) {
            val endMonth = if (year == currentYear) currentDateTime.monthValue else 12
            for (month in 1..endMonth) {
                result.add("${year}${String.format("%02d", month)}")
            }
        }
        return result.toSet()
    }
}

fun Instant.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(this, ZoneId.of("UTC"))
}
