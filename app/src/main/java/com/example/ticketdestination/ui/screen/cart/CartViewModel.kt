package com.example.ticketdestination.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketdestination.model.OrderTicket
import com.example.ticketdestination.repository.TicketRepository
import com.example.ticketdestination.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(private val repository: TicketRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> =  MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>> get() = _uiState

    fun getAddedBuyTicket() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedBuyTicket()
                .collect{buyTicket ->
                    val totalPrice = buyTicket.sumOf { it.ticket.priceTicket * it.count }
                    _uiState.value = UiState.Success(CartState(buyTicket, totalPrice))
                }
        }
    }

    fun updateBuyTicket(ticketId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateBuyTicket(ticketId, count)
                .collect {
                    if (it) {
                        getAddedBuyTicket()
                    }
                }
        }
    }
}