package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Tickets : Table() {
    val id = integer("id").autoIncrement()
    val voyageId = integer("voyageId").references(Voyages.id)
    val userId = integer("userId").references(Users.id)
    val placeNumber = integer("placeNumber")
    val hasBaggage = bool("hasBaggage")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Ticket(
    val id: Int,
    val voyageId: Int,
    val userId: Int,
    val placeNumber: Int,
    val hasBaggage: Boolean
)

