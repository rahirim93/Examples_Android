package com.example.testgps

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest
import kotlin.math.log

// Начинает показывать не сразу.

class MainActivity : AppCompatActivity(), MyLocListener.Callbacks {
    private lateinit var locationManager: LocationManager
    private lateinit var myLocListener: MyLocListener
    private lateinit var textView: TextView
    private var lastLocation: Location? = null
    private var distance = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            1 )
    }

    private fun checkPermission() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions()

        } else {
            Toast.makeText(this, "Разрешения даны", Toast.LENGTH_SHORT).show()
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1.0F, myLocListener)
        }
    }

    private fun init() {
        textView = findViewById(R.id.textView)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        myLocListener = MyLocListener()
        myLocListener.initCallback(this)
        checkPermission()
    }

    override fun onLocChanged(loc: Location) {
        if (loc.hasSpeed() && lastLocation != null && loc.speed > 0.0) {
            textView.text = "Скорость в м/с ${loc.speed} \n" +
                    "Скорость в км/ч: ${loc.speed*3600/1000} \n"
            distance += lastLocation!!.distanceTo(loc)
            textView.append("Дистанция: $distance")
            Log.d("rahirim","${loc.speed}")
        }
        lastLocation = loc
    }
}