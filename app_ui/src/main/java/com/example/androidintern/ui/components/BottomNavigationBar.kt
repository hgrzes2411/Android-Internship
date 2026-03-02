package com.example.androidintern.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidintern.ui.navigation.Routes

sealed class BottomNavItem(val route: String, val title: String) {
    object Closet : BottomNavItem(Routes.PRODUCT_LIST, "Closet")
    object Account : BottomNavItem(Routes.PROFILE, "Account")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(
        BottomNavItem.Closet,
        BottomNavItem.Account,
    )

    NavigationBar(containerColor = Color(0xFFFEEBD5)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            val isSelected = when (item) {
                is BottomNavItem.Account -> currentRoute == item.route
                is BottomNavItem.Closet -> currentRoute != Routes.PROFILE
            }
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (item) {
                        BottomNavItem.Closet -> Icon(
                            painter = painterResource(id = com.example.androidintern.ui.R.drawable.closet_icon),
                            contentDescription = item.title
                        )
                        BottomNavItem.Account -> Icon(
                            painter = painterResource(id = com.example.androidintern.ui.R.drawable.account_icon),
                            contentDescription = item.title
                        )
                    }
                },
                label = { Text(text = item.title) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFFDD3AC)
                )
            )
        }
    }
}
