package com.subbu.invoice.domain.usecase

import androidx.lifecycle.LiveData
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.InvoiceEntry
import com.subbu.invoice.data.repository.InvoiceRepo
import com.subbu.invoice.domain.repo.CustomerRepo
import com.subbu.invoice.presentaion.setting.GetAllItems
import com.subbu.invoice.utils.Result

data class InvoiceUseCaes(
    val getInvoices: GetAllInvoice
)


data class NewInvoiceUseCase(
    val getCustomerByTxt: GetCustomerByTxt,
    val getItems: GetAllItems,
    val addEntry: AddEntry,
    val getInvoice: GetInvoice,
    val updateInvoice: UpdateInvoice,
    val getEntries: GetEntries,
    val updateEntry: UpdateEntry,
    val deleteEntry: DeleteEntry,
    val deleteInvoice: DeleteInvoice
)

class GetCustomerByTxt(private val repo: CustomerRepo) {
    suspend operator fun invoke(
        search: String,
        hideCashSale: Boolean = false
    ): Result<List<Customer>> {
        return try {
            val datas = repo.getAll().filter {
                (it.name.contains(
                    search, ignoreCase = true
                ) || it.mobileNo.contains(search) ||
                        (search.startsWith("cash sale", ignoreCase = true)))
                        && if (hideCashSale) !it.name.startsWith(
                    "cash sale",
                    ignoreCase = true
                ) else true
            }
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}

class AddEntry(private val repo: InvoiceRepo) {
    suspend operator fun invoke(entry: InvoiceEntry): Result<Invoice> {
        return try {
            val datas = repo.addEntry(entry)
            Result.Success(datas)
        } catch (e: Exception) {
            Result.Error(e);
        }
    }
}

class GetInvoice(private val repo: InvoiceRepo) {
    suspend operator fun invoke(invoiceNo: Int?): Result<Invoice> {
        try {
            val datas = repo.getInvoice(invoiceNo)
            return Result.Success(datas)
        } catch (e: Exception) {
            println(e);
            return Result.Error(e);
        }
    }
}

class GetEntries(private val repo: InvoiceRepo) {
    suspend operator fun invoke(invoiceNo: Int): Result<LiveData<List<InvoiceEntry>>> {
        try {
            val ee = repo.getEntries(invoiceNo);
            return Result.Success(ee);
        } catch (e: Exception) {
            println(e);
            return Result.Error(e);
        }
    }
}

class UpdateInvoice(private val repo: InvoiceRepo) {
    suspend operator fun invoke(invoice: Invoice): Result<Invoice> {
        return try {
            val datas = repo.updateInvoice(invoice)
            return Result.Success(datas)
        } catch (e: Exception) {
            println(e);
            return Result.Error(e);
        }
    }
}

class UpdateEntry(private val repo: InvoiceRepo) {
    suspend operator fun invoke(invoice: InvoiceEntry): Result<InvoiceEntry> {
        return try {
            val datas = repo.updateEntry(invoice)
            return Result.Success(datas)
        } catch (e: Exception) {
            println(e);
            return Result.Error(e);
        }
    }
}

class DeleteEntry(private val repo: InvoiceRepo) {
    suspend operator fun invoke(invoice: InvoiceEntry): Result<Boolean> {
        return try {
            repo.deleteEntry(invoice)
            return Result.Success(true)
        } catch (e: Exception) {
            println(e);
            return Result.Error(e);
        }
    }
}


class DeleteInvoice(private val repo: InvoiceRepo) {
    suspend operator fun invoke(invoice: Invoice): Result<Boolean> {
        return try {
            repo.deleteInvoice(invoice)
            return Result.Success(true)
        } catch (e: Exception) {
            println(e);
            return Result.Error(e);
        }
    }
}


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
