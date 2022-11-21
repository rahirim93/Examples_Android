package com.example.testwidget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "Receiver"
class Receiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Сработал ресивер", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onReceive: ")
    }
}