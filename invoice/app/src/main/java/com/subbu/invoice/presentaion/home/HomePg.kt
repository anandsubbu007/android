package com.subbu.invoice.presentaion.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.subbu.invoice.navigation.SetupNavigation
import com.subbu.invoice.presentaion.Invoice.InvoiceView
import com.subbu.invoice.presentaion.Voucher.VoucherPg
import com.subbu.invoice.presentaion.components.AppBar
import org.koin.androidx.compose.koinViewModel
import com.subbu.invoice.presentaion.components.BottomNavBar
import com.subbu.invoice.presentaion.components.BottomNavItem
import com.subbu.invoice.presentaion.setting.SettingPg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePg(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    viewModel.tabNavController = rememberNavController()
    viewModel.AppNavController = navController;
    Scaffold(
        topBar = { AppBar(title = "", navController = navController) },
        bottomBar = {
            BottomNavBar(viewModel = viewModel)
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            HomeContent(
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun HomeContent(viewModel: HomeViewModel) {
    NavHost(
        navController = viewModel.tabNavController,
        startDestination = BottomNavItem.Payments.screen_route,
    ) {

        composable(BottomNavItem.Invoices.screen_route) {
            InvoiceView(homeController = viewModel)
        }
        composable(BottomNavItem.Payments.screen_route) {
            VoucherPg(viewModel)
        }
        composable(BottomNavItem.Settings.screen_route) {
            SettingPg(homeController = viewModel)
        }
    }

}
