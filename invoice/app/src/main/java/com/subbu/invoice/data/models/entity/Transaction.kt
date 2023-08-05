package com.subbu.invoice.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.subbu.invoice.data.models.enums.TransType
import java.time.LocalDateTime


@Entity(
    tableName = "tTransactions", foreignKeys = [
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
data class Transaction(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "customerId") val customerId: String,
    @ColumnInfo(name = "invoiceNo") val invoiceNo: Int,
    val type: TransType,
    val amount: Float,
    val details: Float,
    val date: LocalDateTime = LocalDateTime.now(),
) {}









