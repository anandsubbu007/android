package com.subbu.invoice.data.models.converter


import android.renderscript.RenderScript.Priority
import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDateTime(date: String): LocalDateTime {
        return LocalDateTime.parse(date)
    }
}

