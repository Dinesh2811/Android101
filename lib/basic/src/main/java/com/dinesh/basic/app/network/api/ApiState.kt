package com.dinesh.basic.app.network.api

import retrofit2.Response


sealed class ApiState<out T> {
    data class Loading(val isLoading: Boolean = true) : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error<T>(val message: String, val data: T?) : ApiState<T>()
    data class Exception<T>(val exception: Throwable, val data: T?) : ApiState<T>()
}


fun <T> Response<T>.relativeUrl(): String {
    return try {
        val pathSegments = raw().request.url.pathSegments.takeLast(2)
        pathSegments.joinToString("/")
    } catch (e: Exception) {
        raw().request.url.toString()
    }
}


// TODO: ApiState -->  data class Success<T>(val data: T) : ApiState<T>() --> Add new argument