package com.example.mydasdasda

import androidx.lifecycle.ViewModel
import com.example.testworker.database.WeatherItemDB

class WeatherViewModel: ViewModel() {

    private val weatherRepository = WeatherRepository.get()

    var weatherItemsLiveData = weatherRepository.getWeatherItemsBDLiveData()

    fun addItem(weatherItemDB: WeatherItemDB) = weatherRepository.addItem(weatherItemDB)

    fun deleteAll() = weatherRepository.deleteAll()
}