package com.dinesh.basic.app.network.api

import android.util.Log
import org.json.JSONException
import org.json.JSONObject


interface ApiStateCallback<T> {
    suspend fun onApiStateChanged(state: ApiState<T?>)
}


inline fun <reified T> handleError(statusCode: Int?, errorBody: String?): ApiState<T> {
    val errorMessage = extractMessageFromJson(errorBody) ?: "Unknown error"
    Log.e("log_handleError", "Message: $errorMessage, StatusCode: $statusCode")
    return ApiState.Error(message = errorMessage, data = null)
}

fun extractMessageFromJson(json: String?): String? {
    return try {
        JSONObject(json.orEmpty()).optString("message")
    } catch (e: JSONException) {
        null
    }
}
