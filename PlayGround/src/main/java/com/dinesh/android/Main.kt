package com.dinesh.android

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.dinesh.android.databinding.MainBinding
import com.dinesh.basic.view.DatePickerHelper.showDatePicker
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


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

//        Log.i(TAG, "onCreate: ${formatPhoneNumberWithCountryCode("")}")
//        Log.i(TAG, "onCreate: ${formatPhoneNumberWithCountryCode("8")}")
//        Log.i(TAG, "onCreate: ${formatPhoneNumberWithCountryCode("86")}")
//        Log.i(TAG, "onCreate: ${formatPhoneNumberWithCountryCode("866")}")
//        Log.i(TAG, "onCreate: ${formatPhoneNumberWithCountryCode("8667")}")

        val layout = binding.phoneNumberInputEditText
        layout.post {
            val widthPx = layout.width
            val heightPx = layout.height

            val widthDp = pxToDp(widthPx)
            val heightDp = pxToDp(heightPx)

            Log.d(TAG, "Width in dp: $widthDp, Height in dp: $heightDp")
        }

        binding.phoneNumberInputEditText.addTextChangedListener(PhoneNumberInputWatcher(binding.phoneNumberInputEditText){ formattedPhoneNumber, actualPhoneNumber ->
            Log.i(TAG, "11: formattedPhoneNumber --> ${formattedPhoneNumber} actualPhoneNumber --> ${actualPhoneNumber}")
        })

//        val phoneNumberUtil = PhoneNumberUtil.getInstance()
//        val rawPhoneNumber = Phonenumber.PhoneNumber()
//        rawPhoneNumber.nationalNumber = 8667024800
//        rawPhoneNumber.countryCode = 1
//        val isValid = phoneNumberUtil.isValidNumber(rawPhoneNumber)
//        Log.i(TAG, "onCreate: ${rawPhoneNumber}     $isValid")
//        Log.i(TAG, "hi1: ${phoneNumberUtil.parse("8667024800", Locale.US.country).countryCode}     ${Locale.US.country}     ${Locale.US.displayCountry}")
//        val internationalFormat = phoneNumberUtil.format(rawPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
    }
    private fun pxToDp(px: Int): Float {
        return px / Resources.getSystem().displayMetrics.density
    }

}




class PhoneNumberInputWatcher(
    private val editText: EditText,
    private val afterTextChangedAction: (formattedPhoneNumber: String, actualPhoneNumber: String) -> Unit
) : TextWatcher {

    private var isUpdating = false
    private var oldString = ""

    override fun afterTextChanged(s: Editable) {
        if (isUpdating) {
            isUpdating = false
            return
        }

        var str = s.toString().replace(Regex("[^0-9]"), "")
        var formattedPhoneNumber = ""

        if (str.length > 0) {
            formattedPhoneNumber += "("
        }
        if (str.length > 3) {
            formattedPhoneNumber += str.substring(0, 3) + ") "
            str = str.substring(3)
        }
        if (str.length > 3) {
            formattedPhoneNumber += str.substring(0, 3) + "-"
            str = str.substring(3)
        }
        if (str.isNotEmpty()) {
            formattedPhoneNumber += str
        }

        isUpdating = true
        editText.setText(formattedPhoneNumber)
        editText.setSelection(formattedPhoneNumber.length)

        afterTextChangedAction(formattedPhoneNumber, s.toString().replace(Regex("[^0-9]"), ""))
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        oldString = s.toString()
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}