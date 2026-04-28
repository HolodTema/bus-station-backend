package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


data object Stations: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val hasBuilding = bool("hasBuilding").default(false)
    val hasRestroom = bool("hasRestroom").default(false)
    val hasCaffe = bool("hasCaffe").default(false)
    val hasTown = bool("hasTown").default(false)

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Station(
    val id: Int,
    val name: String,
    val hasBuilding: Boolean,
    val hasRestroom: Boolean,
    val hasCaffe: Boolean,
    val hasTown: Boolean
)
