package com.dinesh.android.kotlin.retrofit.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.android.kotlin.retrofit.ApiClient
import com.dinesh.android.kotlin.retrofit.ApiInterface
import com.dinesh.android.kotlin.retrofit.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = "log_" + ApiViewModel::class.java.name.split(ApiViewModel::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ApiViewModel : ViewModel() {
    //    private val apiInterface: ApiInterface = ApiClient.getApiInterface()
    private lateinit var apiInterface: ApiInterface

    private val _apiState = MutableLiveData<ApiState>()
    val apiState: LiveData<ApiState> = _apiState

    fun getProductAsObject() {
        viewModelScope.launch {
            apiInterface = withContext(Dispatchers.IO) {
                ApiClient.getApiInterface()
            }
            _apiState.value = ApiState.Loading
            try {
                val jsonData = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsObject() }
                Log.e(TAG, "getProductAsObject: ")
                _apiState.value = ApiState.Success(jsonData)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONObject: ${e.message}")
                _apiState.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getProductAsArray() {
        viewModelScope.launch {
            apiInterface = withContext(Dispatchers.IO) {
                ApiClient.getApiInterface()
            }
            _apiState.value = ApiState.Loading
            try {
                val jsonDataList = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsArray() }
                if (jsonDataList.isNotEmpty()) {
                    val jsonData = jsonDataList[0]
                    Log.e(TAG, "getProductAsArray: ")
                    _apiState.value = ApiState.Success(jsonData)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONArray: ${e.message}")
                _apiState.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

/*

class ApiViewModel : ViewModel() {
    private val apiInterface: ApiInterface = ApiClient.getApiInterface()

    private val _apiState = MutableLiveData<ApiState>()
    val apiState: LiveData<ApiState> = _apiState

    init {
        fetchJsonData()
    }

    private fun fetchJsonData() {
        viewModelScope.launch {
            _apiState.value = ApiState.Loading
            try {
                val jsonData = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsObject() }
                _apiState.value = ApiState.Success(jsonData)
            } catch (e: Exception) {
                _apiState.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

 */