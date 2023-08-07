package com.subbu.invoice.presentaion.Invoice

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.subbu.invoice.navigation.Screens
import com.subbu.invoice.utils.Result
import com.subbu.invoice.presentaion.home.HomeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.presentaion.setting.SettingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceView(
    homeController: HomeViewModel,
    viewModel: InvoiceViewModel = koinViewModel()
) {
    val states by viewModel.invoicesState.collectAsState();
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                homeController.AppNavController.navigate("NewInvoice/-1") {}
            }) {
                Text(text = "Invoice", modifier = Modifier.padding(8.dp))
            }
        }

    ) { pading ->
        InvoiceListing(viewModel, homeController, states)
    }
}

@Composable
fun InvoiceListing(
    viewModel: InvoiceViewModel,
    homeController: HomeViewModel,
    state: Result<LiveData<List<Invoice>>>
) {
    when (state) {
        is Result.Success -> {
            val datas = state.data?.observeAsState()?.value ?: emptyList();
            LazyColumn(content = {
                items(items = datas, key = { e -> e.invoiceNo }) { itm ->
                    Column() {
                        Box(
                            modifier = Modifier
                                .padding(6.dp, 10.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        homeController.AppNavController.navigate(
                                            "NewInvoice/" + itm.invoiceNo.toString()
                                        )
                                    }
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = itm.customerName,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(2.dp)
                                )
                                Text(
                                    text = itm.formattedCreatedAt,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(2.dp)
                                )
                            }
                        }
                    }
                    Divider()
                }

            })
        }

        else -> {}
    }
}