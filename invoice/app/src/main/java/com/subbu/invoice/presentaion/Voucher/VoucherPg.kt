@file:OptIn(ExperimentalMaterialApi::class)

package com.subbu.invoice.presentaion.Voucher

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.subbu.invoice.data.models.entity.Voucher
import com.subbu.invoice.presentaion.Form.Voucher.VoucherFormSheet
import com.subbu.invoice.presentaion.home.HomeViewModel
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import com.subbu.invoice.presentaion.Form.Voucher.VoucherFormVM

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun VoucherPg(
    homeController: HomeViewModel,
    viewModel: VoucherVM = koinViewModel(),
    formViewModel: VoucherFormVM = koinViewModel(),
) {
    val states by viewModel.voucherState.collectAsState();
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    LaunchedEffect(Unit) {
        snapshotFlow { modalSheetState.currentValue }
            .collect {
                if (it == ModalBottomSheetValue.Hidden) {
                    formViewModel.clear();
                }
            }
    }
    var selectedVoucherId by remember { mutableStateOf<Int?>(null) }
    selectedVoucherId = null;
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            VoucherFormSheet(
                viewModel = formViewModel,
                modalSheetState = modalSheetState,
            )
        }
    ) {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        formViewModel.setVoucherId(null)
                        if (modalSheetState.isVisible)
                            modalSheetState.hide()
                        else
                            modalSheetState.show()
                    }
                }) {
                    Text(text = "Voucher", modifier = Modifier.padding(8.dp))
                }
            }

        ) { pading ->
            Column {
                TotalSummary(viewModel, states)
                VoucherListing(viewModel, states, onTapVoucher = {
                    selectedVoucherId = it;
                    coroutineScope.launch {
                        formViewModel.setVoucherId(it)
                        modalSheetState.show()
                    }
                })
            }
        }
    }
}

@Composable
fun VoucherListing(
    viewModel: VoucherVM,
    state: Result<LiveData<List<Voucher>>>,
    onTapVoucher: (id: Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    when (state) {
        is Result.Success -> {
            val datas = state.data?.observeAsState()?.value ?: emptyList();
            LazyColumn(content = {
                items(items = datas, key = { e -> e.id }) { itm ->
                    Column() {
                        Box(
                            modifier = Modifier
                                .padding(6.dp, 10.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        coroutineScope.launch {
                                            onTapVoucher(itm.id);
//                                            viewModel.voucherId = itm.id;
//                                            if (modalSheetState.isVisible)
//                                                modalSheetState.hide()
//                                            else
//                                                modalSheetState.show()
                                        }
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
                                    text = (if (itm.isCredit) "+" else "-") +
                                            " %.2f".format(itm.amount),
                                    fontWeight = FontWeight.Bold,
                                    color = if (itm.isCredit) Color.Green else Color.Red,
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


@Composable
fun TotalSummary(
    viewModel: VoucherVM,
    state: Result<LiveData<List<Voucher>>>
) {
    when (state) {
        is Result.Success -> {
            val datas = state.data?.observeAsState()?.value ?: emptyList();
//            println("datas: $datas");
            val debitTotal = datas.map { if (it.isCredit) 0F else it.amount }.sum();
            val creditTotal = datas.map { if (it.isCredit) it.amount else 0F }.sum();
            val total = creditTotal - debitTotal;

            Box(
                modifier = Modifier
                    .background(
                        Color.Gray.copy(.4f),
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                ) {
                    Text(
                        "Balance", fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        "%.2f".format(total) + " ₹",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (total > 0) Color.Green else Color.Red,
                    )
                }
            }
        }

        else -> {}
    }
}