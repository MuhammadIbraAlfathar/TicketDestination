package com.example.ticketdestination.repository

import com.example.ticketdestination.model.FakeData
import com.example.ticketdestination.model.OrderTicket
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TicketRepository {
    private val buyTicket = mutableListOf<OrderTicket>()

    init {
        if (buyTicket.isEmpty()) {
            FakeData.dummyTicket.forEach {
                buyTicket.add(OrderTicket(it, 0))
            }
        }
    }

    fun getAllTicket(): Flow<List<OrderTicket>> {
        return flowOf(buyTicket)
    }

    fun getTicketById(tickedId: Long): OrderTicket {
        return buyTicket.first {
            it.ticket.id == tickedId
        }
    }

    fun updateBuyTicket(ticketId: Long, newCountValue: Int): Flow<Boolean> {
        val index = buyTicket.indexOfFirst {
            it.ticket.id == ticketId
        }
        val result = if (index >= 0) {
            val buyTickets = buyTicket[index]
            buyTicket[index] = buyTickets.copy(ticket = buyTickets.ticket, count = newCountValue)
            true
        } else {
            false
        }
        return  flowOf(result)
    }

    fun getAddedBuyTicket(): Flow<List<OrderTicket>> {
        return getAllTicket()
            .map { orderTicket ->
                orderTicket.filter {
                    it.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: TicketRepository? = null

        fun getInstance(): TicketRepository =
            instance ?: synchronized(this) {
                TicketRepository().apply {
                    instance = this
                }
            }
    }
}