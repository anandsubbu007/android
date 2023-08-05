package com.subbu.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.Item

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM Invoice ORDER BY invoiceNo ASC")
    fun getAll(): List<Invoice>

    @Query("SELECT * FROM invoice WHERE invoiceNo=:invId")
    fun get(invId: Int): Invoice

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun create(note: Invoice)

    @Update
     fun update(note: Invoice)

    @Delete
     fun delete(note: Invoice)

    @Query("DELETE FROM Invoice")
     fun deleteAll()
}