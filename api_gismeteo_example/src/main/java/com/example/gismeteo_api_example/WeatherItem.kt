package com.example.gismeteo_api_example

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("air") var air: Float = 0.0F,
    //var pressure: Int = 0
)