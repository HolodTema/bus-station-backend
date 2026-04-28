package com.example.service

import com.example.model.CreateStationRequest
import com.example.model.Station
import com.example.model.Stations
import com.example.model.UpdateStationRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class StationService {

    fun createStation(request: CreateStationRequest): Station? = transaction {
        val result = Stations.insert {
            it[Stations.name] = request.name
            it[Stations.hasBuilding] = request.hasBuilding
            it[Stations.hasRestroom] = request.hasRestroom
            it[Stations.hasCaffe] = request.hasCaffe
            it[Stations.hasTown] = request.hasTown
        }

        val stationId = result[Stations.id]
        getStationById(stationId)
    }

    fun updateStation(request: UpdateStationRequest): Boolean = transaction {
        val numberUpdatedRows = Stations.update(
            where = { Stations.id eq request.id }
        ) {
            it[name] = request.name
            it[hasBuilding] = request.hasBuilding
            it[hasRestroom] = request.hasRestroom
            it[hasCaffe] = request.hasCaffe
            it[hasTown] = request.hasTown
        }
        numberUpdatedRows > 0
    }

    fun deleteStation(stationId: Int): Boolean = transaction {
        val numberDeletedRows = Stations.deleteWhere {
            Stations.id eq stationId
        }
        numberDeletedRows > 0
    }

    fun getStationById(stationId: Int): Station? = transaction {
        Stations.select { Stations.id eq stationId }
            .map { row ->
                Station(
                    id = row[Stations.id],
                    name = row[Stations.name],
                    hasBuilding = row[Stations.hasBuilding],
                    hasRestroom = row[Stations.hasRestroom],
                    hasCaffe = row[Stations.hasCaffe],
                    hasTown = row[Stations.hasTown],
                )
            }.singleOrNull()
    }

    fun getAllStations(): List<Station> = transaction {
        Stations.selectAll()
            .map { row ->
                Station(
                    id = row[Stations.id],
                    name = row[Stations.name],
                    hasBuilding = row[Stations.hasBuilding],
                    hasRestroom = row[Stations.hasRestroom],
                    hasCaffe = row[Stations.hasCaffe],
                    hasTown = row[Stations.hasTown],
                )
            }
    }
}