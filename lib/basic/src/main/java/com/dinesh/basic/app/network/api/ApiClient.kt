package com.dinesh.basic.app.network.api

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dinesh.basic.BuildConfig
import com.dinesh.basic.app.LogLevel
import com.dinesh.basic.app.log
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
    private const val TAG = "ApiClient"
//    private const val BASE_URL = "https://sandbox.plaid.com"
    private const val BASE_URL = "https://dev-kybsis.vercel.app/api/"
//    private const val BASE_URL = "https://dev-kybsis-alpha.vercel.app/api/"
    private const val TIMEOUT_SECONDS = 60L

    @Singleton
    @Provides
    fun providesToken2(@Named("Retrofit2") retrofit : Retrofit) : ApiService2 {
        return retrofit.create(ApiService2::class.java)
    }

    @Singleton
    @Provides
    @Named("Retrofit2")
    fun providesRetrofit2(httpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sandbox.plaid.com/")
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            LogLevel.Debug.log(TAG = "Http_", message = message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY

//            level = if (BuildConfig.DEBUG) {
//                HttpLoggingInterceptor.Level.BODY
//            } else {
//                HttpLoggingInterceptor.Level.NONE
//            }

        }
    }

    @Singleton
    @Provides
    fun providesToken(@Named("Retrofit1") retrofit : Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    @Named("Retrofit1")
    fun providesRetrofit1(httpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
//            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        customLoggingInterceptor: Interceptor,
        chuckerInterceptor : ChuckerInterceptor
    ): OkHttpClient {
        if (BuildConfig.DEBUG) {
            return OkHttpClient.Builder()
                .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(customLoggingInterceptor)
                .addInterceptor(chuckerInterceptor)
                .build()
        } else {
            return OkHttpClient.Builder()
                .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(customLoggingInterceptor)
                .addInterceptor(chuckerInterceptor)
                .build()
        }
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create()
        )
    }

    @Singleton
    @Provides
    fun providesChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(context = context, showNotification = true)
        return ChuckerInterceptor.Builder(context).collector(chuckerCollector).createShortcut(true).build()
    }

    @Singleton
    @Provides
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request: Request = chain.request()
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            val startTime = System.currentTimeMillis()

            // Log the request details
            logRequestDetails(request)

//            val uuid = UUID.randomUUID().toString()
//            requestBuilder.header("correlation-id", uuid)
//            Log.d(TAG, "UUID:: $uuid")
//            Log.d(TAG, "Authorization:: Bearer $accessToken")

            val response = chain.proceed(requestBuilder.build())

            // Log the response details
            logResponseDetails(response, startTime)
            response
        }
    }

    private fun logRequestDetails(request: Request) {
        // Log request details as needed
        Log.d(TAG, "Request URL: ${request.url}")
        Log.d(TAG, "Request Method: ${request.method}")
        Log.d(TAG, "Request Headers: ${request.headers}")

        Log.d(TAG, "Request Headers: ...")
        request.headers.forEach { (name, value) ->
            Log.d(TAG, "$name: $value")
        }

        // Log Authorization header and token
        request.header("Authorization")?.let {
            Log.d(TAG, "Authorization Header in Request: $it")
        }

        // Log Request Body
        request.body?.let { requestBody ->
            Log.d(TAG, "Request Body: ${getRequestBody(requestBody)}")
        }

        // Log other details you need
    }

    private fun logResponseDetails(response: Response, startTime: Long) {
        try {
            val responseBodyString = response.peekBody(Long.MAX_VALUE).string()
            Log.d(TAG, "Response Code: ${response.code}")
            val elapsedTime = System.currentTimeMillis() - startTime
            Log.d(TAG, "Response Headers: ...")
            response.headers.forEach { (name, value) ->
                Log.d(TAG, "$name: $value")
            }

//            // Log Authorization header from the response
//            response.header("Authorization")?.let {
//                Log.d(TAG, "Authorization Header in Response: $it")
//            }

            if (responseBodyString.length > 4000) {
                val chunkSize = 4000
                responseBodyString.chunked(chunkSize).forEach { chunk ->
                    Log.d(TAG, chunk)
                }
            } else {
                Log.d(TAG, responseBodyString)
            }
            Log.d(TAG, "Response Time: $elapsedTime ms")
        } catch (e: Exception) {
            Log.e(TAG, "logResponseDetails: ${e.message}", e)
        }
    }

    private fun getRequestBody(requestBody: RequestBody): String {
        val buffer = Buffer()
        try {
            requestBody.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: Exception) {
            Log.e(TAG, "getRequestBody: Error reading request body -->  ${e.message}", e)
        } finally {
            buffer.close()
        }
        return ""
    }

}

