package com.subbu.invoice.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.subbu.invoice.presentaion.home.HomePg


@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.home.route
    ) {
        composable(
            route = Screens.home.route
        ) {
            HomePg(
                navController = navController
            )
        }
//        composable(
//            route = Screens.Add.route,
//        ) {
//            AddScreen(
//                navController = navController
//            )
//        }
    }
}