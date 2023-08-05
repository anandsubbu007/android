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
    @Query("SELECT * FROM tTransactions ORDER BY id")
    fun getAll(): List<Transaction>

    @Query("SELECT * FROM tTransactions WHERE id=:transId")
    fun get(transId: Int): Transaction

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun create(data: Transaction)

    @Update
     fun update(data: Transaction)

    @Delete
     fun delete(data: Transaction)

    @Query("DELETE FROM tTransactions")
     fun deleteAll()
}