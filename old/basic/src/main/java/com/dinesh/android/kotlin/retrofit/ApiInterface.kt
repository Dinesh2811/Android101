package com.dinesh.android.kotlin.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("productsTestJsonObject")
    suspend fun getProductJsonDataAsObject(): JsonData

    @GET("productsTestJsonArray")
    suspend fun getProductJsonDataAsArray(): List<JsonData>

//    @POST("productsTestJsonObject")
    @POST("productsTestJsonArray")
    suspend fun postJsonData(@Body jsonData: JsonData): Response<ResponseBody>

}