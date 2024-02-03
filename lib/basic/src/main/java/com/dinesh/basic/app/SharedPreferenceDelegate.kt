package com.dinesh.basic.app

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private val TAG = "log_" + SharedPreferenceDelegate::class.java.name.split(SharedPreferenceDelegate::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

/**
 * # SharedPreferenceDelegate
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
 *     @Inject lateinit var constants: Constants
 *
 *         //  Save the data using shared preferences
 *         constants.TOKEN = "your_token"
 *
 *         //  Get the data using shared preferences
 *         Log.e(TAG, "onCreate: token --> ${constants.TOKEN}")
 *
 *         //  Erase the shared preferences data
 *         constants.resetPreferences()
 *
 */

@Singleton
class Constants @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var TOKEN: String? by SharedPreferenceDelegate(sharedPreferences, "TOKEN", "")
    var ID: Int? by SharedPreferenceDelegate(sharedPreferences, "ID", 0)
    var user: String? by SharedPreferenceDelegate(sharedPreferences, "USER", "")
    var USER_EMAIL: String? by SharedPreferenceDelegate(sharedPreferences, "USER_EMAIL", "")
    var USER_IS_ACTIVE: Boolean by SharedPreferenceDelegate(sharedPreferences, "USER_IS_ACTIVE", false)

    fun resetPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}

class SharedPreferenceDelegate<T>(private val sharedPreferences: SharedPreferences, private val key: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return when (defaultValue) {
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue as Float) as T
            is Int -> sharedPreferences.getInt(key, defaultValue as Int) as T
            is Long -> sharedPreferences.getLong(key, defaultValue as Long) as T
            is String -> sharedPreferences.getString(key, defaultValue as String) as T
            else -> throw IllegalArgumentException("This type cannot be accessed by sharedPreferences")
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
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
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MySharedPreferences_${context.packageName}", AppCompatActivity.MODE_PRIVATE)
    }
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