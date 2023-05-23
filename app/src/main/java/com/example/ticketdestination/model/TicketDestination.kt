package com.example.ticketdestination.model

data class TicketDestination(
    val id: Long,
    val photoUrl: String,
    val title: String,
    val desc: String,
    val location: String,
    val priceTicket: Long,
)