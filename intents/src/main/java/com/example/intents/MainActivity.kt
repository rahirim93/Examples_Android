package com.example.intents

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/** Пример использования pendingIntents
 * При использовании BroadcastReceiver не забываем прописать его в манифесте.
 * При нажатии на кнопку отправлятся уведомление
 * При нажатии на уведомление отправляется pendingIntent
 * Система получив интент посредством класса Receiver пишет сообщение в лог
 */

private const val CHANNEL_ID = "channelId"

class MainActivity : AppCompatActivity() {

    private val TAG = "intentsExample"

    private lateinit var button: Button

    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var notificationBuilder: NotificationCompat.Builder

    private lateinit var intent1: Intent
    private lateinit var pIntent1: PendingIntent



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log("onCreate")

        init()
    }

    private fun sendNotif(id: Int, pIntent: PendingIntent) {
        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("Title $id")
            .setContentText("Content $id")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
        with(notificationManager) {
            notify(id, notificationBuilder.build())
        }
    }

    private fun createIntent(action: String, extra: String): Intent {
        var intent = Intent(this, Receiver::class.java)
        intent.action = action
        intent.putExtra("extra", extra)
        return intent
    }

    private fun createNotificationChannel() {
        val name = "myChannel"
        val descriptionText = "descriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun log(string: String) {
        Log.d(TAG, string)
    }

    private fun init() {
        button = findViewById(R.id.button)
        button.setOnClickListener {
            intent1 = createIntent("action 1", "extra 1")
            pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0)
            sendNotif(1, pIntent1)
        }
        notificationManager = NotificationManagerCompat.from(this)
        createNotificationChannel()
    }
}