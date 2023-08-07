package com.subbu.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(item: Item)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: Array<Item>)
    @Delete
     fun delete(item: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: Item)

    @Query("SELECT * FROM Items ORDER BY id ASC")
    fun getAll(): List<Item>

    //
    @Query("SELECT * FROM Items WHERE id = :itemId LIMIT 1")
    fun get(itemId: Int): Item?

    @Query("DELETE FROM Items")
    fun deleteAll()
}