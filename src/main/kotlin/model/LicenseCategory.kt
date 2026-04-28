package com.example.model

import org.jetbrains.exposed.sql.Table


object LicenseCategories : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val description = varchar("description", 200)

    override val primaryKey = PrimaryKey(id)
}


data class LicenseCategory(
    val id: Int,
    val name: String,
    val description: String
)

