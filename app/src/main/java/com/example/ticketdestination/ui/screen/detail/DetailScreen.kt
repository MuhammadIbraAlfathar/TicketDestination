package com.example.ticketdestination.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.ticketdestination.ViewModelFactory
import com.example.ticketdestination.di.Injection
import com.example.ticketdestination.ui.common.UiState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ticketdestination.R
import com.example.ticketdestination.ui.components.BuyButton
import com.example.ticketdestination.ui.components.TicketCounter
import com.example.ticketdestination.ui.theme.TicketDestinationTheme
import com.example.ticketdestination.utils.convertRupiah

@Composable
fun DetailScreen(
    ticketId: Long,
    viewModel: DetailTicketViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getTicketById(ticketId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    photoUrl = data.ticket.photoUrl,
                    title = data.ticket.title,
                    desc = data.ticket.desc,
                    location = data.ticket.location,
                    basePrice = data.ticket.priceTicket,
                    count = data.count,
                    onBackClick = navigateBack,
                    onAddToCart = {
                        viewModel.addToCart(data.ticket, it)
                        navigateToCart()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}


@Composable
fun DetailContent(
    photoUrl: String,
    title: String,
    desc: String,
    location: String,
    basePrice: Long,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var totalTicket by rememberSaveable {
        mutableStateOf(0L)
    }
    var buyTicketCount by rememberSaveable {
        mutableStateOf(count)
    }
    
    Column(modifier = modifier) {
        Column(modifier = modifier
            .verticalScroll(rememberScrollState())
            .weight(1f)
        ) {
            Box {
               AsyncImage(
                   model = photoUrl,
                   contentDescription = null,
                   contentScale = ContentScale.Crop,
                   modifier = modifier
                       .height(400.dp)
                       .fillMaxWidth()
                       .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
               )
                Icon(
                    imageVector = Icons.Default.ArrowBack ,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = convertRupiah(basePrice),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colors.secondary
                    ),
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.LightGray))
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            TicketCounter(
                id = 1,
                ticketCount = buyTicketCount,
                onTicketIncreased = {buyTicketCount++},
                onTicketDecreased = {if (buyTicketCount > 0) buyTicketCount--},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalTicket = basePrice * buyTicketCount
            BuyButton(
                text = totalTicket,
                enabled = buyTicketCount > 0,
                onClick =  {
                    onAddToCart(buyTicketCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3A)
@Composable
fun Preview(){
    TicketDestinationTheme {
        DetailContent(
            photoUrl = "",
            title = "Ragunan",
            desc = "asdsadlkalskdkalsdlalkdladkldakdlkslakd",
            location = "Jakarta Selatan",
            basePrice = 20000,
            count = 2,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}