package com.dinesh.android.local.v1

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dinesh.android.ui.theme.Material3Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    val person = Person(
        name = "katherine",
        age = 25,
        address = Address(
            street = "123 street",
            city = "New Orleans",
            country = "US"
        )
    )

    private val apolloClient = ApiClient()
    private suspend fun logData() {
        apolloClient.apply {
//            postData(person)

//            Log.i(TAG, "getGreeting: ${getGreeting()?.hello}")
//            Log.i(TAG, "getNumbers: ${getNumbers()?.numbers}")
            Log.i(TAG, "getPersons: ${getPersons()?.persons}")
//            Log.i(TAG, "getPerson: ${getPerson("John")}")
//            Log.i(TAG, "getPersonsByNameAge: ${getPersonsByNameAge("John", 30)}")
//            Log.i(TAG, "getPersonsByName: ${getPersonsByName("John")}")
//            Log.i(TAG, "getPersonsByAge: ${getPersonsByAge(30)}")
//            Log.i(TAG, "deletePersonsByName: ${deletePersonsByName(listOf("John"))}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.e(TAG, "getData: ${apolloClient.getData()}")
                logData()
            }
        }

        setContent {
            Material3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            lifecycleScope.launch(Dispatchers.IO) {
                                repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    Log.e(TAG, "numbers --> ${apolloClient.getData()?.numbers?.joinToString { it.toString() }}")
                                    logData()
                                }
                            }
                        }) {
                            Text(text = "GetData")
                        }
                    }

                }
            }
        }

    }
}