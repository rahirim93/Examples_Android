package com.example.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

/** Пример использования будильника с отправкой уведомления при срабатывании
 * Пример использования своего BroadcastReceiver
 * Добавить звонок и с новым активити*/

private const val CHANNEL_ID = "channelId"

class MainActivity : AppCompatActivity() {

    private val TAG = "alarmExample"

    private lateinit var buttonSetAlarm: Button

    private lateinit var buttonTenSeconds: Button

    private lateinit var alarmManager: AlarmManager

    private lateinit var simpleDateFormat: SimpleDateFormat

    private lateinit var materialTimePicker: MaterialTimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log("onCreate")

        init()

    }


    // Оставляем как есть
    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val alarmInfoIntent = Intent(this, MainActivity::class.java)
        alarmInfoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    private fun init() {

        initMaterialTimePicker()

        initButtons()

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    }

    private fun setAlarm(calendar: Calendar, alarmActionPendingIntent: PendingIntent) {
        val alarmClockInfo = AlarmManager.AlarmClockInfo(calendar.timeInMillis, getAlarmInfoPendingIntent())
        alarmManager.setAlarmClock(alarmClockInfo, alarmActionPendingIntent)
        Toast.makeText(this, "Будильник установлен на ${simpleDateFormat.format(calendar.time)}", Toast.LENGTH_SHORT).show()
    }

    private fun initMaterialTimePicker() {
        materialTimePicker = MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
            .setMinute(Calendar.getInstance().get(Calendar.MINUTE) + 1)
            .setTitleText("Выберете время для будильника")
            .build()

        materialTimePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.set(Calendar.MINUTE, materialTimePicker.minute)
            calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.hour)


            //finish()
        }
    }

    private fun initButtons() {
        buttonTenSeconds = findViewById(R.id.buttonTenSeconds)
        buttonTenSeconds.setOnClickListener {

            val calendar = Calendar.getInstance().timeInMillis + 5000
            val calendar2 = Calendar.getInstance()
            calendar2.timeInMillis = calendar

            var pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                Intent(this, Receiver::class.java),
                0
            )

            setAlarm(calendar2, pendingIntent)
            finish()
        }

        buttonSetAlarm = findViewById(R.id.buttonSetAlarm)
        buttonSetAlarm.setOnClickListener {
            materialTimePicker.show(supportFragmentManager, "alarm")
        }
    }

    private fun log(string: String) {
        Log.d(TAG, string)
    }
}