package com.example.ticketdestination.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ticketdestination.R
import com.example.ticketdestination.ViewModelFactory
import com.example.ticketdestination.di.Injection
import com.example.ticketdestination.ui.common.UiState
import com.example.ticketdestination.ui.components.BuyTicket
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
    val succesMessage = stringResource(id = R.string.succesMessage, state.buyTicket.count())
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Text(text = stringResource(id = R.string.menu_cart),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                ),
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier.weight(1f),
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
        BuyTicket(
            price = state.totalPrice,
            enabled = state.buyTicket.isNotEmpty(),
            onClick = {
                onBuyButtonClicked(succesMessage)
            }
        )
    }
}