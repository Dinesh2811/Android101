package com.dinesh.android.basic

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.basic.view.DatePickerHelper.showDatePicker
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
