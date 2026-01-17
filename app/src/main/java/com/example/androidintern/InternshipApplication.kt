package com.example.androidintern

import android.app.Application

class InternshipApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        StrictModeInitializer.init()
    }
}