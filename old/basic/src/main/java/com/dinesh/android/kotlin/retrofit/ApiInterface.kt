package com.dinesh.android.kotlin.retrofit

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @GET("productsTestJsonObject")
    suspend fun getProductJsonDataAsObject(): JsonData

    @GET("productsTestJsonArray")
    suspend fun getProductJsonDataAsArray(): List<JsonData>

//    @POST("productsTestJsonObject")
    @POST("productsTestJsonArray")
    suspend fun postJsonData(@Body jsonData: JsonData): Response<ResponseBody>

//    @DELETE("dashboard/remove_widget")
//    suspend fun removeWidget(@Header("Authorization") token: String, @Body widgetRequest: WidgetRequest): Response<RemoveWidgetResponse>

    @HTTP(method = "DELETE", path = "dashboard/remove_widget", hasBody = true)
    suspend fun removeWidget(@Header("Authorization") token: String, @Body widgetRequest: WidgetRequest): Response<ResponseBody>

}


@Keep
data class WidgetRequest(
    @SerializedName("widgetId")
    val widgetId: Int
)