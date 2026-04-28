package com.example.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.model.Admin
import com.example.model.AdminLoginRequest
import com.example.model.AdminRegisterRequest
import com.example.model.Admins
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class AdminService {

    fun register(request: AdminRegisterRequest): Admin? = transaction {
        val passwordCharArray = request.password.toCharArray()
        val hashedPassword = BCrypt.withDefaults().hashToString(12, passwordCharArray)

        val result = Admins.insert {
            it[name] = request.name
            it[surname] = request.surname
            it[email] = request.email
            it[password] = hashedPassword
        }

        val adminId = result[Admins.id]
        getAdminById(adminId)
    }

    fun login(request: AdminLoginRequest): Admin? = transaction {
        val admin = getAdminByEmail(request.email)
        if (admin == null) {
            return@transaction null
        }

        val isPasswordValid = BCrypt.verifyer()
            .verify(request.password.toCharArray(), admin.password)
            .verified

        if (isPasswordValid) {
            admin
        }
        else {
            null
        }
    }

    fun getAdminById(adminId: Int): Admin? = transaction {
        Admins.select { Admins.id eq adminId }
            .map {
                Admin(
                    id = it[Admins.id],
                    name = it[Admins.name],
                    surname = it[Admins.surname],
                    email = it[Admins.email],
                    password = it[Admins.password],
                )
            }.singleOrNull()
    }

    fun getAdminByEmail(email: String): Admin? = transaction {
        Admins.select { Admins.email eq email }
            .map {
                Admin(
                    id = it[Admins.id],
                    name = it[Admins.name],
                    surname = it[Admins.surname],
                    email = it[Admins.email],
                    password = it[Admins.password],
                )
            }.singleOrNull()
    }
}