package com.example.api_yandex_weather_example.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

private const val LAT = "55.89700"
private const val LON = "37.42970"


interface WheatherApi {
    @Headers("X-Yandex-API-Key: 3e4bf7eb-9184-4920-b54b-d78521cf6c96")
    @GET("v2/informers?lat=$LAT&lon=$LON")
    fun fetchWeather(): Call<WeatherResponse>
}