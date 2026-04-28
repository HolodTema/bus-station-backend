package com.example.service

import com.example.model.Bus
import com.example.model.Buses
import com.example.model.CreateBusRequest
import com.example.model.UpdateBusRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class BusService {

    fun createBus(request: CreateBusRequest): Bus? = transaction {
        val result = Buses.insert {
            it[Buses.registrationNumber] = request.registrationNumber
            it[Buses.model] = request.model
            it[Buses.color] = request.color
            it[Buses.amountPlaces] = request.amountPlaces
        }

        val busId = result[Buses.id]
        getBusById(busId)
    }

    fun updateBus(request: UpdateBusRequest): Boolean = transaction {
        val numberUpdatedRows = Buses.update(
            where = { Buses.id eq request.id }
        ) {
            it[registrationNumber] = request.registrationNumber
            it[model] = request.model
            it[color] = request.color
            it[amountPlaces] = request.amountPlaces
        }
        numberUpdatedRows > 0
    }

    fun deleteBus(busId: Int): Boolean = transaction {
        val numberDeletedRows = Buses.deleteWhere {
            Buses.id eq busId
        }
        numberDeletedRows > 0
    }

    fun getBusById(busId: Int): Bus? = transaction {
        Buses.select { Buses.id eq busId }
            .map { row ->
                Bus(
                    id = row[Buses.id],
                    registrationNumber = row[Buses.registrationNumber],
                    model = row[Buses.model],
                    color = row[Buses.color],
                    amountPlaces = row[Buses.amountPlaces],
                )
            }.singleOrNull()
    }

    fun getAllBuses(): List<Bus> = transaction {
        Buses.selectAll()
            .map { row ->
                Bus(
                    id = row[Buses.id],
                    registrationNumber = row[Buses.registrationNumber],
                    model = row[Buses.model],
                    color = row[Buses.color],
                    amountPlaces = row[Buses.amountPlaces],
                )
            }
    }
}