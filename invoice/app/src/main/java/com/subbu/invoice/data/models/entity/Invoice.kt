package com.subbu.invoice.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
//    0 -> DOne, 1-> InProgress, 2-> Archived,
    val status: Int = 1,
    val mobileNo: String,
    val customerName: String,
    val details: String,
    @ColumnInfo(name = "customer_id") val customer_id: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    @Ignore
    val entries = mutableListOf<InvoiceEntry>();
    val formattedCreatedAt: String
        get() = createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}

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
    @ColumnInfo(name = "itemId") val itemId: Int,
    val itemName: String,
    val price: Float,
    val qty: Float,
    val tax: Float,
) {}


