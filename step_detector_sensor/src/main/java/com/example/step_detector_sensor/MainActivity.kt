package com.example.step_detector_sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

/**
 * Пример использования датчика начала движения
 */

class MainActivity : AppCompatActivity(), SensorEventListener  {

    private lateinit var sensor: Sensor

    private lateinit var sensorManager: SensorManager

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.values?.get(0)!!.toInt() == 1) {
            textView.text = "Движение"
        } else textView.text = "Остановка"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}