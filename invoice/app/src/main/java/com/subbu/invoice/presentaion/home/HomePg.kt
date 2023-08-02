package com.subbu.invoice.presentaion.home

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePg(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current

    Scaffold {
        padding -> HomeContent(padding = padding, viewModel = viewModel, context =  context)
    }
}

@Composable
fun HomeContent(
    padding: PaddingValues,
    context: Context,
    viewModel: HomeViewModel
) {
Column {
    Text(text = "Hello Anand...!")
}
}