package com.dinesh.android.app

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.dinesh.android.MainActivity
import java.util.*

private val TAG = "log_" + Root::class.java.name.split(Root::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

open class Root : AppCompatActivity() {
}

var saveLastClassNameList: LinkedList<Class<*>> = LinkedList()

/**
 * Saves the current activity class to the [saveLastClassNameList] if it's not already saved.
 * @param launchActivity, Launches the specified activity class and finishes the current activity.
 */
fun Context.launchActivity(launchActivity: Class<*>) {
    // Check if the current activity class is already saved to prevent duplicates
    if (!saveLastClassNameList.contains(this::class.java)) {
        saveLastClassNameList.addFirst(this::class.java)
    }
    // Convert the context to an AppCompatActivity to call finish()
    val activity = this as AppCompatActivity
    this.startActivity(Intent(this, launchActivity))
    activity.finish()
}

var backButtonPressedTime = 0L

/**
 * Handles back navigation and exit behavior for an activity.
 * If the activity stack is not empty -> it navigates back to the last activity.
 * If the activity stack is empty -> If the back button is pressed twice within two seconds, the app is exited.
 * @param defaultClassName the default class to navigate back to if the activity stack is empty.
 */
fun AppCompatActivity.onBackNavigationAction(defaultClassName: Class<*> = MainActivity::class.java) {
    val currentTime = System.currentTimeMillis()
    var lastClassName: Class<*>? = null
    if (saveLastClassNameList.isNotEmpty()) {
        lastClassName = saveLastClassNameList.poll()
    }

    if (lastClassName != null && this::class.java != lastClassName && defaultClassName != lastClassName) {
        // Navigate back to the last activity
        startActivity(Intent(this, lastClassName))
        finish()
    } else {
        // Exit the app if the back button is pressed twice within two seconds
        if (backButtonPressedTime + 2000 > currentTime) {
            finishAffinity()
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "onBackPressed: Press back again to exit")
        }
        backButtonPressedTime = currentTime
    }
}

//fun <T : View> AppCompatActivity.findView(id: Int): T {
//    return findViewById<T>(id)
//}