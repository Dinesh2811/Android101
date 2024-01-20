package com.dinesh.android.kotlin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain

private val TAG = "log_" + LayoutInflaterClass::class.java.name.split(LayoutInflaterClass::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class LayoutInflaterClass : ToolbarMain() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.activity_main)

        layoutInflater(R.layout.fancy_snackbar_layout)

    }

    private fun layoutInflater(fancySnackBarLayout: Int) {
        val frameLayoutSnackBar: FrameLayout = findViewById(R.id.framelayout)
        val layoutParams = frameLayoutSnackBar.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.verticalBias = 1.0f
        frameLayoutSnackBar.layoutParams = layoutParams
        val v: View = LayoutInflater.from(this).inflate(fancySnackBarLayout, frameLayoutSnackBar, false)
        frameLayoutSnackBar.addView(v)
    }
}