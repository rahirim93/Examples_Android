package com.example.class_calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Функция показывает количетво дней до аванса и ЗП
    fun daysToPay() {
        val today = Calendar.getInstance() // Сегодняшняя дата

        // Аванс в этом месяце
        val prePayDay = Calendar.getInstance()
        prePayDay.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), 27, 0, 0, 0)

        // ЗП в следующем месяце
        val payDayNextMonth = Calendar.getInstance()
        payDayNextMonth.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, 12, 0, 0, 0)

        val dateFormat = SimpleDateFormat("dd.MM.yyyy") //Настройка формата вывода в кнопку


        Log.d("rahirim", dateFormat.format(prePayDay.time))
        Log.d("rahirim", dateFormat.format(payDayNextMonth.time))

        //int days = Duration.between(calendar1.toInstant(), calendar2.toInstant()).toDays();

        val daysToPrePay = Duration.between(today.toInstant(), prePayDay.toInstant()).toDays()
        val daysToPay = Duration.between(today.toInstant(), payDayNextMonth.toInstant()).toDays()

        Log.d("rahirim", daysToPrePay.toString())
        Log.d("rahirim", daysToPay.toString())

        //val calendar = Calendar.getInstance() // Текущая дата
        //calendar.set(year, month, dayOfMonth) // Изменяем на выбранную
        //val dateFormat = SimpleDateFormat("dd.MM.yyyy") //Настройка формата вывода в кнопку
        //buttonDate.text = dateFormat.format(calendar.time) // Вывод в кнопку
    }
}