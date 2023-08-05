package com.subbu.invoice.data.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.subbu.invoice.data.models.entity.Customer

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: Array<Customer>)

    @Delete
    fun delete(data: Customer)

    @Query("SELECT * FROM customer ORDER BY id ASC")
    fun getAll(): List<Customer>

    //
    @Query("SELECT * FROM customer WHERE id = :customerId LIMIT 1")
    fun get(customerId: Int): Customer?


}