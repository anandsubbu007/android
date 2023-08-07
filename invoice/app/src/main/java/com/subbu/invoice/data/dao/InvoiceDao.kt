package com.subbu.invoice.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.InvoiceEntry

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM Invoice ORDER BY invoiceNo DESC")
    fun getAll(): LiveData<List<Invoice>>

    @Query("SELECT * FROM invoice WHERE invoiceNo=:invId Limit 1")
    fun get(invId: Int): Invoice?

    @Query("SELECT * FROM invoice WHERE status=:statusId Limit 1 ")
    fun getByStatus(statusId: Int): Invoice?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun create(invoice: Invoice): Long

    @Update
    fun update(invoice: Invoice)

    @Delete
    fun delete(invoice: Invoice)

    @Delete
    fun deleteEntry(entry: InvoiceEntry)

    @Query("DELETE FROM Invoice")
    fun deleteAll()
    @Query("DELETE FROM InvoiceEntry Where invoiceNo = :id")
    fun deleteEntry(id:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntry(entry: InvoiceEntry): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateEntry(entry: InvoiceEntry)

    @Query("SELECT * FROM InvoiceEntry WHERE invoiceNo=:invNo")
    fun getEntries(invNo: Int): LiveData<List<InvoiceEntry>>

}