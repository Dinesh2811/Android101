package com.dinesh.android.kotlin.retrofit.v2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dinesh.android.kotlin.retrofit.ApiInterface
import com.dinesh.android.kotlin.retrofit.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiRepository(private val apiInterface: ApiInterface) {
    private val _apiState = MutableLiveData<ApiState>()
    val apiState: LiveData<ApiState> = _apiState

    suspend fun productAsObject() {
        _apiState.value = ApiState.Loading
        try {
            val jsonData = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsObject() }
            _apiState.value = ApiState.Success(jsonData)
        } catch (e: Exception) {
            _apiState.value = ApiState.Error(e.message?: "Unknown error")
        }
    }

    suspend fun productAsArray() {
        _apiState.value = ApiState.Loading
        try {
            val jsonDataList = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsArray() }
            if (jsonDataList.isNotEmpty()) {
                val jsonData = jsonDataList[0]
                _apiState.value = ApiState.Success(jsonData)
            }
        } catch (e: Exception) {
            _apiState.value = ApiState.Error(e.message?: "Unknown error")
        }
    }
}