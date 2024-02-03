package com.dinesh.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dinesh.basic.app.Constants

class MainActivity: ComponentActivity() {
    private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyLayoutView()
                }
            }
        }

//        var user by sharedPreferences(PreferenceKey.User, "")
////        user = "user_35"
//
//        Log.i(TAG, "onCreate: user --> $user")

        Constants.TOKEN = "your_token"
        val token = Constants.TOKEN
        Log.i(TAG, "onCreate: token3 --> $token")

//        resetPreferences()
        Log.e(TAG, "onCreate: token4 --> ${Constants.TOKEN}")



//        startActivity(Intent(this, Main::class.java))

    }
}

@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    Text("Hello there")
}