package com.example.testmigration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.testmigration.database.TestEntity


class MainActivity : AppCompatActivity() {

    private val viewModel: MyAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testObserver = Observer<List<TestEntity>>{ _ ->

        }
        viewModel.listSalariesLiveData.observe(this, testObserver)

        val test = TestEntity()
        test.salary = 41000
        test.second = 32412
        //viewModel.addSalary(test)

    }
}