@file:OptIn(ExperimentalMaterial3Api::class)

package com.subbu.invoice.presentaion.Form.Voucher

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.subbu.invoice.data.models.entity.Voucher
import com.subbu.invoice.presentaion.Voucher.VoucherVM
import kotlinx.coroutines.launch
import com.subbu.invoice.utils.Result


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VoucherFormSheet(
    modalSheetState: ModalBottomSheetState,
    viewModel: VoucherFormVM,
) {
    val couruten = rememberCoroutineScope();
    val state by viewModel.voucherState.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(all = 2.dp)
    ) {
        Text(
            "Voucher",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp, 8.dp)
        )
        Divider()
        when (state) {
            is Result.Success -> {
                CustomerFieldVoucher(viewModel)
                AmountField(viewModel)
            }

            else -> {}
        }


        Box(modifier = Modifier.padding(20.dp))
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            if (viewModel.vouId != null) IconButton(onClick = {
                couruten.launch {
                    viewModel.delete(viewModel.vouId!!)
                    modalSheetState.hide()
                }
            }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete"
                )
            }
            Box(modifier = Modifier.weight(1f))
            ElevatedButton(onClick = {
                if (viewModel.voucher.customerId == -1) {
                    Toast.makeText(
                        context,
                        "Please Select Customer",
                        Toast.LENGTH_LONG
                    ).show()
                }
                couruten.launch {
                    viewModel.updateVoucher()
                    modalSheetState.hide()
                }
            }) {
                Text(text = if (viewModel.vouId == null) "Create" else "Update")
            }
        }
        Box(modifier = Modifier.padding(6.dp))
    }
}


@Composable
fun AmountField(viewModel: VoucherFormVM) {
    val focusManager = LocalFocusManager.current
    var number by remember { mutableStateOf("") }
    var isCredit by remember { mutableStateOf(true) }
    val voucher = viewModel.voucher;
    isCredit = voucher.isCredit;
    number = if (voucher.amount == 0F) "" else "%.0f".format(voucher.amount);
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(all = 2.dp)
            .height(66.dp)
    ) {
        TextField(
            value = number,
            onValueChange = {
                try {
                    if (it.length > 0) {
                        number = getValidatedNumber(it)
                        viewModel.updateAmount(number.toFloat())
                    } else {
                        number = "";
                        viewModel.updateAmount(0F)
                    }
                } catch (e: Exception) {
                    number = "";
                    viewModel.updateAmount(0F)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(2.dp),
            label = { Text("Amount") },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
        )
        AppRadioGroup(isCredit, callback = {
            isCredit = it;
            viewModel.updateType(it)
        })
    }
}

@Composable
fun AppRadioGroup(
    isCredit: Boolean,
    callback: (isCredit: Boolean) -> Unit
) {
    var selectedOption by remember { mutableStateOf("") }
    selectedOption = if (isCredit) "Credit" else "Debit"

    val onSelectionChange = { text: String ->
        selectedOption = text;
        callback(text == "Credit")
    }

    Row(
    ) {
        listOf("Credit", "Debit").forEach { text ->
            Row(
                modifier = Modifier
                    .padding(all = 8.dp),
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall.merge(),
                    color = Color.White,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(size = 12.dp),
                        )
                        .clickable {
                            onSelectionChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.LightGray
                            }
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                )
            }
        }
    }
}

fun getValidatedNumber(text: String): String {
    // Start by filtering out unwanted characters like commas and multiple decimals
    val filteredChars = text.filterIndexed { index, c ->
        c in "0123456789"
//                ||                      // Take all digits
//                (c == '.' && text.indexOf('.') == index)  // Take only the first decimal
    }
    return filteredChars;
    // Now we need to remove extra digits from the input
//    return if (filteredChars.contains('.')) {
//        val beforeDecimal = filteredChars.substringBefore('.')
//        val afterDecimal = filteredChars.substringAfter('.')
//        beforeDecimal.take(3) + "." + afterDecimal.take(2)    // If decimal is present, take first 3 digits before decimal and first 2 digits after decimal
//    } else {
//        filteredChars.take(3)                     // If there is no decimal, just take the first 3 digits
//    }
}