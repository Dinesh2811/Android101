package com.dinesh.basic.formatting

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


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