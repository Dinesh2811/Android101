package com.dinesh.android.kotlin.retrofit.v2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dinesh.android.app.ToolbarMain
import com.dinesh.android.kotlin.retrofit.ApiClient
import com.dinesh.android.kotlin.retrofit.ApiState
import com.dinesh.android.kotlin.retrofit.logJsonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = "log_" + ApiMain::class.java.name.split(ApiMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ApiMain : ToolbarMain() {
//    private val apiRepository = ApiRepository(ApiClient.getApiInterface())
    private lateinit var apiRepository: ApiRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            apiRepository = withContext(Dispatchers.IO) {
                ApiRepository(ApiClient.getApiInterface())
            }
            fetchData()
            withContext(Dispatchers.Main) {
                apiRepository.apiState.observe(this@ApiMain) { state ->
                    when (state) {
                        is ApiState.Loading -> {
                            Log.i(TAG, "onCreate: Loading JSON data")
                        }

                        is ApiState.Success -> {
//                    Log.d(TAG, "onCreate: ${state.data}")
                            logJsonData(state.data)
                        }

                        is ApiState.Error -> {
                            Log.e(TAG, "onCreate: ${state.message}")
                        }
                    }
                }
            }
        }


    }

    private fun fetchData() {
//        productAsObject()
        productAsArray()
    }

    private fun productAsObject() {
        lifecycleScope.launch {
            Log.e(TAG, "productAsObject: ")
            apiRepository.productAsObject()
        }
    }

    private fun productAsArray() {
        lifecycleScope.launch {
            Log.e(TAG, "productAsArray: ")
            apiRepository.productAsArray()
        }
    }
}