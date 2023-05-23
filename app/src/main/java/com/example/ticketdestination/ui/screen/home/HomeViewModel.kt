package com.example.ticketdestination.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ticketdestination.model.OrderTicket
import com.example.ticketdestination.repository.TicketRepository
import com.example.ticketdestination.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TicketRepository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<OrderTicket>>> =  MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderTicket>>> get() = _uiState

    fun getAllTicket() {
        viewModelScope.launch {
            repository.getAllTicket()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}