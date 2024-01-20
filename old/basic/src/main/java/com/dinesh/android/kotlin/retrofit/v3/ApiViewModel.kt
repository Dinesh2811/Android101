package com.dinesh.android.kotlin.retrofit.v3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.android.kotlin.retrofit.ApiClient
import com.dinesh.android.kotlin.retrofit.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ApiViewModel : ViewModel() {
    private val TAG = "log_" + ApiViewModel::class.java.name.split(ApiViewModel::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

//    private val apiRepository = ApiRepository(ApiClient.getApiInterface())
    private lateinit var apiRepository: ApiRepository
    private val _apiState = MutableLiveData<ApiState>()
    val apiState: LiveData<ApiState> = _apiState

    fun getProductAsObject() {
        viewModelScope.launch {
            apiRepository = withContext(Dispatchers.IO) {
                ApiRepository(ApiClient.getApiInterface())
            }
            _apiState.value = ApiState.Loading
            try {
                val jsonData = withContext(Dispatchers.IO) { apiRepository.getProductAsObject() }
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
            apiRepository = withContext(Dispatchers.IO) {
                ApiRepository(ApiClient.getApiInterface())
            }
            _apiState.value = ApiState.Loading
            try {
                val jsonDataList = withContext(Dispatchers.IO) { apiRepository.getProductAsArray() }
                if (jsonDataList.isNotEmpty()) {
                    val jsonData = jsonDataList[0]
                    Log.e(TAG, "getProductAsArray: ")
                    _apiState.value = ApiState.Success(jsonData)
                }
            } catch (e: Exception) {
                _apiState.value = ApiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}