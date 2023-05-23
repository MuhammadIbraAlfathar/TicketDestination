package com.example.ticketdestination.ui.screen.cart

import com.example.ticketdestination.model.OrderTicket

data class CartState(
    val buyTicket: List<OrderTicket>,
    val totalPrice: Long,
)