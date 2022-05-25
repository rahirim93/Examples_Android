package com.example.isbatteryoptimazedcheck

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        button.setOnClickListener {
            // Вывод в лог состояния приложения, применена ли к нему оптимизация батареи
            val powerManager = getSystemService(POWER_SERVICE) as PowerManager
            val isIgnoring = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                powerManager.isIgnoringBatteryOptimizations("com.example.isbatteryoptimazedcheck")
            } else {
                TODO("VERSION.SDK_INT < M")
            }
            textView.text = "Is ignoring is $isIgnoring"
        }
    }
}