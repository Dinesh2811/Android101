package com.dinesh.android.kotlin.database.room.v0

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userViewModel!!.allUsers?.observe(this) {
            Log.d(TAG, "onCreate: $it")
        }

        userViewModel!!.insert(User("userName 1", "password123"))
    }

    override fun onDestroy() {
        super.onDestroy()
        userViewModel!!.shutdownExecutor()
    }
}