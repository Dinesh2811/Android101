package com.dinesh.android.app.user_interface.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class Button2Receiver : BroadcastReceiver() {
    private val TAG = "log_" + Button2Receiver::class.java.name.split(Button2Receiver::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Button 2 clicked!", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onReceive: Button 2 clicked!")
    }
}