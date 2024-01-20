package com.dinesh.android.kotlin.retrofit.v3

import com.dinesh.android.kotlin.retrofit.ApiInterface
import com.dinesh.android.kotlin.retrofit.JsonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ApiRepository(private val apiInterface: ApiInterface) {
    suspend fun getProductAsObject(): JsonData {
        return withContext(Dispatchers.IO) {
            apiInterface.getProductJsonDataAsObject()
        }
    }
    suspend fun getProductAsArray(): List<JsonData> {
        val jsonDataList = withContext(Dispatchers.IO) {
            apiInterface.getProductJsonDataAsArray()
        }
        return jsonDataList
    }

}