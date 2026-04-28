package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


object Drivers : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val surname = varchar("surname", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 200)
    val licenseCategoryId = integer("licenseCategoryId").references(LicenseCategories.id)
    val experienceYears = integer("experienceYears")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Driver(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val licenseCategoryId: Int,
    val experienceYears: Int
)

@Serializable
data class DriverRegisterRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val licenseCategoryId: Int,
    val experienceYears: Int
)

@Serializable
data class DriverLoginRequest(
    val email: String,
    val password: String
)
