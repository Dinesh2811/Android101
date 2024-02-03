package com.dinesh.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.databinding.MainBinding
import com.dinesh.basic.DatePickerHelper.showDatePicker

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Main: AppCompatActivity() {
    private lateinit var binding: MainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            selectDate.setOnClickListener {
                showDatePicker(this@Main, apiDateFormat = { apiDateFormat ->
//                    Log.i(TAG, "onCreate: ${apiDateFormat}")
                }) { calendar, formattedDate ->
                    Log.i(TAG, "onCreate: ${formattedDate}")
                }
            }
        }

    }
}
