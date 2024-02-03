package com.dinesh.basic.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.basic.MyApp
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
//
//@SuppressLint("StaticFieldLeak")
//object Constants {
//
//    lateinit var context: Context
//        private set
//    fun init(context: Context) {
//        this.context = context
//    }
//
//    var TOKEN: String? by SharedPreferenceDelegate("TOKEN", "")
//    var ID: Int? by SharedPreferenceDelegate("ID", 0)
//    var USER_NAME: String? by SharedPreferenceDelegate("USER", "")
//    var USER_EMAIL: String? by SharedPreferenceDelegate("USER_EMAIL", "")
//    var USER_IS_ACTIVE: Boolean by SharedPreferenceDelegate("USER_IS_ACTIVE", false)
//}
//
//class SharedPreferenceDelegate<T>(private val key: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {
//
////    private val sharedPreferences: SharedPreferences? = MyApp.context?.getSharedPreferences("MySharedPreferences_${MyApp.context?.packageName}", AppCompatActivity.MODE_PRIVATE)
//    private val sharedPreferences: SharedPreferences? = MyApp.context?.getSharedPreferences("MySharedPreferences_${MyApp.context?.packageName}", AppCompatActivity.MODE_PRIVATE)
//
//    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
//        if (sharedPreferences != null) {
//            @Suppress("UNCHECKED_CAST")
//            return when (defaultValue) {
//                is Boolean -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
//                is Float -> sharedPreferences.getFloat(key, defaultValue as Float) as T
//                is Int -> sharedPreferences.getInt(key, defaultValue as Int) as T
//                is Long -> sharedPreferences.getLong(key, defaultValue as Long) as T
//                is String -> sharedPreferences.getString(key, defaultValue as String) as T
//                else -> throw IllegalArgumentException("This type cannot be accessed by sharedPreferences")
//            }
//        } else {
//            throw Exception("sharedPreferences instance is null")
//        }
//    }
//
//    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
//        if (sharedPreferences != null) {
//            with(sharedPreferences.edit()) {
//                when (value) {
//                    is Boolean -> putBoolean(key, value)
//                    is Float -> putFloat(key, value)
//                    is Int -> putInt(key, value)
//                    is Long -> putLong(key, value)
//                    is String -> putString(key, value)
//                    else -> throw IllegalArgumentException("This type cannot be saved into SharedPreferences")
//                }
//                apply()
//            }
//        } else {
//            throw Exception("sharedPreferences instance is null")
//        }
//    }
//}
//
//fun Context.resetPreferences() {
//    val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPreferences_${packageName}", AppCompatActivity.MODE_PRIVATE)
//    sharedPreferences.edit().clear().apply()
//}
//



object Constants {
    private var sharedPreferences: SharedPreferences? = null
    fun init(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    var TOKEN: String? by SharedPreferenceDelegate(sharedPreferences, "TOKEN", "")
    var ID: Int? by SharedPreferenceDelegate(sharedPreferences, "ID", 0)
    var user: String? by SharedPreferenceDelegate(sharedPreferences, "USER", "")
    var USER_EMAIL: String? by SharedPreferenceDelegate(sharedPreferences, "USER_EMAIL", "")
    var USER_IS_ACTIVE: Boolean by SharedPreferenceDelegate(sharedPreferences, "USER_IS_ACTIVE", false)
}

class SharedPreferenceDelegate<T>(private val sharedPreferences: SharedPreferences?, private val key: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (sharedPreferences != null) {
            @Suppress("UNCHECKED_CAST")
            return when (defaultValue) {
                is Boolean -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
                is Float -> sharedPreferences.getFloat(key, defaultValue as Float) as T
                is Int -> sharedPreferences.getInt(key, defaultValue as Int) as T
                is Long -> sharedPreferences.getLong(key, defaultValue as Long) as T
                is String -> sharedPreferences.getString(key, defaultValue as String) as T
                else -> throw IllegalArgumentException("This type cannot be accessed by sharedPreferences")
            }
        } else {
//            throw Exception("sharedPreferences instance is null")
            return "" as T
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (sharedPreferences != null) {
            with(sharedPreferences.edit()) {
                when (value) {
                    is Boolean -> putBoolean(key, value)
                    is Float -> putFloat(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is String -> putString(key, value)
                    else -> throw IllegalArgumentException("This type cannot be saved into SharedPreferences")
                }
                apply()
            }
        } else {
//            throw Exception("sharedPreferences instance is null")
        }
    }
}

fun Context.resetPreferences() {
    val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPreferences_${packageName}", AppCompatActivity.MODE_PRIVATE)
    sharedPreferences.edit().clear().apply()
}






/*

class SharedPreferenceDelegate(context: Context, private val name: String, private val defaultValue: String = "") : ReadWriteProperty<Any?, String> {

// * # SharedPreferenceDelegate
// *
// *  *Don't*
// *
// *     private fun usualMethod() {
// *         //  Global declaration
// *         val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
// *         val editor: SharedPreferences.Editor = sharedPreferences.edit()
// *
// *         //  Save the data using shared preferences
// *         editor.putString("Theme", "Theme.Material3")
// *         editor.apply() //Important
// *
// *         //  Get the shared preferences data
// *         val currentTheme = sharedPreferences.getString("Theme", "Theme.Material3")
// *         Log.e(TAG, "Get the shared preferences data: ----> $currentTheme")
// *     }
// *
// *  *Do*
// *
// *         //  Save the data using shared preferences
// *         var theme: String by sharedPreferences("Theme")
// *         theme = "Theme.Material3.Dark"
// *
// *         //  Get the data using shared preferences
// *         Log.i(TAG, "onCreate: $theme")

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


*/




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
