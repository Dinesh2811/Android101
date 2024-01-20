package com.dinesh.android.kotlin.retrofit.v0

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dinesh.android.app.ToolbarMain
import com.dinesh.android.kotlin.retrofit.ApiClient
import com.dinesh.android.kotlin.retrofit.ApiInterface
import com.dinesh.android.kotlin.retrofit.JsonData
import com.dinesh.android.kotlin.retrofit.logJsonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

private val TAG = "log_" + ApiMain::class.java.name.split(ApiMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ApiMain : ToolbarMain() {
//    private val apiInterface = ApiClient.getApiInterface()
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            apiInterface = withContext(Dispatchers.IO) {
                ApiClient.getApiInterface()
            }
            fetchData()
        }

    }

    private fun fetchData() {
        productAsObject()
        productAsArray()
    }

    private fun productAsObject() {
        lifecycleScope.launch {
            try {
                val jsonData = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsObject() }
                Log.e(TAG, "productAsObject: ")
                logJsonData(jsonData)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONObject: ${e.message}")
            }
        }
    }

    private fun productAsArray() {
        lifecycleScope.launch {
            try {
                val jsonDataList = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsArray() }
                if (jsonDataList.isNotEmpty()) {
                    val jsonData = jsonDataList[0]
                    Log.e(TAG, "productAsArray: ")
                    logJsonData(jsonData)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONArray: ${e.message}")
            }
        }
    }

}