package com.subbu.invoice.presentaion.setting

import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.presentaion.home.HomeViewModel
import com.subbu.invoice.presentaion.items.ItemsListing
import org.koin.androidx.compose.koinViewModel
import com.subbu.invoice.utils.Result


@Composable
fun SettingPg(
    homeController: HomeViewModel,
    viewModel: SettingViewModel = koinViewModel()
) {
    val state by viewModel.fetchedCustomerState.collectAsState()
    val itemsState by viewModel.fetchedItemState.collectAsState()

    val context = LocalContext.current


    Column {
        Box(modifier = Modifier.padding(6.dp)) {
            Text(
                "Customers",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()
        CustomerListing(state, context)
        Divider()
        Divider()
        Box(modifier = Modifier.padding(6.dp)) {
            Text(
                text = "Items",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider()
        ItemsListing(itemsState, context, null)
    }
}

@Composable
fun CustomerListing(
    state: Result<List<Customer>>,
    context: Context,
) {
    when (state) {
        is Result.Success -> {
            LazyColumn() {
                items(items = state.data ?: emptyList(), key = { e -> e.id }) { data ->
                    Box(modifier = Modifier.padding(6.dp)) {
                        Text(text = data.name)

                    }
                }
            }
        }

        is Result.Error -> {
            Toast.makeText(context, state.error.message, Toast.LENGTH_LONG).show()
        }

        Result.Loading -> {
            CircularProgressIndicator()
        }

        Result.Idle -> {}
    }
}
