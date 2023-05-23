package com.example.ticketdestination.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ticketdestination.ViewModelFactory
import com.example.ticketdestination.di.Injection

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {

}