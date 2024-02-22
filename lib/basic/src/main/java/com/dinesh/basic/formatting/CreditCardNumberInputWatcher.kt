package com.dinesh.basic.formatting

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlin.math.min


class CreditCardNumberInputWatcher(
    private val editText: EditText,
    private val afterTextChangedAction: (formattedCardNumber: String, actualCardNumber: String) -> Unit
) : TextWatcher {

    private var isUpdating = false
    override fun afterTextChanged(s: Editable) {
        if (isUpdating) {
            isUpdating = false
            return
        }

        var str = s.toString().replace(Regex("\\s"), "")
        val formattedCardNumber = StringBuilder()

        for (i in str.indices) {
            if (i != 0 && i % 4 == 0) {
                formattedCardNumber.append(" ")
            }
            formattedCardNumber.append(str[i])
        }

        if (s.toString() != formattedCardNumber.toString()) {
            isUpdating = true
            editText.setText(formattedCardNumber.toString())
            val cursorPosition = min(formattedCardNumber.length, editText.length())
            editText.setSelection(cursorPosition)
        }

        afterTextChangedAction(formattedCardNumber.toString(), str)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}
