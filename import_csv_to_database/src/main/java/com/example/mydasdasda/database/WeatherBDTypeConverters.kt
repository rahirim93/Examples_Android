package com.example.testworker.database

import androidx.room.TypeConverter
import java.util.*

class WeatherBDTypeConverters {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
    @TypeConverter
    fun toTempFloat(temp: Long): Float {
        return (temp/100000.0).toFloat()
    }
    @TypeConverter
    fun toTempInt(temp: Float): Long {
        return (temp * 100000.0).toLong()
    }
}