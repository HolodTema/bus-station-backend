package com.example.plugin

import io.ktor.server.application.Application

fun Application.configureDatabase() {
    val url = environment.config.property("database.url").getString()
    val driver = environment.config.property("database.driverClassName").getString()
    val maxPoolSize = environment.config.property("database.maxPoolSize").getString().toInt()
}