package com.subbu.invoice.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "Invoice", foreignKeys = [ForeignKey(
        entity = Customer::class,
        childColumns = ["customer_id"],
        parentColumns = ["id"],
    )]
)
data class Invoice(
    @PrimaryKey(autoGenerate = true) val invoiceNo: Int = 0,
    val status: String,
    val mobileNo: String,
    val details: String,
    val customer_id: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Invoice::class,
            childColumns = ["invoiceNo"], parentColumns = ["id"],
        ),
        ForeignKey(
            entity = Item::class,
            childColumns = ["itemId"], parentColumns = ["id"],
        ),
    ]
)
data class InvoiceEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "invoiceNo") val invoiceNo: Int,
    @ColumnInfo(name = "itemId") val itemId: String,
    val itemName: String,
    val price: Float,
    val qty: Float,
    val tax: Float,
) {}







