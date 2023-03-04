package com.example.api_yandex_weather_example

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.api_yandex_weather_example.api.WeatherResponse
import com.example.api_yandex_weather_example.api.WheatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "YandexFetchr"

class YandexFetchr {

    private val wheatherApi: WheatherApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            //.baseUrl("https://api.openweathermap.org")
            //.baseUrl("https://api.gismeteo.net")
            .baseUrl("https://api.weather.yandex.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        wheatherApi = retrofit.create(WheatherApi::class.java)
    }

    fun fetchWeather(): MutableLiveData<WeatherItem> {
        val responseLiveData: MutableLiveData<WeatherItem> = MutableLiveData()
        val yandexRequest: Call<WeatherResponse> = wheatherApi.fetchWeather()

        yandexRequest.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                Log.d(TAG, "Ответ получен: ${response.body()}")
                Log.d(TAG, "Успешность запроса: ${response.isSuccessful}")
                Log.d(TAG, "Ошибка запроса: ${response.errorBody()}")
                Log.d(TAG, "Код запроса: ${response.code()}")
                Log.d(TAG, "Заголовки: ${response.headers()}")

                val weatherResponse: WeatherResponse? = response.body()
                Log.d(TAG, "Ответ получен: ${response.body()}")
                val weatherItem: WeatherItem = weatherResponse?.weatherItem ?: WeatherItem()
                //Log.d(TAG, "${(weatherItem.temp - 273).toString()}")
                responseLiveData.value = weatherItem
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка запроса", t)
            }
        })
        return responseLiveData
    }

    fun fetchWeatherResponse(): Call<WeatherResponse> {
        return wheatherApi.fetchWeather()
    }
}