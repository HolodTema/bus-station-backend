package com.example.model

import com.example.util.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime


object Voyages : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 200)
    val startStationId = integer("startStationId").references(Stations.id)
    val endStationId = integer("endStationId").references(Stations.id)
    val startTime = datetime("startTime")
    val endTime = datetime("endTime")
    val cost = integer("cost")
    val driverId = integer("driverId").references(Drivers.id)
    val busId = integer("busId").references(Buses.id)

    override val primaryKey = PrimaryKey(Tickets.id)
}

@Serializable
data class Voyage(
    val id: Int,
    val name: String,
    val startStationId: Int,
    val endStationId: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val endTime: LocalDateTime,
    val cost: Int,
    val driverId: Int,
    val busId: Int
)

