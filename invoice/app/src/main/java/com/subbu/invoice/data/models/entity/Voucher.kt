package com.subbu.invoice.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(
    tableName = "Voucher", foreignKeys = [
        ForeignKey(
            entity = Invoice::class,
            childColumns = ["invoiceNo"], parentColumns = ["invoiceNo"],
        ),
        ForeignKey(
            entity = Customer::class,
            childColumns = ["customerId"], parentColumns = ["id"],
        ),
    ], indices = [Index("invoiceNo"), Index("customerId")]
)
data class Voucher(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "customerId") val customerId: Int,
    @ColumnInfo(name = "invoiceNo") val invoiceNo: Int? = null,
    val customerName: String,
    val isCredit: Boolean,
    val amount: Float,
    val details: String? = null,
    val date: LocalDateTime = LocalDateTime.now(),
) {
    val dateF: String
        get() = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

}









