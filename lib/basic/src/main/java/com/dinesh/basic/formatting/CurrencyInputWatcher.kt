package com.dinesh.basic.formatting

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

private val localeCurrencyFormat = Locale.US

class CurrencyInputWatcher(private val editText: EditText, private val afterTextChangedAction: (String, BigDecimal) -> Unit) : TextWatcher {

    private val formatter = NumberFormat.getCurrencyInstance(localeCurrencyFormat)
    private val maxAmount = BigDecimal(1000000)
    init {
        (formatter as? java.text.DecimalFormat)?.apply {
            currency = Currency.getInstance(localeCurrencyFormat)
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    override fun afterTextChanged(editable: Editable) {
        val amountWithPrefix = editable.toString().replace(Regex("[^0-9]"), "")
        var doubleValue = amountWithPrefix.toDoubleOrNull() ?: 0.0
        var number = BigDecimal(doubleValue / 100)

        if (number > maxAmount) {
            number = maxAmount
        }

        val formattedValue = formatter.format(number)
        editText.removeTextChangedListener(this)
        editText.setText(formattedValue)
        editText.setSelection(formattedValue.length)
        editText.addTextChangedListener(this)
        afterTextChangedAction(formattedValue, editText.getNumericValue())
    }

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}
}


fun TextView.getNumericValue(setScale: Int = 2): BigDecimal {
    val amountWithPrefix = text.toString()
    val cleanedAmount = amountWithPrefix.replace(Regex("[^0-9]"), "")
    val doubleValue = cleanedAmount.toDoubleOrNull() ?: 0.0

    return BigDecimal(doubleValue / 100).setScale(setScale, RoundingMode.HALF_UP)
}


fun currencyFormat(amount: String): String {
    return try {
        val number = amount.toDouble()
        val formatter = DecimalFormat("###,###,##0.00", DecimalFormatSymbols.getInstance(localeCurrencyFormat))
        formatter.format(number)
    } catch (e: Exception) {
        e.printStackTrace()
        "0.00"
    }
}