package com.example.testmigration

import androidx.lifecycle.ViewModel
import com.example.testmigration.database.TestEntity

class MyAppViewModel: ViewModel() {

    private val salaryRepository = MyAppRepository.get()

    fun addSalary(salary: TestEntity){
        salaryRepository.addSalary(salary)
    }

    val listSalariesLiveData = salaryRepository.getSalaries()
}