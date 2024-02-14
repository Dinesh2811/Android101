package com.dinesh.basic.formatting

import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan


fun getSpannableString(text: String, integerPartSize: Int, decimalPartSize: Int): SpannableString {
    val spannableString = SpannableString(text)
    val dotIndex = text.indexOf('.')
    if (dotIndex != -1) {
        spannableString.setSpan(AbsoluteSizeSpan(integerPartSize, true), 0, dotIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(AbsoluteSizeSpan(decimalPartSize, true), dotIndex + 1, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spannableString
}

