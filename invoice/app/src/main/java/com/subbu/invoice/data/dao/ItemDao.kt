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
import java.util.concurrent.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(item: Item)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Item>)
    @Delete
     fun delete(item: Item)
}