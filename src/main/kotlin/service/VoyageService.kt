package com.example.service

import com.example.model.CreateVoyageRequest
import com.example.model.UpdateVoyageRequest
import com.example.model.Voyage
import com.example.model.Voyages
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class VoyageService {

    fun createVoyage(request: CreateVoyageRequest): Voyage? = transaction {
        val result = Voyages.insert {
            it[Voyages.name] = request.name
            it[Voyages.startStationId] = request.startStationId
            it[Voyages.endStationId] = request.endStationId
            it[Voyages.startTime] = request.startTime
            it[Voyages.endTime] = request.endTime
            it[Voyages.cost] = request.cost
            it[Voyages.driverId] = request.driverId
            it[Voyages.busId] = request.busId
        }

        val voyageId = result[Voyages.id]
        getVoyageById(voyageId)
    }

    fun updateBus(request: UpdateVoyageRequest): Boolean = transaction {
        val numberUpdatedRows = Voyages.update(
            where = { Voyages.id eq request.id }
        ) {
            it[name] = request.name
            it[startStationId] = request.startStationId
            it[endStationId] = request.endStationId
            it[startTime] = request.startTime
            it[endTime] = request.endTime
            it[cost] = request.cost
            it[driverId] = request.driverId
            it[busId] = request.busId
        }
        numberUpdatedRows > 0
    }

    fun deleteVoyage(voyageId: Int): Boolean = transaction {
        val numberDeletedRows = Voyages.deleteWhere {
            Voyages.id eq voyageId
        }
        numberDeletedRows > 0
    }

    fun getVoyageById(voyageId: Int): Voyage? = transaction {
        Voyages.select { Voyages.id eq voyageId }
            .map { row ->
                Voyage(
                    id = row[Voyages.id],
                    name = row[Voyages.name],
                    startStationId = row[Voyages.startStationId],
                    endStationId = row[Voyages.endStationId],
                    startTime = row[Voyages.startTime],
                    endTime = row[Voyages.endTime],
                    cost = row[Voyages.cost],
                    driverId = row[Voyages.driverId],
                    busId = row[Voyages.busId],
                )
            }.singleOrNull()
    }

    fun getAllVoyages(): List<Voyage> = transaction {
        Voyages.selectAll()
            .map { row ->
                Voyage(
                    id = row[Voyages.id],
                    name = row[Voyages.name],
                    startStationId = row[Voyages.startStationId],
                    endStationId = row[Voyages.endStationId],
                    startTime = row[Voyages.startTime],
                    endTime = row[Voyages.endTime],
                    cost = row[Voyages.cost],
                    driverId = row[Voyages.driverId],
                    busId = row[Voyages.busId],
                )
            }
    }
}