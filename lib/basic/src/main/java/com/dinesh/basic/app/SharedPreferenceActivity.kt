package com.dinesh.basic.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private val TAG = "log_" + SharedPreferenceDelegate::class.java.name.split(SharedPreferenceDelegate::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

/**
 * # Destructuring Declaration
 *
 *  *Don't*
 *
 *     private fun usualMethod() {
 *         //  Global declaration
 *         val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
 *         val editor: SharedPreferences.Editor = sharedPreferences.edit()
 *
 *         //  Save the data using shared preferences
 *         editor.putString("Theme", "Theme.Material3")
 *         editor.apply() //Important
 *
 *         //  Get the shared preferences data
 *         val currentTheme = sharedPreferences.getString("Theme", "Theme.Material3")
 *         Log.e(TAG, "Get the shared preferences data: ----> $currentTheme")
 *     }
 *
 *  *Do*
 *
 *         //  Save the data using shared preferences
 *         var theme: String by sharedPreferences("Theme")
 *         theme = "Theme.Material3.Dark"
 *
 *         //  Get the data using shared preferences
 *         Log.i(TAG, "onCreate: $theme")
 *
 */

class SharedPreferenceDelegate(context: Context, private val name: String, private val defaultValue: String = "") : ReadWriteProperty<Any?, String> {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPreferences", AppCompatActivity.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return sharedPreferences.getString(name, defaultValue) ?: defaultValue
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        editor.putString(name, value).apply()
    }
}

fun Context.sharedPreferences(name: String) = SharedPreferenceDelegate(this, name)

/*

fun <T> Context.sharedPreferences(name: String, defaultValue: T) = SharedPreferenceDelegate(this, name, defaultValue)

class SharedPreferenceDelegate<T>(context: Context, private val name: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPreferences_${context.packageName}", AppCompatActivity.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (defaultValue) {
            is Boolean -> sharedPreferences.getBoolean(name, defaultValue as Boolean) as T
            is Float -> sharedPreferences.getFloat(name, defaultValue as Float) as T
            is Int -> sharedPreferences.getInt(name, defaultValue as Int) as T
            is Long -> sharedPreferences.getLong(name, defaultValue as Long) as T
            is String -> sharedPreferences.getString(name, defaultValue as String) as T
            else -> throw IllegalArgumentException("This type cannot be saved into SharedPreferences")
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when (value) {
            is Boolean -> editor.putBoolean(name, value)
            is Float -> editor.putFloat(name, value)
            is Int -> editor.putInt(name, value)
            is Long -> editor.putLong(name, value)
            is String -> editor.putString(name, value)
            else -> throw IllegalArgumentException("This type can be saved into SharedPreferences")
        }.apply()
    }
}

 */