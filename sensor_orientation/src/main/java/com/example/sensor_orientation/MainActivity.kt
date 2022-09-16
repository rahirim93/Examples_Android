package com.example.sensor_orientation

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var mSensorManager: SensorManager
    private lateinit var mOrientation: Sensor

    private var xy_angle = 0f
    private var xz_angle = 0f
    private var zy_angle = 0f

    private lateinit var xyView: TextView
    private lateinit var xzView: TextView
    private lateinit var zyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xyView = findViewById(R.id.xyValue)
        xzView = findViewById(R.id.xzValue)
        zyView = findViewById(R.id.zyValue)
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            xy_angle = event.values[0]  //Плоскость XY
            xz_angle = event.values[1]; //Плоскость XZ
            zy_angle = event.values[2]; //Плоскость ZY
        }

        xyView.text = xy_angle.toInt().toString()
        xzView.text = xz_angle.toInt().toString()
        zyView.text = zy_angle.toInt().toString()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        super.onStop()
        mSensorManager.unregisterListener(this)
    }
}