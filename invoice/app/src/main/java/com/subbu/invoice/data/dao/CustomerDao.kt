package com.subbu.invoice.data.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Customer

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Customer)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: Array<Customer>)

    @Delete
    fun delete(data: Customer)

    @Query("SELECT * FROM customer ORDER BY id ASC")
    fun getAll(): List<Customer>

    //
    @Query("SELECT * FROM customer WHERE id = :customerId LIMIT 1")
    fun get(customerId: Int): Customer?

    @Query("DELETE FROM customer")
    fun deleteAll()

}