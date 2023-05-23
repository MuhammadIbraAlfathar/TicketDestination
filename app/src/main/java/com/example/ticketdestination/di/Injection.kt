package com.example.ticketdestination.di

import com.example.ticketdestination.repository.TicketRepository

object Injection {
    fun provideRepository(): TicketRepository {
        return TicketRepository.getInstance()
    }
}