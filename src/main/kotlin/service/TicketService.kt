package com.example.service

import com.example.model.CreateTicketRequest
import com.example.model.Ticket
import com.example.model.Tickets
import com.example.model.UpdateTicketRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TicketService {

    fun createTicket(request: CreateTicketRequest): Ticket? = transaction {
        val result = Tickets.insert {
            it[Tickets.voyageId] = request.voyageId
            it[Tickets.userId] = request.userId
            it[Tickets.placeNumber] = request.placeNumber
            it[Tickets.hasBaggage] = request.hasBaggage
        }

        val ticketId = result[Tickets.id]
        getTicketById(ticketId)
    }

    fun updateTicket(request: UpdateTicketRequest): Boolean = transaction {
        val numberUpdatedRows = Tickets.update(
            where = { Tickets.id eq request.id }
        ) {
            it[id] = request.id
            it[voyageId] = request.voyageId
            it[userId] = request.userId
            it[placeNumber] = request.placeNumber
            it[hasBaggage] = request.hasBaggage
        }
        numberUpdatedRows > 0
    }

    fun deleteTicket(ticketId: Int): Boolean = transaction {
        val numberDeletedRows = Tickets.deleteWhere {
            Tickets.id eq ticketId
        }
        numberDeletedRows > 0
    }

    fun getTicketById(ticketId: Int): Ticket? = transaction {
        Tickets.select { Tickets.id eq ticketId }
            .map { row ->
                Ticket(
                    id = row[Tickets.id],
                    voyageId = row[Tickets.voyageId],
                    userId = row[Tickets.userId],
                    placeNumber = row[Tickets.placeNumber],
                    hasBaggage = row[Tickets.hasBaggage],
                )
            }.singleOrNull()
    }

    fun getAllTickets(): List<Ticket> = transaction {
        Tickets.selectAll()
            .map { row ->
                Ticket(
                    id = row[Tickets.id],
                    voyageId = row[Tickets.voyageId],
                    userId = row[Tickets.userId],
                    placeNumber = row[Tickets.placeNumber],
                    hasBaggage = row[Tickets.hasBaggage],
                )
            }
    }
}
