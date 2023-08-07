package com.subbu.invoice.presentaion.Voucher

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subbu.invoice.data.models.entity.Voucher
import com.subbu.invoice.domain.usecase.VoucherUseCase
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VoucherVM(val usecase: VoucherUseCase) : ViewModel() {
    val _entriesState = MutableStateFlow<Result<LiveData<List<Voucher>>>>(Result.Idle);
    val voucherState: StateFlow<Result<LiveData<List<Voucher>>>> = _entriesState;

    init {
        getData();
    }

    fun getData() {
        _entriesState.value = Result.Loading;
        viewModelScope.launch {
            val result = usecase.getTransactions();
            _entriesState.value = result;
        }
    }
}
