package com.example.androidintern

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy

object StrictModeInitializer {
    fun init() {
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyFlashScreen()
                .build()
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )
    }
}