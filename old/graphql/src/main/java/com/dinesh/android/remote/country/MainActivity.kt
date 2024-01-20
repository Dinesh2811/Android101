package com.dinesh.android.remote.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.compose.ui.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dinesh.android.ui.theme.Material3Theme
import com.dinesh.android.remote.country.data.ApolloCountryClient
import com.dinesh.android.remote.country.model.SimpleCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class MainActivity : AppCompatActivity() {
    private val apolloClient = ApolloCountryClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var countries = listOf<SimpleCountry>()

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                countries = apolloClient.getCountries()
                Log.e(TAG, "onCreate: $countries")
            }
        }

        setContent {
            Material3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val randomCountry = apolloClient.getCountry(countries.random().code)
                                Log.i(TAG, "onCreate: $randomCountry")
                            }
                        }) {
                            Text(text = "Get Country")
                        }
                    }

                }
            }
        }

    }
}