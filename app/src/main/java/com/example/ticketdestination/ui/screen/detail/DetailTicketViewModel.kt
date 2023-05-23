package com.example.ticketdestination.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketdestination.model.OrderTicket
import com.example.ticketdestination.model.TicketDestination
import com.example.ticketdestination.repository.TicketRepository
import com.example.ticketdestination.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailTicketViewModel(private val repository: TicketRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderTicket>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderTicket>>
        get() = _uiState

    fun getTicketById(ticketId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getTicketById(ticketId))
        }
    }

    fun addToCart(ticket: TicketDestination, count: Int) {
        viewModelScope.launch {
            repository.updateBuyTicket(ticket.id, count)
        }
    }
}