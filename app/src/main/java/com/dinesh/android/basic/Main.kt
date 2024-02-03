package com.dinesh.android.basic

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.*
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.dinesh.basic.DatePickerHelper
import com.dinesh.basic.DatePickerHelper.showDatePicker
import com.dinesh.xml.databinding.BasicMainBinding

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Main: AppCompatActivity() {
    private lateinit var binding: BasicMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BasicMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectDate()
    }

    private fun selectDate() {
        binding.selectDate.setOnClickListener {
            showDatePicker(this, apiDateFormat = { apiDateFormat ->
                Log.d(TAG, "selectDate: apiDateFormat --> ${apiDateFormat}")
            }) { calendar, formattedDate ->
                Log.i(TAG, "selectDate: formattedDate --> ${formattedDate}")
            }
        }
    }
}
