package org.yellowsunn.couponconcurrency.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class TestDataSourceConfig {
    @Primary
    @Bean(name = ["mainDataSource"])
    @ConfigurationProperties(prefix = "spring.main.datasource.hikari")
    fun mainDataSource(): HikariDataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["lockDataSource"])
    @ConfigurationProperties(prefix = "spring.lock.datasource.hikari")
    fun lockDataSource(): HikariDataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }
}
