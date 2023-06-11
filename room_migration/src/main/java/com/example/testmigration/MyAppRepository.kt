package com.example.testmigration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.testmigration.database.TestDatabase
import com.example.testmigration.database.TestEntity
import java.util.concurrent.Executors

private const val DATABASE_NAME = "test_database"


class MyAppRepository private constructor(context: Context){

    private val database : TestDatabase = Room.databaseBuilder(
        context.applicationContext,
        TestDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val testDao = database.testDao()

    private val executor = Executors.newSingleThreadExecutor()

    fun getSalaries(): LiveData<List<TestEntity>> = testDao.getSalaries()

    fun addSalary(salary: TestEntity) {
        executor.execute {
            testDao.addSalary(salary)
        }
    }

    fun updateSalary(salary: TestEntity){
        executor.execute {
            testDao.updateSalary(salary)
        }
    }

    fun deleteSalary(salary: TestEntity){
        executor.execute {
            testDao.deleteSalary(salary)
        }
    }

    companion object {
        private var INSTANCE: MyAppRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MyAppRepository(context)
            }
        }

        fun get(): MyAppRepository {
            return INSTANCE ?:
            throw IllegalStateException("BatteryRepository must be initialized")
        }
    }

}