package com.dinesh.android.kotlin.retrofit.v0_hilt


/*

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.dinesh.android.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userAPI: UserAPI

    val myResponse: MutableLiveData<User> = MutableLiveData()
    val myResponseList: MutableLiveData<List<User>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("log_", "onCreate:")

        getPost()
//        myResponse.observe(this) {
//            Log.i("log_", "onCreate: myResponse --> ${it}")
//        }
    }


    fun getPost() {
        lifecycleScope.launch {
            myResponse.value = userAPI.getPost()
        }
    }

    fun getPosts() {
        lifecycleScope.launch {
            myResponseList.value = userAPI.getPosts()
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ApiClient_FullHilt {
    private const val TAG = "log_ApiClient"
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"    //  "http://10.0.2.2/"
    private const val TIMEOUT_SECONDS = 60L
    private const val RETRY_COUNT = 3

//    @Singleton
//    @Provides
//    fun providesApiService(@Named("Retrofit1") retrofit : Retrofit) : ApiService {
//        return retrofit.create(ApiService::class.java)
//    }

    @Singleton
    @Provides
    fun providesToken(@Named("Retrofit1") retrofit : Retrofit) : UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    @Named("Retrofit1")
    fun providesRetrofit1(httpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        customLoggingInterceptor: Interceptor,
        sslContext: SSLContext,
        trustAllCerts: Array<TrustManager>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(customLoggingInterceptor)
//            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
//            .hostnameVerifier { _, _ -> true }
//            .addInterceptor(ApiRetryInterceptor(RETRY_COUNT))
            .build()
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
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.i("log_info", message)
        }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
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

            // Log Authorization header from the response
            response.header("Authorization")?.let {
                Log.d(TAG, "Authorization Header in Response: $it")
            }

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

    @Singleton
    @Provides
    fun providesTrustAllCerts(): Array<TrustManager> = arrayOf(object: X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }
    })

    @Singleton
    @Provides
    fun providesSslContext(trustAllCerts: Array<TrustManager>): SSLContext = SSLContext.getInstance("TLS").apply {
        init(null, trustAllCerts, SecureRandom())
    }

}


data class User(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)

interface UserAPI {

    @GET("posts/1")
    suspend fun getPost(): User

    @GET("posts")
    suspend fun getPosts(): List<User>
}

*/
