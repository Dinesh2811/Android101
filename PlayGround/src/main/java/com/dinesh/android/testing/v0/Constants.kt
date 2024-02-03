//package com.dinesh.android.testing.v0
//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.appcompat.app.AppCompatActivity
//import kotlin.properties.ReadWriteProperty
//import kotlin.reflect.KProperty
//
//object Constants {
//    private var sharedPreferences: SharedPreferences? = null
//    fun init(sharedPreferences: SharedPreferences) {
//        this.sharedPreferences = sharedPreferences
//    }
//
//    var TOKEN: String? by SharedPreferenceDelegate(sharedPreferences, "TOKEN", "")
//    var ID: Int? by SharedPreferenceDelegate(sharedPreferences, "ID", 0)
//    var user: String? by SharedPreferenceDelegate(sharedPreferences, "USER", "")
//    var USER_EMAIL: String? by SharedPreferenceDelegate(sharedPreferences, "USER_EMAIL", "")
//    var USER_IS_ACTIVE: Boolean by SharedPreferenceDelegate(sharedPreferences, "USER_IS_ACTIVE", false)
//}
//
//class SharedPreferenceDelegate<T>(private val sharedPreferences: SharedPreferences?, private val key: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {
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
////            throw Exception("sharedPreferences instance is null")
//            return "" as T
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
////            throw Exception("sharedPreferences instance is null")
//        }
//    }
//}
//
//fun Context.resetPreferences() {
//    val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPreferences_${packageName}", AppCompatActivity.MODE_PRIVATE)
//    sharedPreferences.edit().clear().apply()
//}
//
//
//
//
///*
//
//        val token by sharedPreferences(PreferenceKey.Token, "token")
//        val id by sharedPreferences(PreferenceKey.Id, 28)
//        val user by sharedPreferences(PreferenceKey.User, "user_28")
//        val userEmail by sharedPreferences(PreferenceKey.UserEmail, "dl@gmail.com")
//        val userIsActive by sharedPreferences(PreferenceKey.UserIsActive, true)
//
// */