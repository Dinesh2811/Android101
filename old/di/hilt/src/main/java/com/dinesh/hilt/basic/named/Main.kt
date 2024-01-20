package com.dinesh.hilt.basic.named

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@AndroidEntryPoint
class Main : AppCompatActivity() {
    @Inject
    @Named("ReturnType1")
    lateinit var returnType : ReturnType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("log_", "onCreate: ${returnType}")
    }

}


data class Model(val data: String)
data class ReturnType(val returnValue: String, val dataValue: String)
@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    @Named("Model1")
    fun providesModel1() : Model {
        return Model("First")
    }
    @Singleton
    @Provides
    @Named("Model2")
    fun providesModel2() : Model {
        return Model("Second")
    }
    @Singleton
    @Provides
    @Named("ReturnType1")
    fun providesReturnType1(@Named("Model1") model : Model): ReturnType {
        return ReturnType("providesReturnType1", model.data)
    }
    @Singleton
    @Provides
    @Named("ReturnType2")
    fun providesReturnType2(@Named("Model2") model : Model): ReturnType {
        return ReturnType("providesReturnType2", model.data)
    }
}



@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("Retrofit1")
    fun providesRetrofit1() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://sandbox.plaid.com")
            .build()
    }


    @Singleton
    @Provides
    @Named("Retrofit2")
    fun providesRetrofit2() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://google.com")
            .build()
    }

    @Singleton
    @Provides
    @Named("ApiService1")
    fun providesApiService1(@Named("Retrofit1") retrofit : Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java) // Use Base_URL_1 to create retrofit instance
    }

    @Singleton
    @Provides
    @Named("ApiService2")
    fun providesApiService2(@Named("Retrofit2") retrofit : Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java) // Use Base_URL_2 to create retrofit instance
    }

}

class Repository @Inject constructor(@Named("ApiService1") private val apiService : ApiService) {

}

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

}

interface ApiService {
    @GET("todos")
    suspend fun fetchTodos(): Response<List<Todo>>
}

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
