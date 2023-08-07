package com.subbu.invoice.presentaion.items

import android.content.Context
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.presentaion.components.AppBar
import com.subbu.invoice.utils.Result
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListingPg(
    selectedProdId: Int?,
    navController: NavController,
    viewModel: ItemsListingVM = koinViewModel()
) {
    val state by viewModel.fetchedItemState.collectAsState()
    val context = LocalContext.current;
    Scaffold(
        topBar = { AppBar(title = "Items", navController = navController) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            ItemsListing(state = state, context = context, navController = navController)
        }
    }
}


@Composable
fun ItemsListing(
    state: Result<List<Item>>,
    context: Context,
    navController: NavController?
) {
    when (state) {
        is Result.Success -> {
            LazyColumn() {
                items(items = state.data ?: emptyList(), key = { e -> e.id }) { itm ->

                    Box(modifier = Modifier
                        .padding(6.dp, 10.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                navController?.previousBackStackEntry?.savedStateHandle?.set(
                                    "data",
                                    itm
                                );
                                navController?.popBackStack();
//                                navController?.previousBackStackEntry?.let { entry ->
//                                    entry.arguments?.putParcelable("data", itm)
//                                    println(entry.arguments);
//                                }
                            }
                        }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = itm.name)
                            Text(text = "%.2f".format(itm.price) + " ₹")
                        }
                    }
                    Divider(color = Color.Black, thickness = 1.dp)
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