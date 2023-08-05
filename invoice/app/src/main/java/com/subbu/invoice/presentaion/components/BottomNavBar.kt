package com.subbu.invoice.presentaion.components

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.subbu.invoice.presentaion.home.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(viewModel: HomeViewModel) {
    val items = listOf(
        BottomNavItem.Invoices,
        BottomNavItem.Payments,
        BottomNavItem.Settings
    )
    BottomNavigation() {
        val navBackStackEntry by viewModel.tabNavController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
//        println("NavHost: " + viewModel.tabNavController);
        items.forEach { item ->
            val isSelected = currentRoute == item.screen_route;
            BottomNavigationItem(
                icon = { },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color.White else Color.White.copy(0.4f),
                        fontSize =  if (isSelected) 21.sp else 19.sp,
                    )
                }, modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    viewModel.tabNavController.navigate(item.screen_route) {
                        viewModel.tabNavController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

    }
}


sealed class BottomNavItem(var title: String, var screen_route: String) {

    object Invoices : BottomNavItem("Invoices", "0")
    object Payments : BottomNavItem("Payments", "1")
    object Settings : BottomNavItem("Settings", "2")
}