package com.dinesh.android.kotlin.retrofit.api


data class Customer(
    val customerId: String,
    val customer: CustomerDetails
)

data class CustomerDetails(
    val details: CustomerInfo,
    val products: List<Product>
)

data class CustomerInfo(
    val name: String,
    val mobileNumber: String
)

data class Product(
    val productName: String,
    val productPrice: String,
    val productCount: Int,
    val productImage: String?
)
