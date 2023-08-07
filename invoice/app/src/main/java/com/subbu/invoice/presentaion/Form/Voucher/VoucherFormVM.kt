package com.subbu.invoice.presentaion.Form.Voucher

import com.subbu.invoice.presentaion.Voucher.VoucherUseCase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Voucher
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

class VoucherFormVM(val usecase: VoucherUseCase) : ViewModel() {
    val _voucherState = MutableStateFlow<Result<Voucher>>(Result.Idle);
    val voucherState: StateFlow<Result<Voucher>> = _voucherState;
    val _customerSearchState = MutableStateFlow<Result<List<Customer>>>(Result.Idle);
    val customerSearchState: StateFlow<Result<List<Customer>>> = _customerSearchState;
    var customer: Customer? = null;
    lateinit var voucher: Voucher;
    var vouId: Int? = null;
   suspend fun setVoucherId(vouId: Int?) {
        this.vouId = vouId;
        customer = null;
        _voucherState.value = Result.Loading;
        viewModelScope.launch {
            if (vouId != null) {
                val result = usecase.getVoucher(vouId);
                if (result is Result.Success) {
                    voucher = result.data!!;
                } else {
                    voucher = Voucher(
                        amount = 0F, isCredit = true,
                        customerId = -1, customerName = ""
                    )
                }
                _voucherState.value = Result.Success(voucher)
            } else {
                voucher = Voucher(
                    amount = 0F, isCredit = true,
                    customerId = -1, customerName = ""
                )
                _voucherState.value = Result.Success(voucher)
            }
            println(voucher);
        }
    }

    fun clear() {
        vouId = null;
        customer = null;
        voucher = Voucher(
            amount = 0F, isCredit = true,
            customerId = -1, customerName = ""
        );
        _customerSearchState.value = Result.Loading;
    }

    suspend fun getCustomers(searchTxt: String): List<Customer> {
        val result = usecase.getCustomerByTxt(searchTxt, true);
        _customerSearchState.value = result;
        if (result is Result.Success) return result.data ?: emptyList();
        return emptyList();
    }

    fun setCustomerData(customer: Customer) {
        this.customer = customer;
        voucher = voucher.copy(customerId = customer.id, customerName = customer.name)
    }

    fun updateAmount(amount: Float) {
        voucher = voucher.copy(amount = amount);
    }

    fun updateType(isCredit: Boolean) {
        voucher = voucher.copy(isCredit = isCredit);
    }

    suspend fun updateVoucher() {
        if (voucher.customerId == null) {
            return;
        }
        usecase.updateVoucher(
            if (customer == null) voucher else voucher.copy(
                customerId = customer!!.id,
                customerName = customer!!.name,
            )
        );
    }
}
