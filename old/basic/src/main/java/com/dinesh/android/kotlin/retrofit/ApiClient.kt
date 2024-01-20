package com.dinesh.android.kotlin.retrofit

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dinesh.android.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiClient {
    private const val TAG = "ApiClient"
    //    private const val BASE_URL = "http://10.0.2.2/"   http://192.168.1.5:3000/api/customers
    private const val TIMEOUT_SECONDS = 60L
    private const val RETRY_COUNT = 3
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
//        Log.d("log_info", message)
    }.apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val customLoggingInterceptor = Interceptor { chain ->
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        val startTime = System.currentTimeMillis()

        // Log the request details
        logRequestDetails(request)

//        val uuid = UUID.randomUUID().toString()
//        requestBuilder.header("correlation-id", uuid)
//        Log.d(TAG, "UUID:: $uuid")
//        Log.d(TAG, "Authorization:: Bearer $accessToken")

        val response = chain.proceed(requestBuilder.build())

        // Log the response details
        logResponseDetails(response, startTime)
        response
    }

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }
    })

    private val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    private val httpClient = OkHttpClient.Builder()
        .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(customLoggingInterceptor)
//        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
//        .hostnameVerifier { _, _ -> true }
        .addInterceptor(ApiRetryInterceptor(RETRY_COUNT))
//        .addInterceptor(chuckerInterceptor)
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    private val gsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder()
//            .setLenient()
//            .setPrettyPrinting()
            .create()
    )

//    @Singleton
//    @Provides
//    fun providesChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
//        val chuckerCollector = ChuckerCollector(context = context, showNotification = true)
//        return ChuckerInterceptor.Builder(context).collector(chuckerCollector).createShortcut(true).build()
//    }

//    fun getApiInterface(baseUrl: String = "http://10.0.2.2/"): ApiInterface {
//        return createRetrofit(baseUrl).create(ApiInterface::class.java)
//    }

    fun <T> getApiInterface(apiInterface: Class<T>, baseUrl: String = "http://10.0.2.2/"): T {
        return createRetrofit(baseUrl).create(apiInterface)
    }

    inline fun <reified T> getApiInterface(baseUrl: String = "http://10.0.2.2/"): T {
        return getApiInterface(T::class.java, baseUrl)
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

//        // Log Authorization header from the response
//        response.header("Authorization")?.let {
//            Log.d(TAG, "Authorization Header in Response: $it")
//        }

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


class ApiRetryInterceptor(private val maxRetries: Int) : Interceptor {
    private val TAG = "log_" + ApiRetryInterceptor::class.java.name.split(ApiRetryInterceptor::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        var response: Response? = null
        for (retryCount in 0..maxRetries) {
            response = chain.proceed(request)
            if (response.isSuccessful || response.code != 404) {
                break
            } else {
                // If the response code is 404, it means the search didn't match,
                // so we should not retry the request
                break
            }
        }
        if (response == null) {
            Log.e(TAG, "No response received")
            return createErrorResponse() // Create a custom error response or return an appropriate response object
        }
        return response
    }
    private fun createErrorResponse(): Response {
        val errorBody = "{\"error\":\"No response received\"}".toResponseBody("application/json".toMediaType())
        return Response.Builder()
            .code(500) // Use an appropriate error code
            .message("Internal Server Error")
            .body(errorBody)
            .protocol(Protocol.HTTP_1_1)
//            .request(Request.Builder().url("http://dummyurl.com").build())
            .build()
    }
}


/*


object ApiClient {
    private const val TAG = "ApiClient"
    private const val TIMEOUT_SECONDS = 60L
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val customLoggingInterceptor = Interceptor { chain ->
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        val startTime = System.currentTimeMillis()

        // Log the request details
        logRequestDetails(request)

//        val uuid = UUID.randomUUID().toString()
//        requestBuilder.header("correlation-id", uuid)
//        Log.d(TAG, "UUID:: $uuid")
//        Log.d(TAG, "Authorization:: Bearer $accessToken")

        val response = chain.proceed(requestBuilder.build())

        // Log the response details
        logResponseDetails(response, startTime)
        response
    }

    private val httpClient = OkHttpClient.Builder()
        .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(customLoggingInterceptor)
        .build()

    private fun gsonBuilder(): Gson = GsonBuilder()
//        .setLenient()
//        .setPrettyPrinting()
        .create()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder()))
            .build()
    }

    fun <T> getApiInterface(apiInterface: Class<T>, baseUrl: String = "http://10.0.2.2/"): T {
        return createRetrofit(baseUrl).create(apiInterface)
    }

    inline fun <reified T> getApiInterface(baseUrl: String = "http://10.0.2.2/"): T {
        return getApiInterface(T::class.java, baseUrl)
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

//        // Log Authorization header from the response
//        response.header("Authorization")?.let {
//            Log.d(TAG, "Authorization Header in Response: $it")
//        }

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


 */

//object ApiClient {
//    private const val BASE_URL = "http://10.0.2.2/"
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    fun getApiInterface(): ApiInterface {
//        return retrofit.create(ApiInterface::class.java)
//    }
//}

/*

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiInterface(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}

 */