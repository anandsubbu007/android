package com.subbu.invoice.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.subbu.invoice.presentaion.Form.Invoice.NewInvoicePg
import com.subbu.invoice.presentaion.components.SplashScreen
import com.subbu.invoice.presentaion.home.HomePg
import com.subbu.invoice.presentaion.items.ItemsListingPg
import java.security.KeyStore.Entry


@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.home.route
//        startDestination = "splash_screen"
    ) {
//        composable("splash_screen") {
//            SplashScreen(navController = navController)
//        }
        composable(
            route = Screens.home.route
        ) {
            HomePg(
                navController = navController
            )
        }
        composable(
            route = Screens.NewInvoice.route,
            arguments = listOf(navArgument("InvId") { type = NavType.IntType }),
        ) {
            val invId = it.arguments?.getInt("InvId", -1)
            NewInvoicePg(
                invId = if (invId == -1) null else invId,
                navController = navController
            )
        }
        composable(
            route = Screens.ProductListing.route,
        ) {
            val selectedProdId = it.arguments?.getInt("data")
            ItemsListingPg(
                selectedProdId = selectedProdId,
                navController = navController
            )
        }
//
    }
}