@file:Suppress("Since15")

package com.subbu.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Customer
import java.util.concurrent.Flow

@Dao
interface CustomerDao {
//    @Query("SELECT * FROM customer ORDER BY id ASC")
//    suspend fun getAll()
//
//    @Query("SELECT * FROM customer WHERE id=:id")
//    fun get(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(note: Customer)

//    @Update
//    suspend fun update(note: Customer)
//
    @Delete
    fun delete(note: Customer)

//    @Query("DELETE FROM customer")
//    suspend fun deleteAll()
}