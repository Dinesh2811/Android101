package com.dinesh.android.kotlin.retrofit.authorization.bearer

data class CreateOrderResponse(
    val status: Boolean,
    val message: String,
    val responseCode: Int,
    val data: String
)