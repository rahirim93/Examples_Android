package com.example.openweathermap_api_example.api

import com.example.openweathermap_api_example.WeatherItem
import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("main")
    lateinit var weatherItem: WeatherItem
}