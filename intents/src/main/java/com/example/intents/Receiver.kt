package com.example.intents

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.PackageManagerCompat.LOG_TAG

class Receiver : BroadcastReceiver() {

    private val TAG = "intentsExample"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive")
        Log.d(TAG, "action = ${intent?.action}")
        Log.d(TAG, "extra = ${intent?.getStringExtra("extra")}")
    }
}