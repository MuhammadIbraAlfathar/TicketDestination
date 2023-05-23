package com.example.ticketdestination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ticketdestination.repository.TicketRepository
import com.example.ticketdestination.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: TicketRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else {

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}