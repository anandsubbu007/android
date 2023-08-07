package com.subbu.invoice.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.subbu.invoice.data.dao.CustomerDao
import com.subbu.invoice.data.dao.InvoiceDao
import com.subbu.invoice.data.dao.ItemDao
import com.subbu.invoice.data.dao.VoucherDao
import com.subbu.invoice.data.models.converter.LocalDateTimeConverter
import com.subbu.invoice.data.models.entity.Customer
import com.subbu.invoice.data.models.entity.Invoice
import com.subbu.invoice.data.models.entity.InvoiceEntry
import com.subbu.invoice.data.models.entity.Item
import com.subbu.invoice.data.models.entity.Voucher


@Database(
//    entities = [Customer::class],
    entities = [Customer::class, Item::class, Voucher::class, Invoice::class, InvoiceEntry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customers(): CustomerDao
    abstract fun items(): ItemDao
    abstract fun vouchers(): VoucherDao
    abstract fun invoice(): InvoiceDao
}