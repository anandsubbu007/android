package com.subbu.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.Item
import java.util.concurrent.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM Items ORDER BY id ASC")
    fun getAll(): List<Item>

    @Query("SELECT * FROM Items WHERE id=:id")
    fun get(id: Int): Item

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(note: Item)

    @Update
    suspend fun update(note: Item)

    @Delete
    suspend fun delete(note: Item)

    @Query("DELETE FROM Items")
    suspend fun deleteAll()
}