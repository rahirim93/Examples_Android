package com.example.work_manager

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {
        val resources = context.resources
        val notification = NotificationCompat
            .Builder(context, "rahirim")
            .setTicker("Hello")
            .setSmallIcon(android.R.drawable.ic_menu_report_image)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(0, notification)

        return Result.success()
    }

}