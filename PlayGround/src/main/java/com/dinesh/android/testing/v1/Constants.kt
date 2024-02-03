//package com.dinesh.android.testing.v1
//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.appcompat.app.AppCompatActivity
//import com.dinesh.android.MyApp
//import kotlin.properties.ReadWriteProperty
//import kotlin.reflect.KProperty
//
//object Constants {
//    var TOKEN: String? by SharedPreferenceDelegate("TOKEN", "")
//    var ID: Int? by SharedPreferenceDelegate("ID", 0)
//    var USER_NAME: String? by SharedPreferenceDelegate("USER", "")
//    var USER_EMAIL: String? by SharedPreferenceDelegate("USER_EMAIL", "")
//    var USER_IS_ACTIVE: Boolean by SharedPreferenceDelegate("USER_IS_ACTIVE", false)
//}
//
//class SharedPreferenceDelegate<T>(private val key: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {
//
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
