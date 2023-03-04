package com.example.gismeteo_api_example.api

import com.example.gismeteo_api_example.WeatherItem
import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("temperature")
    lateinit var weatherItem: WeatherItem
}