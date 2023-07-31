package com.subbu.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.data.models.entity.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM tTransactions ORDER BY invoiceNo ASC")
    fun getAll(): List<Invoice>

    @Query("SELECT * FROM tTransactions WHERE id=:id")
    fun get(id: Int): Invoice

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(note: Invoice)

    @Update
    suspend fun update(note: Invoice)

    @Delete
    suspend fun delete(note: Invoice)

    @Query("DELETE FROM Invoice")
    suspend fun deleteAll()
}