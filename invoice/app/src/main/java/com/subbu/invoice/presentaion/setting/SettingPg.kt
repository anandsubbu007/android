package com.subbu.invoice.presentaion.setting

import android.widget.ListView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.widget.ListViewAutoScrollHelper
import androidx.navigation.NavController
import com.subbu.invoice.presentaion.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingPg(
    homeController: HomeViewModel,
    viewModel: SettingViewModel = koinViewModel()
) {

    Column {
        Text(text = "Customers")
    }
}