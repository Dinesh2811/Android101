package com.dinesh.basic.view

import android.content.Context
import android.content.res.ColorStateList
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.dinesh.xml.R


object Theme {

    fun setButtonTheme(textView: TextView, backgroundTintResId: Int, textColorResId: Int, context: Context) {
        // Set backgroundTint
        val backgroundTint = ContextCompat.getColor(context, backgroundTintResId)
        ViewCompat.setBackgroundTintList(textView, ColorStateList.valueOf(backgroundTint))

        // Set textColor
        val textColor = ContextCompat.getColor(context, textColorResId)
        textView.setTextColor(textColor)
    }

    enum class ButtonTheme(val backgroundTintResId: Int, val textColorResId: Int) {
        DEFAULT(R.color.btn_default, R.color.white),
        DISABLE(R.color.btn_disable, R.color.btn_disable_text)
    }

    fun setButtonTheme(textView: TextView, buttonTheme: ButtonTheme, context: Context) {
        // Set backgroundTint
        val backgroundTint = ContextCompat.getColor(context, buttonTheme.backgroundTintResId)
        ViewCompat.setBackgroundTintList(textView, ColorStateList.valueOf(backgroundTint))

        // Set textColor
        val textColor = ContextCompat.getColor(context, buttonTheme.textColorResId)
        textView.setTextColor(textColor)
    }

}