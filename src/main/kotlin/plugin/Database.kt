package com.example.plugin

import com.example.model.Admins
import com.example.model.Buses
import com.example.model.Drivers
import com.example.model.LicenseCategories
import com.example.model.Stations
import com.example.model.Tickets
import com.example.model.Users
import com.example.model.Voyages
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val url = environment.config.property("database.url").getString()
    val driver = environment.config.property("database.driverClassName").getString()
    val maxPoolSize = environment.config.property("database.maxPoolSize").getString().toInt()

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = url
        driverClassName = driver
        maximumPoolSize = maxPoolSize
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }

    val dataSource = HikariDataSource(hikariConfig)

    Database.connect(dataSource)

    transaction {
        SchemaUtils.create(
            Admins,
            Users,
            Drivers,
            Buses,
            LicenseCategories,
            Stations,
            Voyages,
            Tickets
        )
    }
}