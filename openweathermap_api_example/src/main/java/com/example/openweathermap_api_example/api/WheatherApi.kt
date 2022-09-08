package com.example.openweathermap_api_example.api

import retrofit2.Call
import retrofit2.http.GET

interface WheatherApi {
    @GET("data/2.5/weather" +
        "?lat=55.89700&lon=37.42970&appid=26fd0daa5594752eefdb75f754c37cb8")
    fun fetchWeather(): Call<WeatherResponse>
}