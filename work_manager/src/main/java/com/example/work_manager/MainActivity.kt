package com.example.work_manager

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.DurationUnit

private const val WORK_TAG = "work tag"

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    private lateinit var buttonStart: Button
    private lateinit var buttonCancel: Button
    private lateinit var buttonCancelByTag: Button
    private lateinit var buttonDelete: Button
    private lateinit var buttonDeleteCancel: Button

    private lateinit var workManager: WorkManager
    private lateinit var workerObserver: Observer<List<WorkInfo>>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        textView.gravity = Gravity.LEFT

        workManager = WorkManager.getInstance(this)
        // Выполнение работы единожды и сразу
        val myWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .addTag(WORK_TAG)
            .build()
        // Выполнение работы единожды с задержкой
        val myWorkRequest4: WorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .addTag(WORK_TAG)
            .setInitialDelay(5000, TimeUnit.MILLISECONDS)
            .build()
        // Настраивает на выполнение каждые 15 мин
        val myWorkRequest2 = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .addTag(WORK_TAG)
            .build()


        // Вывод информации о задачах WorkManager
        workerObserver = Observer {
            textView.text = "Кол-во: ${it.size}\n"
            it.forEach { workInfo ->
                textView.append("\n${workInfo.id}\n${workInfo.outputData}\n${workInfo.progress}\n${workInfo.state}\n${workInfo.runAttemptCount}\n${workInfo.tags}\n")
            }
        }
        workManager.getWorkInfosByTagLiveData(WORK_TAG).observe(this, workerObserver)


        buttonStart = findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener {
            // Ставим задачи в очередь
            workManager.enqueue(myWorkRequest)
            workManager.enqueue(myWorkRequest2)
            workManager.enqueue(myWorkRequest4)
        }

        buttonCancel = findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            // Отменяем все работы (при этом они не удаляются)
            workManager.cancelAllWork()
        }
        buttonCancelByTag = findViewById(R.id.buttonCancelByTag)
        buttonCancelByTag.setOnClickListener {
            // Отменяем все работы с заданным тэгом (при этом они не удаляются)
            workManager.cancelAllWorkByTag(WORK_TAG)
        }

        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            // Удаляем все работы (удалятся только завершенные или отмененны, те что запланированы, но не выполнены останутся)
            workManager.pruneWork()
        }

        buttonDeleteCancel = findViewById(R.id.buttonDeleteCancel)
        buttonDeleteCancel.setOnClickListener {
            // Отменяем и удаляем
            workManager.cancelAllWork()
            workManager.pruneWork()
        }
    }
}