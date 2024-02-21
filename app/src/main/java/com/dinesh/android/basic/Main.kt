package com.dinesh.android.basic

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.basic.util.AccountType
import com.dinesh.basic.formatting.CurrencyInputWatcher
import com.dinesh.basic.formatting.currencyFormat
import com.dinesh.basic.formatting.getNumericValue
import com.dinesh.basic.view.DatePickerHelper.showDatePicker
import com.dinesh.xml.databinding.BasicMainBinding
import java.math.BigDecimal

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Main: AppCompatActivity() {
    private lateinit var binding: BasicMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BasicMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectDate()
        autoCompleteTextViewSpinner()
        currencyFormatInputEditText()
    }

    private fun currencyFormatInputEditText() {
        binding.currencyFormatInputEditText.setText("$${currencyFormat("20123.56")}")
        val currencyFormatInputEditTextValue: BigDecimal = binding.currencyFormatInputEditText.getNumericValue()
        Log.i(TAG, "currencyFormatInputEditText: currencyFormatInputEditTextValue --> $currencyFormatInputEditTextValue")
        binding.currencyFormatInputEditText.addTextChangedListener(CurrencyInputWatcher(binding.currencyFormatInputEditText) { text, value ->
            Log.i(TAG, "currencyFormatInputEditText: text --> $text value --> $value")
        })
    }

    private fun autoCompleteTextViewSpinner() {
//        autoCompleteTextViewSpinnerV1(AccountType.entries.map { it.displayText })
        autoCompleteTextViewSpinnerV2(AccountType.entries.map { it.displayText })
    }

    private fun autoCompleteTextViewSpinnerV1(list: List<String> = emptyList()) {
        binding.autoCompleteTextViewSpinner.setAdapter(ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, list))
        binding.autoCompleteTextViewSpinner.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Log.i(TAG, "onItemSelected: position $selectedItem")
        }
        binding.autoCompleteTextViewSpinner.isFocusable = false

        binding.autoCompleteTextViewSpinner.setOnClickListener {
            binding.autoCompleteTextViewSpinner.showDropDown()
        }
    }

    private fun autoCompleteTextViewSpinnerV2(list: List<String> = emptyList()) {
        val adapter = object : ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, list) {
            override fun getFilter(): Filter {
                return object : Filter() {
                    override fun performFiltering(constraint: CharSequence?): FilterResults {
                        return FilterResults().apply { values = list; count = list.size }
                    }

                    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                        notifyDataSetChanged()
                    }
                }
            }
        }

        binding.autoCompleteTextViewSpinner.setAdapter(adapter)
        binding.autoCompleteTextViewSpinner.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Log.i(TAG, "onItemSelected: position $selectedItem")
        }
        binding.autoCompleteTextViewSpinner.isFocusable = false

        binding.autoCompleteTextViewSpinner.setOnClickListener {
            binding.autoCompleteTextViewSpinner.showDropDown()
        }
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
