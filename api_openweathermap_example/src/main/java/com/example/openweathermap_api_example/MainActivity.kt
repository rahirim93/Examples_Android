package com.example.openweathermap_api_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/** Пример использования openweathermap API.
 * Выводит температуру по координатам (в Химках)
 * Запрос к API и вывод в поле текста.
 * Не забываем прописать разрешение доступа к интернету в манифесте.
 * Все инструкции и ключ на сайте API.
 * Не забываем добавить зависимости.*/

private const val TAG = "WeatherRequest"

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        val weatherLiveData: LiveData<WeatherItem> = YandexFetchr().fetchWeather()
        weatherLiveData.observe(
            this,
            Observer {
                Log.d(TAG, "Температура: ${(it.temp - 273).toString()}")
                textView.text = "Температура: " + (it.temp).toString() + " градусов в Химках"
                textView.append( "\nДавление: " + it.pressure.toString() + " гПа в Химках")
            })
    }
}