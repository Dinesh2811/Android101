package com.dinesh.android.kotlin.retrofit

sealed class ApiState {
    object Loading : ApiState()
    data class Success(val data: JsonData) : ApiState()
    data class Error(val message: String) : ApiState()
}