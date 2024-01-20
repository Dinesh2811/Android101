package com.dinesh.android.kotlin.retrofit.v1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.dinesh.android.app.ToolbarMain
import com.dinesh.android.kotlin.retrofit.ApiClient
import com.dinesh.android.kotlin.retrofit.ApiInterface
import com.dinesh.android.kotlin.retrofit.ApiState
import com.dinesh.android.kotlin.retrofit.logJsonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = "log_" + ApiMain::class.java.name.split(ApiMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ApiMain : ToolbarMain() {
//    private val apiInterface = ApiClient.getApiInterface()
    private lateinit var apiInterface: ApiInterface
    private val _apiState = MutableLiveData<ApiState>()
    private val apiState: LiveData<ApiState> = _apiState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Observe apiState LiveData
        apiState.observe(this) { state ->
            when (state) {
                is ApiState.Loading -> {
                    // Show loading state
                    Log.i(TAG, "onCreate: Loading JSON data")
                }

                is ApiState.Success -> {
                    // Hide loading state and display data
//                    Log.d(TAG, "onCreate: ${state.data}")
                    logJsonData(state.data)
                }

                is ApiState.Error -> {
                    // Hide loading state and display error message
                    Log.e(TAG, "Error getting JSON data: ${state.message}")
                }
            }
        }

        lifecycleScope.launch {
            apiInterface = withContext(Dispatchers.IO) {
                ApiClient.getApiInterface()
            }
            fetchData()
        }
    }

    private fun fetchData() {
//        productAsObject()
        productAsArray()
    }

    private fun productAsObject() {
        lifecycleScope.launch {
            _apiState.value = ApiState.Loading
            try {
                val jsonData = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsObject() }
                Log.e(TAG, "productAsObject: ")
                _apiState.value = ApiState.Success(jsonData)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONObject: ${e.message}")
                _apiState.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun productAsArray() {
        lifecycleScope.launch {
            _apiState.value = ApiState.Loading
            try {
                val jsonDataList = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsArray() }
                if (jsonDataList.isNotEmpty()) {
                    val jsonData = jsonDataList[0]
                    Log.e(TAG, "productAsArray: ")
                    _apiState.value = ApiState.Success(jsonData)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONArray: ${e.message}")
                _apiState.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}