package com.example.testworker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class WeatherItemDB(@PrimaryKey var id: UUID = UUID.randomUUID(),
                         var dateInMillis: Long = 0,
                         var temperature: Float = 0.0F,
                         var pressure: Int = 0)