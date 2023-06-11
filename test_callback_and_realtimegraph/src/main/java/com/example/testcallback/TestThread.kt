package com.example.testcallback

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.core.content.ContextCompat.registerReceiver

class TestThread(private val callback: MyCallback, context: Context): Thread() {

    private val batteryManager = context.getSystemService(Service.BATTERY_SERVICE) as BatteryManager
    private val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    val batteryStatus = context.registerReceiver(null, intentFilter)
    override fun run() {
        while (true) {
            sleep(1000)
            //callback.onWork(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW))
            callback.onWork(batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)!!.toInt())

        }
    }
}

interface MyCallback {
    fun onWork(num: Int)
}