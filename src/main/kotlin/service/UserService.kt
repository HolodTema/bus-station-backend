package com.example.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.model.User
import com.example.model.UserRegisterRequest
import com.example.model.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserService {

    fun register(request: UserRegisterRequest): User? = transaction {
        val passwordCharArray = request.password.toCharArray()
        val hashedPassword = BCrypt.withDefaults().hashToString(12, passwordCharArray)

        val result = Users.insert {
            it[name] = request.name
            it[surname] = request.surname
            it[email] = request.email
            it[password] = hashedPassword
        }

        val userId = result[Users.id]
        getUserById(userId)
    }

    fun login(email: String, password: String): User? = transaction {
        val user = getUserByEmail(email)
        if (user == null) {
            return@transaction null
        }

        val isPasswordValid = BCrypt.verifyer()
            .verify(password.toCharArray(), user.password)
            .verified

        if (isPasswordValid) {
            user
        }
        else {
            null
        }
    }

    fun getUserById(userId: Int): User? = transaction {
        Users.select { Users.id eq userId }
            .map {
                User(
                    id = it[Users.id],
                    name = it[Users.name],
                    surname = it[Users.surname],
                    email = it[Users.email],
                    password = it[Users.password],
                )
            }.singleOrNull()
    }

    fun getUserByEmail(email: String): User? = transaction {
        Users.select { Users.email eq email }
            .map {
                User(
                    id = it[Users.id],
                    name = it[Users.name],
                    surname = it[Users.surname],
                    email = it[Users.email],
                    password = it[Users.password],
                )
            }.singleOrNull()
    }
}