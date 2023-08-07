package com.subbu.invoice.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.subbu.invoice.data.models.entity.Voucher

@Dao
interface VoucherDao {
    @Query("SELECT * FROM Voucher ORDER BY id DESC")
    fun getAll(): LiveData<List<Voucher>>

    @Query("SELECT * FROM Voucher WHERE id=:transId")
    fun get(transId: Int): Voucher

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun create(data: Voucher): Long

    @Update
    fun update(data: Voucher)

    @Delete
    fun delete(data: Voucher)

    @Query("DELETE FROM Voucher")
    fun deleteAll()
}