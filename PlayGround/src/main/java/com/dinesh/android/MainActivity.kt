package com.dinesh.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity: ComponentActivity() {
    private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
