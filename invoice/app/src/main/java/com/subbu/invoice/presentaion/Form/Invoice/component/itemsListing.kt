package com.subbu.invoice.presentaion.Form.Invoice.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.subbu.invoice.presentaion.Form.Invoice.NewInvoiceVM
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.launch

@Composable
fun ItemsInKart(viewModel: NewInvoiceVM) {
    val eState by viewModel.entryState.collectAsState();
    val state = eState;
    Log.i("Anand", state.toString())
    val coureteen = rememberCoroutineScope();
    when (state) {
        is Result.Success -> {
            val entryDatas = state.data?.observeAsState();
            val entries = entryDatas?.value ?: emptyList();

            LazyColumn(content = {
                items(items = entries, key = { e -> e.id }) { itm ->
                    var qty: Float = itm.qty;
                    fun getQty(): Float {
                        return qty;
                    }
                    Box(
                        modifier = Modifier.padding(6.dp, 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Icon(Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.pointerInput(Unit) {
                                    detectTapGestures {
                                        coureteen.launch {
                                            viewModel.deleteEntry(itm)
                                        }
                                    }
                                })

                            Text(
                                text = itm.itemName,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(2.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text("-",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 32.sp,
                                    modifier = Modifier
                                        .padding(3.dp, 2.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures {
                                                coureteen.launch {
                                                    viewModel.updateEntry(
                                                        itm.copy(qty = itm.qty - 1), false
                                                    )
                                                }
                                            }
                                        })
                                Text(
                                    text = "%.0f".format(qty),
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(2.dp)
                                )
                                Text("+",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 32.sp,
                                    modifier = Modifier
                                        .padding(3.dp, 2.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures {
                                                coureteen.launch {
                                                    val ita = itm.copy(qty = getQty() + 1);
                                                    viewModel.updateEntry(ita, true);
                                                }
                                            }
                                        })

                            }
//                                Text(text = "%.2f".format(itm.price) + " ₹")

                        }
                    }
                }

            })
        }

        is Result.Error -> {
            Toast.makeText(LocalContext.current, state.error.message, Toast.LENGTH_LONG).show()
        }

        Result.Loading -> {
            CircularProgressIndicator()
        }

        Result.Idle -> {}
    }

}


@Composable
fun AppBottomBar(viewModel: NewInvoiceVM) {
    val eState by viewModel.entryState.collectAsState();
//    val eInvState by viewModel.InvoiceState.collectAsState();
//    val eIState = eInvState;

    val state = eState;
    val coureteen = rememberCoroutineScope();
    when (state) {
        is Result.Success -> {
            val entryDatas = state.data?.observeAsState();
            val invoice = viewModel.invoice;
            val entries = entryDatas?.value ?: emptyList();
            val totals = entries.map { it.qty * it.price }.sum()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            ) {
                Text(text = "Total: ", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                Text(
                    text = "%.2f".format(totals) + " ₹",
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp, modifier = Modifier
                        .weight(1f)
                )
                ElevatedButton(onClick = {
                    coureteen.launch {
                        viewModel.saveInvoice();
                        viewModel.AppNavController.popBackStack();
                    }
                }) {
                    Text(
                        text = if (invoice.status == 1) "Create" else "Update",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

        }

        is Result.Error -> {}
        Result.Loading -> {}
        Result.Idle -> {}
    }

}

