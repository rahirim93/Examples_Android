package com.example.testgps

import android.location.Location
import android.location.LocationListener

class MyLocListener: LocationListener {

    interface Callbacks {
        fun onLocChanged(loc: Location)
    }

    private var callbacks: Callbacks? = null

    override fun onLocationChanged(p0: Location) {
        callbacks?.onLocChanged(p0)
    }

    fun initCallback(callbacks: Callbacks) {
        this.callbacks = callbacks
    }
}