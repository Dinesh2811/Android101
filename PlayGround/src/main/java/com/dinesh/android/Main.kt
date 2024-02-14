package com.dinesh.android

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.databinding.MainBinding
import com.dinesh.basic.view.DatePickerHelper.showDatePicker

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

        binding.phoneNumberInputEditText.addTextChangedListener(PhoneNumberInputWatcher(binding.phoneNumberInputEditText))

    }
}


class PhoneNumberInputWatcher(private val editText: EditText) : TextWatcher {

    private var isUpdating = false
    private var oldString = ""

    override fun afterTextChanged(s: Editable) {
        if (isUpdating) {
            isUpdating = false
            return
        }

        var str = s.toString().replace(Regex("[^0-9]"), "")
        var phoneNumber = ""

        if (str.length > 0) {
            phoneNumber += "("
        }
        if (str.length > 3) {
            phoneNumber += str.substring(0, 3) + ") "
            str = str.substring(3)
        }
        if (str.length > 3) {
            phoneNumber += str.substring(0, 3) + "-"
            str = str.substring(3)
        }
        if (str.isNotEmpty()) {
            phoneNumber += str
        }

        isUpdating = true
        editText.setText(phoneNumber)
        editText.setSelection(phoneNumber.length)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        oldString = s.toString()
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}
