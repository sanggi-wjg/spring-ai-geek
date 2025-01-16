package com.raynor.geek.rds.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ["com.raynor.geek.rds.entity"])
@EnableJpaRepositories(basePackages = ["com.raynor.geek.rds.repository"])
class DatabaseConfig {

    @Bean
    @ConfigurationProperties("storage.datasource.postgresql")
    fun dataSourceProperties(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    fun dataSource(
        dataSourceProperties: HikariConfig
    ): HikariDataSource {
        return HikariDataSource(dataSourceProperties)
    }
}