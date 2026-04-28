package com.example.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.model.Driver
import com.example.model.DriverLoginRequest
import com.example.model.DriverRegisterRequest
import com.example.model.Drivers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DriverService {

    fun register(request: DriverRegisterRequest): Driver? = transaction {
        val passwordCharArray = request.password.toCharArray()
        val hashedPassword = BCrypt.withDefaults().hashToString(12, passwordCharArray)

        val result = Drivers.insert {
            it[name] = request.name
            it[surname] = request.surname
            it[email] = request.email
            it[password] = hashedPassword
//            it[licenseCategoryId] = request.licenseCategoryId
            it[experienceYears] = request.experienceYears
        }

        val driverId = result[Drivers.id]
        getDriverById(driverId)
    }

    fun login(request: DriverLoginRequest): Driver? = transaction {
        val driver = getDriverByEmail(request.email)
        if (driver == null) {
            return@transaction null
        }

        val isPasswordValid = BCrypt.verifyer()
            .verify(request.password.toCharArray(), driver.password)
            .verified

        if (isPasswordValid) {
            driver
        } else {
            null
        }
    }

    fun getDriverById(driverId: Int): Driver? = transaction {
        Drivers.select { Drivers.id eq driverId }
            .map {
                Driver(
                    id = it[Drivers.id],
                    name = it[Drivers.name],
                    surname = it[Drivers.surname],
                    email = it[Drivers.email],
                    password = it[Drivers.password],
//                    licenseCategoryId = it[Drivers.licenseCategoryId],
                    experienceYears = it[Drivers.experienceYears],
                )
            }.singleOrNull()
    }

    fun getDriverByEmail(email: String): Driver? = transaction {
        Drivers.select { Drivers.email eq email }
            .map {
                Driver(
                    id = it[Drivers.id],
                    name = it[Drivers.name],
                    surname = it[Drivers.surname],
                    email = it[Drivers.email],
                    password = it[Drivers.password],
//                    licenseCategoryId = it[Drivers.licenseCategoryId],
                    experienceYears = it[Drivers.experienceYears],
                )
            }.singleOrNull()
    }
}