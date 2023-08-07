package com.subbu.invoice.presentaion.Form.Invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.InvoiceEntry
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.data.repository.InvoiceRepo
import com.subbu.invoice.domain.repo.CustomerRepo
import com.subbu.invoice.domain.usecase.NewInvoiceUseCase
import com.subbu.invoice.presentaion.setting.GetAllItems
import com.subbu.invoice.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NewInvoiceVM(private val usecase: NewInvoiceUseCase) : ViewModel() {
    lateinit var AppNavController: NavController;
    val _InvoiceState = MutableStateFlow<Result<Invoice>>(Result.Idle)
    val InvoiceState: StateFlow<Result<Invoice>> = _InvoiceState;

    val _customerSearchState = MutableStateFlow<Result<List<Customer>>>(Result.Idle);
    val customerSearchState: StateFlow<Result<List<Customer>>> = _customerSearchState;

    val _entriesState = MutableStateFlow<Result<LiveData<List<InvoiceEntry>>>>(Result.Idle);
    val entryState: StateFlow<Result<LiveData<List<InvoiceEntry>>>> = _entriesState;

    var customer: Customer? = null;
    var invoiceNo: Int? = null;
    lateinit var invoice: Invoice;

    suspend fun setInvNo(invoiceNo: Int?) {
        this.invoiceNo = invoiceNo;
        val result = usecase.getInvoice(invoiceNo);
        viewModelScope.launch {
            if (result is Result.Success) {
                invoice = result.data!!;
                _entriesState.value = usecase.getEntries(invoice.invoiceNo);
            }
            _InvoiceState.value = result;
        }
    }

    suspend fun setCustomer(customer: Customer) {
        this.customer = customer;
        invoice = invoice.copy(customer_id = customer.id, customerName = customer.name)
        val result = usecase.updateInvoice(invoice!!);
        viewModelScope.launch {
            _InvoiceState.value = result;
        }
    }

    suspend fun getCustomers(searchTxt: String): List<Customer> {
        val result = usecase.getCustomerByTxt(searchTxt);
        viewModelScope.launch {
            _customerSearchState.value = result;
        }
        if (result is Result.Success) return result.data ?: emptyList();
        return emptyList();
    }

    suspend fun addNewProduct(item: Item) {
        var result = usecase.addEntry(
            InvoiceEntry(
                tax = item.tax,
                price = item.price,
                invoiceNo = invoiceNo ?: -1,
                itemId = item.id,
                itemName = item.name,
                qty = 1.0F
            )
        );
        viewModelScope.launch {
            _InvoiceState.value = result;
        }
    }

    suspend fun deleteEntry(entry: InvoiceEntry) {
        usecase.deleteEntry(entry)
    }

    suspend fun deleteInvoice() {
        if (invoice.invoiceNo > 0) usecase.deleteInvoice(invoice)
    }

    suspend fun updateEntry(entry: InvoiceEntry, isAdd: Boolean) {
        val res = entryState.value;
        if (res is Result.Success) {
            val entry = res.data!!.value!!.find { it.id == entry.id }!!;
            usecase.updateEntry(entry.copy(qty = if (isAdd) entry.qty + 1 else entry.qty - 1))
        }
    }

    suspend fun saveInvoice() {
        usecase.updateInvoice(invoice.copy(status = 0));
    }

}
