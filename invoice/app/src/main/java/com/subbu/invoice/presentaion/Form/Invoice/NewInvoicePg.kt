package com.subbu.invoice.presentaion.Form.Invoice

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.subbu.invoice.presentaion.components.AppBar
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.currentBackStackEntryAsState
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.navigation.Screens
import com.subbu.invoice.presentaion.Form.Invoice.component.CustomerField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.Observer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.presentaion.Form.Invoice.component.AppBottomBar
import com.subbu.invoice.presentaion.Form.Invoice.component.ItemsInKart
import kotlinx.coroutines.coroutineScope
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewInvoicePg(
    invId: Int?, navController: NavController,
    viewModel: NewInvoiceVM = koinViewModel()
) {
    val context = LocalContext.current
    viewModel.AppNavController = navController;
    val coroutineScope = rememberCoroutineScope()
    Log.i("Anand INIT", "invId:  $invId");
    LaunchedEffect("Init") {
        coroutineScope.launch {
            viewModel.setInvNo(invId);
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState();
    val noResultData = backStackEntry?.savedStateHandle?.getLiveData<Item>("data");
    backStackEntry?.savedStateHandle?.remove<Item>("data")
    if (noResultData?.value != null) {
        LaunchedEffect("Add Product") {
            coroutineScope.launch {
                viewModel.addNewProduct(noResultData.value!!);
            }
        }
    }

    Scaffold(topBar = { AppBar(title = "New Invoice", navController = navController, true) },
        floatingActionButton = {

        },
        bottomBar = {
            AppBottomBar(viewModel)
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            InvoiceForm(padding, viewModel)
        }
    }
}


@Composable
fun InvoiceForm(
    padding: PaddingValues, viewModel: NewInvoiceVM
) {
    val state by viewModel.InvoiceState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        CustomerField(viewModel, state)
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.padding(6.dp)) {
                Text(
                    "Items", fontSize = 26.sp, fontWeight = FontWeight.Bold
                )
            }
            ElevatedButton(onClick = {
                viewModel.AppNavController.navigate(Screens.ProductListing.route) {}
            }) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Add Item")
                Text(text = "Add Items", modifier = Modifier.padding(6.dp, 0.dp))
            }
        }
        Divider()
        ItemsInKart(viewModel = viewModel)
    }

}


