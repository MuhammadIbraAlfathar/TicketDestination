package com.example.ticketdestination.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Cart: Screen("cart")
    object Profile: Screen("profile")
    object DetailTicket: Screen("home/{ticketId}") {
        fun createRoute(ticketId: Long) = "home/$ticketId"
    }
}
