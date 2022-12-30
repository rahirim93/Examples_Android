package com.example.openweathermap_api_example

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("temp") var temp: Float = 0.0F,
    var pressure: Int = 0
)