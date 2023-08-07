package com.subbu.invoice.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.subbu.invoice.data.dao.InvoiceDao
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.InvoiceEntry

class InvoiceRepo(
    private val dao: InvoiceDao,
    private val pref: SharedPreferences
) {

    fun getEntries(invoiceNo: Int): LiveData<List<InvoiceEntry>> {
        return dao.getEntries(invoiceNo);
    }

    fun addEntry(entry: InvoiceEntry): Invoice {
        val inv: Invoice;
        if ((entry.invoiceNo ?: -1) == -1) {
            inv = getInvoice(null)!!;
            val newEntry = dao.insertEntry(
                entry.copy(invoiceNo = inv!!.invoiceNo)
            );
        } else {
            inv = dao.get(entry.invoiceNo)!!;
            if (entry.id == 0) {
                val id = dao.insertEntry(entry);
                entry.copy(id = id.toInt());
            } else {
                dao.updateEntry(entry);
            }
        }
        val entries = dao.getEntries(inv.invoiceNo);
//        inv.entries.addAll(entries.toMutableList());
        return inv;
    }

    fun getAll(): LiveData<List<Invoice>> {
        return dao.getAll();
    }

    fun getInvoice(invNo: Int?): Invoice {
        if (invNo != null) {
            val inv = dao.get(invNo!!);
            if (inv != null) {
                val entries = dao.getEntries(inv.invoiceNo);
//                inv.entries.addAll(entries.toMutableList());
            }
            return inv!!;
        } else {
            var inv = dao.getByStatus(1);
            if (inv != null) {
                val entries = dao.getEntries(inv.invoiceNo);
//                inv.entries.addAll(entries.toMutableList());
                return inv;
            }
            inv = Invoice(
                customer_id = 1,
                status = 1,
                details = "",
                mobileNo = "",
                customerName = "Cash Sale"
            );
            val id = dao.create(inv)
            return inv!!.copy(invoiceNo = id.toInt());
        }
    }

//    fun get(invNo: Int): Invoice? {
//        return dao.get(invNo);
//    }

    fun updateInvoice(invoice: Invoice): Invoice {
        dao.update(invoice);
        return dao.get(invoice.invoiceNo)!!;
    }


    fun updateEntry(entry: InvoiceEntry): InvoiceEntry {
        dao.updateEntry(entry);
        return entry;
    }

    fun deleteEntry(entry: InvoiceEntry) {
        dao.deleteEntry(entry);
    }
}