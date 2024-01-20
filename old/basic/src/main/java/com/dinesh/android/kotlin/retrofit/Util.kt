package com.dinesh.android.kotlin.retrofit

import android.util.Log

private val TAG = "log_ApiMain"

fun logJsonData(data: JsonData) {
    Log.d(TAG, "Total count: ${data.totalCount}")
    data.data.forEach { categoryData ->
        categoryData.category.forEach { category ->
            category.subCategories.forEach { subCategory ->
                subCategory.products.forEach { product ->
                    Log.d(TAG, "Product ID: ${product.id}")
                }
            }
        }
    }
}