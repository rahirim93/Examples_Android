package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 */

private const val NOTIFICATION_ID = 1
private const val CHANNEL_ID = "channelId"

class MainActivity : AppCompatActivity() {

    private lateinit var builder: NotificationCompat.Builder
    var counter = 0
    private lateinit var notificationManagerCompat: NotificationManagerCompat

    private lateinit var button: Button
    private lateinit var button2: Button

    var isWorking = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)

        notificationManagerCompat = NotificationManagerCompat.from(this)

        createNotificationChannel()

        button.setOnClickListener {

            isWorking = true

            builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("Some Text")
                .setContentText("Some Text Content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(notificationManagerCompat) {
                notify(NOTIFICATION_ID, builder.build())
            }

//            // we're starting a loop in a coroutine
//            GlobalScope.launch(Dispatchers.IO) {
//                while (isWorking) {
//                    launch(Dispatchers.IO) {
//                        counter += 5
//                        builder.setContentText("Counter is: $counter \n какой то текст")
//
//                        with(notificationManagerCompat) {
//                            notify(NOTIFICATION_ID, builder.build())
//                        }
//                    }
//                    delay(1000)
//                }
//                Log.d("TAG", "End of the loop for the service")
//            }
        }
        button2.setOnClickListener {
            isWorking = false
        }
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
}