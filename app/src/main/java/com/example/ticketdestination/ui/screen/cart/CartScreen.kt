package com.example.ticketdestination.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ticketdestination.R
import com.example.ticketdestination.ViewModelFactory
import com.example.ticketdestination.di.Injection
import com.example.ticketdestination.ui.common.UiState
import com.example.ticketdestination.ui.components.BuyButton
import com.example.ticketdestination.ui.components.CartItem

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onBuyButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getAddedBuyTicket()
            }
            is UiState.Success -> {
                CartContent(
                    state = uiState.data,
                    onTicketCountChanged = {ticketId, count ->
                        viewModel.updateBuyTicket(ticketId, count)
                    } ,
                    onBuyButtonClicked = onBuyButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onTicketCountChanged: (id: Long, count: Int) -> Unit,
    onBuyButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.buyTicket, key = {it.ticket.id}) {
                CartItem(
                    ticketId = it.ticket.id,
                    photoUrl = it.ticket.photoUrl,
                    title = it.ticket.title,
                    location = it.ticket.location,
                    totalPrice = it.ticket.priceTicket * it.count,
                    count = it.count,
                    onTicketCountChanged = onTicketCountChanged
                )
                Divider()
            }
        }
        BuyButton(
            text = "BUY",
            enabled = state.buyTicket.isNotEmpty(),
            onClick = {onBuyButtonClicked("")},
            modifier = Modifier.padding(16.dp)
        )
    }
}