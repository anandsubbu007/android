package com.subbu.invoice.presentaion.Invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.InvoiceEntry
import com.subbu.invoice.data.repository.InvoiceRepo
import com.subbu.invoice.domain.repo.CustomerRepo
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InvoiceViewModel(private val usecase: InvoiceUseCaes) : ViewModel() {
    val _entriesState = MutableStateFlow<Result<LiveData<List<Invoice>>>>(Result.Idle);
    val invoicesState: StateFlow<Result<LiveData<List<Invoice>>>> = _entriesState;

    init {
        getData();
    }

    fun getData() {
        _entriesState.value = Result.Loading;
        viewModelScope.launch {
            val result = usecase.getInvoices();
            _entriesState.value = result;
        }
    }
}


data class InvoiceUseCaes(
    val getInvoices: GetAllInvoice
)


class GetAllInvoice(private val repo: InvoiceRepo) {
    suspend operator fun invoke(): Result<LiveData<List<Invoice>>> {
        return try {
            val datas = repo.getAll()
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}