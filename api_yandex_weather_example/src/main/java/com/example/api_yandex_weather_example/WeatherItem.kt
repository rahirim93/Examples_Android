package com.example.api_yandex_weather_example

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("temp") var temp: Float = 0.0F,
    @SerializedName("pressure_mm") var pressure: Float = 0.0F,
    @SerializedName("humidity") var humidity: Float = 0.0F
)