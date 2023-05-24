package com.example.ticketdestination

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ticketdestination.ui.navigation.NavigationItem
import com.example.ticketdestination.ui.navigation.Screen
import com.example.ticketdestination.ui.screen.cart.CartScreen
import com.example.ticketdestination.ui.screen.detail.DetailScreen
import com.example.ticketdestination.ui.screen.home.HomeScreen
import com.example.ticketdestination.ui.screen.profile.ProfileScreen


@Composable
fun TicketDestinationApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailTicket.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { ticketId ->
                    navController.navigate(Screen.DetailTicket.createRoute(ticketId))
                })
            }
            composable(Screen.Cart.route) {
                val context = LocalContext.current
                CartScreen(onBuyButtonClicked = { message ->
                    shareTicket(context = context, summary = message )
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                Screen.DetailTicket.route,
                arguments = listOf(navArgument("ticketId") {type = NavType.LongType}),
            ) {
                val id = it.arguments?.getLong("ticketId") ?: -1L
                DetailScreen(
                    ticketId = id ,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    BottomNavigation (
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItemList = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                contentDescription = "home_page",
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_cart),
                contentDescription = "cart_page",
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                contentDescription = "about_page",
                screen = Screen.Profile
            ),
        )
        BottomNavigation {
            navigationItemList.map { item ->
                BottomNavigationItem(
                    icon = {
                           Icon(
                               imageVector = item.icon ,
                               contentDescription = item.contentDescription
                           )
                    },
                    selected = currentRoute == item.screen.route,
                    label = { Text(text = item.title)},
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}



fun shareTicket(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.ticket_destination))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.ticket_destination)
        )
    )
}