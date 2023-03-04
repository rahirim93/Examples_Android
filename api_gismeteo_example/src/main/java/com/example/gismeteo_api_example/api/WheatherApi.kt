package com.example.gismeteo_api_example.api

import com.example.gismeteo_api_example.api.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

//private const val LAT = "55.89700"
//private const val LON = "37.42970"
private const val LAT = "55.89"
private const val LON = "37.42"

interface WheatherApi {
    @Headers("X-Gismeteo-Token: 56b30cb255.3443075")
    @GET("v2/weather/current/?latitude=$LAT&longitude=$LON")
    fun fetchWeather(): Call<WeatherResponse>
}