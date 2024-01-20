package com.dinesh.android.kotlin.retrofit.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Url


interface ApiInterface {
    @GET
    fun getCustomers(@Url url: String): Call<List<Customer>>

    @GET
    fun getCustomerById(@Url url: String): Call<Customer>

    @GET
    fun searchCustomers(
        @Url url: String
//        @Url url: String, @Query("name") name: String?, @Query("mobileNumber") mobileNumber: String?
    ): Call<List<Customer>>

    @GET
    fun getCustomerField(
        @Url url: String
//        @Url url: String, @Path("field") field: String, @Path("value") value: String
    ): Call<Any>

    @GET("api/bearer_authenticated_customers")
    fun getBearerAuthenticatedCustomers(@Header("Authorization") token: String): Call<List<Customer>>

    @Multipart
    @POST("api/post_customers/{id}/products")
    fun addProductToCustomerCart(
        @Path("id") customerId: String,
        @Part("productName") productName: RequestBody,
        @Part("productPrice") productPrice: RequestBody,
        @Part("productCount") productCount: RequestBody,
        @Part productImage: MultipartBody.Part?
    ): Call<Customer>
}