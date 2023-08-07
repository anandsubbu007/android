package com.subbu.invoice.presentaion.Voucher

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Voucher
import com.subbu.invoice.data.repository.VoucherRepo
import com.subbu.invoice.presentaion.Form.Invoice.GetCustomerByTxt
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VoucherVM(val usecase: VoucherUseCase) : ViewModel() {
    val _entriesState = MutableStateFlow<Result<LiveData<List<Voucher>>>>(Result.Idle);
    val voucherState: StateFlow<Result<LiveData<List<Voucher>>>> = _entriesState;
//    var voucherId: Int? = null;

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

data class VoucherUseCase(
    val getTransactions: GetAllVouchers,
    val getCustomerByTxt: GetCustomerByTxt,
    val updateVoucher: UpdateVouchers,
    val getVoucher: GetVoucher
)

class GetAllVouchers(private val repo: VoucherRepo) {
    suspend operator fun invoke(): Result<LiveData<List<Voucher>>> {
        return try {
            val datas = repo.getAll()
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}

class GetVoucher(private val repo: VoucherRepo) {
    suspend operator fun invoke(id: Int): Result<Voucher> {
        return try {
            val datas = repo.get(id)
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}

class UpdateVouchers(private val repo: VoucherRepo) {
    suspend operator fun invoke(data: Voucher): Result<Voucher> {
        return try {
            val datas = repo.upsert(data)
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}