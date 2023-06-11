package com.example.testmigration

import android.app.Application

class MyAppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MyAppRepository.initialize(this)

    }
}