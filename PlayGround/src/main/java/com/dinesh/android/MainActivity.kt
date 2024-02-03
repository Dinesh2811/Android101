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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    @Inject lateinit var constants: Constants

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyLayoutView()
                }
            }
        }

        constants.TOKEN = "your_token"
        val token = constants.TOKEN
        Log.i(TAG, "onCreate: token3 --> $token")

        constants.resetPreferences()
        Log.e(TAG, "onCreate: token4 --> ${constants.TOKEN}")



//        startActivity(Intent(this, Main::class.java))

    }
}

@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    Text("Hello there")
}