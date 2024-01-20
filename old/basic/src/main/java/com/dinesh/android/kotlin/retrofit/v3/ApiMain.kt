package com.dinesh.android.kotlin.retrofit.v3

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.dinesh.android.app.ToolbarMain
import com.dinesh.android.kotlin.retrofit.ApiState
import com.dinesh.android.kotlin.retrofit.logJsonData

private val TAG = "log_" + ApiMain::class.java.name.split(ApiMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ApiMain : ToolbarMain() {
    private lateinit var apiViewModel: ApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiViewModel = ViewModelProvider(this)[ApiViewModel::class.java]

        apiViewModel.apiState.observe(this) { state ->
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
                    Log.e(TAG, "onCreate: ${state.message}")
                }
            }
        }

        apiViewModel.getProductAsObject()
        apiViewModel.getProductAsArray()

    }
}
