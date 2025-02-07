package com.raynor.geek.rds.repository.helper

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.ComparableExpression
import com.raynor.geek.shared.enums.SortDirection

fun ComparableExpression<*>.orderDirection(sortDirection: SortDirection): OrderSpecifier<*> {
    return if (sortDirection == SortDirection.ASC) {
        this.asc()
    } else {
        this.desc()
    }
}
