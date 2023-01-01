package com.example.mydasdasda

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.testworker.database.WeatherDatabase
import com.example.testworker.database.WeatherItemDB
import java.util.concurrent.Executors

private const val DATABASE_NAME = "weather_database"

class WeatherRepository private constructor(context: Context) {

    private val database : WeatherDatabase = Room.databaseBuilder(
        context.applicationContext,
        WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val weatherDao = database.weatherDao()

    private val executor = Executors.newSingleThreadExecutor()

    fun getWeatherItemsBDLiveData(): LiveData<List<WeatherItemDB>> = weatherDao.getWeatherItemsBDLiveData()

    fun addItem(weatherItemDB: WeatherItemDB) {
        executor.execute {
            weatherDao.addItem(weatherItemDB)
        }
    }

    fun deleteAll() {
        executor.execute {
            weatherDao.deleteAll()
        }
    }

    companion object {
        private var INSTANCE: WeatherRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = WeatherRepository(context)
            }
        }

        fun get(): WeatherRepository {
            return INSTANCE ?:
            throw IllegalStateException("BatteryRepository must be initialized")
        }
    }
}