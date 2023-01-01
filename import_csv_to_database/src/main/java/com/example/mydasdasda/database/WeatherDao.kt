package com.example.testworker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherItemDB")
    fun getWeatherItemsBDLiveData(): LiveData<List<WeatherItemDB>>

    @Insert
    fun addItem(weatherItemDB: WeatherItemDB)

    @Query("DELETE FROM WeatherItemDB")
    fun deleteAll()
}