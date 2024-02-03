package com.dinesh.basic

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.basic.app.Constants


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("MySharedPreferences_${packageName}", AppCompatActivity.MODE_PRIVATE)
        Constants.init(sharedPreferences)
    }
}