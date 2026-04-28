package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


object Buses : Table() {
    val id = integer("id").autoIncrement()
    val registrationNumber = varchar("registrationNumber", 6)
    val model = varchar("model", 200)
    val color = varchar("color", 7)
    val amountPlaces = integer("amountPlaces")

    override val primaryKey = PrimaryKey(Drivers.id)
}

@Serializable
data class Bus(
    val id: Int,
    val registrationNumber: String,
    val model: String,
    val color: String,
    val amountPlaces: Int
)

@Serializable
data class CreateBusRequest(
    val registrationNumber: String,
    val model: String,
    val color: String,
    val amountPlaces: Int
)

@Serializable
data class UpdateBusRequest(
    val id: Int,
    val registrationNumber: String,
    val model: String,
    val color: String,
    val amountPlaces: Int
)


