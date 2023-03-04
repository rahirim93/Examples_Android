package com.example.api_yandex_weather_example.api

import com.example.api_yandex_weather_example.WeatherItem
import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("fact")
    lateinit var weatherItem: WeatherItem
}