package com.example.work_manager

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        button.setOnClickListener {
            // Отмена все воркеров
            WorkManager.getInstance(this).cancelAllWork()
        }

        // Запускает воркер один раз, отрабатывает сразу при запуске
        var myWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
        // Настраивает на выполнение каждые 15 мин
        var myWorkRequest2 = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
        WorkManager.getInstance(this).enqueue(myWorkRequest2)
    }
}