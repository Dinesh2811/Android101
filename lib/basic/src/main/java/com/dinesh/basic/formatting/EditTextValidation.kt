package com.dinesh.basic.formatting

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText

fun EditText.allowOnlyAlphabeticalAndSpace() {
    val filter = object : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (i in start until end) {
                if (!Character.isLetter(source[i]) && !Character.isSpaceChar(source[i])) {
//                if (!source.toString().matches(Regex("[a-zA-Z]+"))) {
                    return ""
                }
            }
            return null
        }
    }

    this.filters = arrayOf(filter)
}


fun EditText.disallowEmojis() {
    val filter = object : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i]).toByte()
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return ""
                }
            }
            return null
        }
    }

    this.filters = arrayOf(filter)
}