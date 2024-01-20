package com.dinesh.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Main: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun testing() {
        Log.i(TAG, "testing: lib")
    }
}
