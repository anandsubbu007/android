package com.subbu.invoice.presentaion.Form.Invoice.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.toSize
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.presentaion.Form.Invoice.NewInvoiceVM
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomerField(viewModel: NewInvoiceVM, state: Result<Invoice>) {
    var exp by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    when (state) {
        is Result.Success -> {
            selectedOption = state.data?.customerName ?: "";
        }
        else -> {}
    }
    ExposedDropdownMenuBox(
        expanded = exp,
        onExpandedChange = {
            exp = !exp
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        val coroutineScope = rememberCoroutineScope()
        val focusManager = LocalFocusManager.current
        TextField(
            value = selectedOption,
            onValueChange = {
                selectedOption = it;
                coroutineScope.launch {
                    viewModel.getCustomers(selectedOption)
                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            label = { Text("Customer Name") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = exp)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                }
                .onFocusChanged {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            viewModel.getCustomers(selectedOption)
                        }
                    }
                },
        )
        val itemsState by viewModel.customerSearchState.collectAsState();
        val state: Result<List<Customer>> = itemsState;
        val filterOpts =
            if (state is Result.Success) state.data ?: emptyList() else emptyList();
        if (filterOpts.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = exp, onDismissRequest = {
//                exp = false
                },
                modifier = Modifier
                    .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                filterOpts.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option.name
                            exp = false
                            focusManager.clearFocus()
                            coroutineScope.launch{
                                viewModel.setCustomer(option);
                            }
                        }
                    ) {
                        Text(text = option.name)
                    }
                }
            }
        }
    }
}