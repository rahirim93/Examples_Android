package com.example.testmigration.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TestDao {

    @Query("SELECT * FROM TestEntity ORDER BY date ASC")
    fun getSalaries(): LiveData<List<TestEntity>>

    @Insert
    fun addSalary(salary: TestEntity)

    @Update
    fun updateSalary(salary: TestEntity)

    @Delete
    fun deleteSalary(salary: TestEntity)

}