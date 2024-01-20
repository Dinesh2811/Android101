package com.dinesh.android.local.v0

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.dinesh.android.v0.DataQuery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor {
//            message -> Log.d("log_ApiClient", message)
    }.apply { level = HttpLoggingInterceptor.Level.BODY }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val apollo = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:4000/")
        .okHttpClient(httpClient)
        .build()

    suspend fun getData(): String? {
        return try {
            return apollo.query(DataQuery())
                .execute()
                .data
                ?.hello.toString()
        } catch (e: Exception) {
            Log.e("log_ApiClient", "getData: ${e.message}")
            e.message
        }
    }
}