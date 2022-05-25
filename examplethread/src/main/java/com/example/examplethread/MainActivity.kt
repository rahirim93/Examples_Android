package com.example.examplethread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    var startStop = false // Переменная для подключения/отключения нового потока

    var counter = 0 // Счетчик

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        Thread {
            startStop = true
            while (startStop) {
                Thread.sleep(1000) // Усыпляем поток на секунду
                counter++ // Увеличиваем счетчик на один
                /**
                 * Запрещается обновлять элементы пользовательского интерфейса
                 * с неосновного потока. Для этого используем runOnUiThread,
                 * который позволяет сделать это
                 */
                runOnUiThread {
                    textView.text = counter.toString() // Обновляем текст textView
                }
            }
        }.start() // Запускаем поток
    }
}