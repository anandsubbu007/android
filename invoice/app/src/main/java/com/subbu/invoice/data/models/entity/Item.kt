package com.subbu.invoice.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.subbu.invoice.data.models.enums.Status

@Entity(tableName = "Items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Float,
    val tax: Float,
    val status: Status,
) {
}

