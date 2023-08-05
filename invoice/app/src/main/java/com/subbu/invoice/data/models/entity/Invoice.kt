package com.subbu.invoice.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "Invoice",
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = ["id"],
        childColumns = ["customer_id"],
    )], indices = [Index("customer_id")]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "invoiceNo") val invoiceNo: Int = 0,
    val status: String,
    val mobileNo: String,
    val details: String,
    @ColumnInfo(name = "customer_id") val customer_id: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Invoice::class,
            childColumns = ["invoiceNo"], parentColumns = ["invoiceNo"],
        ),
        ForeignKey(
            entity = Item::class,
            childColumns = ["itemId"], parentColumns = ["id"],
        ),
    ],
    indices = [Index("itemId"), Index("invoiceNo")]
)
data class InvoiceEntry(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "invoiceNo") val invoiceNo: Int,
    @ColumnInfo(name = "itemId") val itemId: String,
    val itemName: String,
    val price: Float,
    val qty: Float,
    val tax: Float,
) {}


