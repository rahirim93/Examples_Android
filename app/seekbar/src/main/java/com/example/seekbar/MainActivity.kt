package com.example.seekbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

/** Описание свойств разметки
android:min="1"         - минимальное значение seekBar
android:max="30"        - максимальное значение seekBar
android:progress="1"    - начальное значение seekBar
onProgressChanged       - слушатель за изменением состояния ползунка
onStartTrackingTouch    - слушатель за началом движения (при касании)
onStopTrackingTouch     - слушатель за концом движения (при отпускании)
 */

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var textView2: TextView
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        seekBar = findViewById(R.id.seekBar)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "Прогресс: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                textView2.text = "Начало передвижения"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                textView2.text = "Конец передвижения"
            }

        })

    }
}