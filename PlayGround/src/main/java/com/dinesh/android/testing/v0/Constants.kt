//package com.dinesh.android.testing.v0
//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.appcompat.app.AppCompatActivity
//import kotlin.properties.ReadWriteProperty
//import kotlin.reflect.KProperty
//
//object Constants {
//    var TOKEN: String? = null
//    var ID: Int? = null
//    var USER_NAME: String? = null
//    var USER_EMAIL: String? = null
//    var USER_IS_ACTIVE: Boolean = false
//}
//
//sealed class PreferenceKey<T>(val key: String) {
//    data object Token : PreferenceKey<String>("TOKEN")
//    data object Id : PreferenceKey<Int>("ID")
//    data object User : PreferenceKey<String>("USER")
//    data object UserEmail : PreferenceKey<String>("USER_EMAIL")
//    data object UserIsActive : PreferenceKey<Boolean>("USER_IS_ACTIVE")
//}
//
//class SharedPreferenceDelegate<T>(context: Context, private val key: PreferenceKey<T>, private val defaultValue: T) : ReadWriteProperty<Any?, T> {
//    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPreferences_${context.packageName}", AppCompatActivity.MODE_PRIVATE)
//    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
//        return when (defaultValue) {
//            is Boolean -> sharedPreferences.getBoolean(key.key, defaultValue as Boolean) as T
//            is Float -> sharedPreferences.getFloat(key.key, defaultValue as Float) as T
//            is Int -> sharedPreferences.getInt(key.key, defaultValue as Int) as T
//            is Long -> sharedPreferences.getLong(key.key, defaultValue as Long) as T
//            is String -> sharedPreferences.getString(key.key, defaultValue as String) as T
//            else -> throw IllegalArgumentException("This type cannot be saved into SharedPreferences")
//        }
//    }
//
//    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
//        when (value) {
//            is Boolean -> editor.putBoolean(key.key, value)
//            is Float -> editor.putFloat(key.key, value)
//            is Int -> editor.putInt(key.key, value)
//            is Long -> editor.putLong(key.key, value)
//            is String -> editor.putString(key.key, value)
//            else -> throw IllegalArgumentException("This type can be saved into SharedPreferences")
//        }.apply()
//    }
//}
//
//fun <T> Context.sharedPreferences(key: PreferenceKey<T>, defaultValue: T) = SharedPreferenceDelegate(this, key, defaultValue)
//
//fun Context.resetPreferences() {
//    val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPreferences_${packageName}", AppCompatActivity.MODE_PRIVATE)
//    sharedPreferences.edit().clear().apply()
//}
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