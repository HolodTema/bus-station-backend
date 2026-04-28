plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    kotlin("plugin.serialization") version "2.3.0"
    application
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}
dependencies {
    // to convert JSON from requests to data classes
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")

    // Authentication & JWT
    implementation("io.ktor:ktor-server-auth:2.3.0")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.0")

    // to use application.yaml file
    implementation("io.ktor:ktor-server-config-yaml")

    // To hash passwords with BCrypt algorithm
    implementation("at.favre.lib:bcrypt:0.10.2")

    // Exposed ORM to work with SQLITE DB
    implementation("org.jetbrains.exposed:exposed-core:0.44.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.44.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.44.1")
    // to work with datetime fields of PostgreSQL and Exposed ORM
    // HikariCP
    implementation("com.zaxxer:HikariCP:5.1.0")
    // SQLite драйвер
    implementation("org.xerial:sqlite-jdbc:3.47.0.0")

    // Ktor core
    implementation("io.ktor:ktor-server-core-jvm")
    // Netty is server which runs Ktor backend (like apache server or nginx etc)
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:1.5.21")
    implementation("io.ktor:ktor-server-core")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.3.20")
}
