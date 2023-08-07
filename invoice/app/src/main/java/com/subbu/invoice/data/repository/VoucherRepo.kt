package com.subbu.invoice.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.subbu.invoice.data.dao.VoucherDao
import com.subbu.invoice.data.models.entity.Voucher

class VoucherRepo(
    private val dao: VoucherDao,
    private val pref: SharedPreferences
) {
    fun get(id: Int): Voucher {
        return dao.get(id);
    }

    fun getAll(): LiveData<List<Voucher>> {
        return dao.getAll();
    }

    fun upsert(data: Voucher): Voucher {
        if (data.id == 0) {
            val id = dao.create(data).toInt()
            return dao.get(id!!);
        } else {
            dao.update(data)
            return dao.get(data.id);
        }
    }


}