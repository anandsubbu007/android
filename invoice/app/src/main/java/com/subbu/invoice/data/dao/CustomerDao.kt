package com.subbu.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer ORDER BY id ASC")
    fun getAll(): List<Customer>

    @Query("SELECT * FROM customer WHERE id=:id")
    fun get(id: Int): Customer

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(note: Customer)

    @Update
    suspend fun update(note: Customer)

    @Delete
    suspend fun delete(note: Customer)

    @Query("DELETE FROM customer")
    suspend fun deleteAll()
}