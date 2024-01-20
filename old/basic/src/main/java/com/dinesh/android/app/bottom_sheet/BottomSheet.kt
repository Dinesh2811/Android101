package com.dinesh.android.app.bottom_sheet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R

private val TAG = "log_" + BottomSheet::class.java.name.split(BottomSheet::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class BottomSheet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_main)

    }
}