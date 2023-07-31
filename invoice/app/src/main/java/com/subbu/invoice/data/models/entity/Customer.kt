package com.subbu.invoice.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Customer")
data class Customer (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val gst: String,
    val mobileNo: String,
    val details:String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
//    val formattedCreatedAt: String
//        get() = createdAt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}