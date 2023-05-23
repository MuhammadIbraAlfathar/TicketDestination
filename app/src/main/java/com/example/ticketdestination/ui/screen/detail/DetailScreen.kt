package com.example.ticketdestination.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ticketdestination.R

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
            is UiState.Success -> {}
        }
    }
}


@Composable
fun DetailContent(
    photoUrl: String,
    title: String,
    basePrice: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalTicket by rememberSaveable {
        mutableStateOf(0)
    }
    val buyTicketCount by rememberSaveable {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                
            }
        }
    }
}