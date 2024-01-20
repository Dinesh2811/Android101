package com.dinesh.android.kotlin.retrofit.authorization.bearer

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiService {
    @Multipart
    @POST("api/v1/orders")
    suspend fun createOrder(
        @Header("Accept") accept: String,
        @Header("Authorization") authorization: String,
        @Part("customer_id") customerId: RequestBody,
        @Part("order_details[0][name]") name: RequestBody,
        @Part("order_details[0][specification]") specification: RequestBody,
        @Part("order_details[0][price]") price: RequestBody,
        @Part("order_details[0][quantity]") quantity: RequestBody,
        @Part("order_details[0][imageurl]") imageurl: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<CreateOrderResponse>
}
