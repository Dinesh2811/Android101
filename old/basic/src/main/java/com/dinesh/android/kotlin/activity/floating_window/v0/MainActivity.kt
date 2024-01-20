package com.dinesh.android.kotlin.activity.floating_window.v0

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import com.dinesh.android.R

private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class MainActivity : AppCompatActivity() {
    private lateinit var floatingWindowServiceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.floating_layout_main)

        val showButton = findViewById<Button>(R.id.showButton)
        val removeButton = findViewById<Button>(R.id.removeButton)

        showButton.setOnClickListener {
            startFloatingWindowService()
        }

        removeButton.setOnClickListener {
            stopFloatingWindowService()
        }

        val rootView = findViewById<ConstraintLayout>(R.id.root_layout)
        rootView.doOnLayout {
            val rootViewWidth = rootView.width
            val rootViewHeight = rootView.height
            Log.i(TAG, "Width: $rootViewWidth, Height: $rootViewHeight")    //  Width: 1080, Height: 2228
            floatingWindowServiceIntent.putExtra("rootWidth", rootViewWidth)
            floatingWindowServiceIntent.putExtra("rootHeight", rootViewHeight)
        }

        floatingWindowServiceIntent = Intent(this, FloatingWindowService::class.java)
    }

    private fun startFloatingWindowService() {
        startService(floatingWindowServiceIntent)
    }

    private fun stopFloatingWindowService() {
        stopService(floatingWindowServiceIntent)
    }
}
