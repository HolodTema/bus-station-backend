package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val surname = varchar("surname", 50)
    val email = varchar("email", 100)
    val password = varchar("password", 100)

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
)

@Serializable
data class UserRegisterRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)

@Serializable
data class UserLoginRequest(
    val email: String,
    val password: String
)

