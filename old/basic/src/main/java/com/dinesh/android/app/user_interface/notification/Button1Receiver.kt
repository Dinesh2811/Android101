package com.dinesh.android.app.user_interface.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class Button1Receiver : BroadcastReceiver() {
    private val TAG = "log_" + Button1Receiver::class.java.name.split(Button1Receiver::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Button 1 clicked!", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onReceive: Button 1 clicked!")
    }
}