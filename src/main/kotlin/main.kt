package com.example

import com.example.plugin.configureJWT
import io.ktor.server.engine.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureJWT()
}
