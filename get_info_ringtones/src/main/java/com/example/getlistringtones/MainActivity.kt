package com.example.getlistringtones

import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ringtoneManager: RingtoneManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ringtoneManager = RingtoneManager(this)

        val cursor = ringtoneManager.cursor // Получаем курсор доступных рингтонов

        // Выводим имена столбцов курсов
        val a = cursor.columnNames
        a.forEach {
            Log.d("mine", it)
        }

        do {
            Log.d("mine", cursor.getString(cursor.getColumnIndex("_id"))) // Выводим строку столбца ID
            Log.d("mine", cursor.getString(cursor.getColumnIndex("title"))) // Выводим строку столбца заголовка
            Log.d("mine", cursor.getString(cursor.getColumnIndex("title_key"))) // Выводим строку столбца ID
        } while (cursor.moveToNext())
    }
}