package com.dinesh.hilt.v2

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

@AndroidEntryPoint
class Main : AppCompatActivity(), ApiStateCallback<List<Todo>> {
    private val apiViewModel: ApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val apiViewModel = hiltViewModel<ApiViewModel>()
        }
        apiViewModel.getTodos()
        lifecycleScope.launch(Dispatchers.Main) {
            apiViewModel.getTodosByPosition()
        }
    }

    override fun onApiStateChanged(state: ApiState<List<Todo>?>) {
        when (state) {
            is ApiState.Loading -> handleLoadingState()
            is ApiState.Success -> {
                val todos: List<Todo>? = state.data
                updateUI(todos)
            }

            is ApiState.Error -> {
                val errorMessage: String = state.message
                val data: List<Todo>? = state.data
                showError(errorMessage)
                updateUI(data)
            }

            is ApiState.Exception -> {
                val exception: Throwable = state.exception
                handleException(exception)
            }
        }
    }

    private fun handleLoadingState() {
        Log.i(TAG, "handleLoadingState...")
    }

    private fun updateUI(todos: List<Todo>?) {
        Log.d(TAG, "updateUI: ${todos?.get(0)}")
    }

    private fun showError(errorMessage: String) {
        Log.w(TAG, "showError: $errorMessage")
    }

    private fun handleException(exception: Throwable) {
        Log.e(TAG, "handleException: ${exception.message}")
    }
}

interface ApiStateCallback<T> {
    fun onApiStateChanged(state: ApiState<T?>)
}

@HiltViewModel
class ApiViewModel @Inject constructor(private val repository: ApiRepository, private val apiStateCallback: ApiStateCallback<List<Todo>>) : ViewModel() {
    private val _todosLiveData = MutableLiveData<List<Todo>>()
    val todosLiveData: LiveData<List<Todo>> = _todosLiveData

    fun getTodos() {
        viewModelScope.launch {
            apiStateCallback.onApiStateChanged(ApiState.Loading)
            val apiState = repository.getTodosState()
            if (apiState is ApiState.Success) {
                apiState.data?.let { _todosLiveData.value = (it) }
            }
            apiStateCallback.onApiStateChanged(apiState)
        }
    }

    private var todosById: List<Todo>? = null
    private var todoByPosition: Todo? = null

    suspend fun getTodosById(userId: Int): List<Todo>? {
        if (todosById == null) {
            val apiState = repository.getTodosByIdState(userId)
            if (apiState is ApiState.Success) {
                todosById = apiState.data
            }
//            apiStateCallback.onApiStateChanged(apiState)
        }
        return todosById
    }

    suspend fun getTodosByPosition(): Todo? {
        if (todoByPosition == null) {
            val apiState = repository.getTodosByPositionState()
            if (apiState is ApiState.Success) {
                todoByPosition = apiState.data
            }
        }
        return todoByPosition
    }
}

interface ApiRepository {
    suspend fun getTodosState(): ApiState<List<Todo>?>
    suspend fun getTodosByIdState(userId: Int): ApiState<List<Todo>?>
    suspend fun getTodosByPositionState(): ApiState<Todo>
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideAuthApi(): ApiService {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): ApiRepository {
        return ApiRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideApiStateCallback(): ApiStateCallback<List<Todo>> {
        return Main()
    }
}

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {
    override suspend fun getTodosState(): ApiState<List<Todo>?> {
        return try {
            val response = apiService.fetchTodos()
            if (response.isSuccessful) {
                ApiState.Success(response.body())
            } else {
                ApiState.Error("API call failed", null)
            }
        } catch (e: Exception) {
            ApiState.Exception(e, null)
        }
    }

    override suspend fun getTodosByIdState(userId: Int): ApiState<List<Todo>?> {
        return try {
            val response = apiService.fetchTodosById(userId)
            if (response.isSuccessful) {
                ApiState.Success(response.body())
            } else {
                ApiState.Error("API call failed", null)
            }
        } catch (e: Exception) {
            ApiState.Exception(e, null)
        }
    }

    override suspend fun getTodosByPositionState(): ApiState<Todo> {
        return try {
            val response = apiService.fetchTodos()
            val position = 40
            if (response.isSuccessful) {
                val todos: List<Todo>? = response.body()
                if (todos?.isNotEmpty() == true) {
                    if (position >= 0 && position < todos.size) {
                        ApiState.Success(todos[position])
                    } else {
                        ApiState.Error("Invalid position", null)
                    }
                } else {
                    ApiState.Error("Empty list or null", null)
                }
            } else {
                ApiState.Error("API call failed", null)
            }
        } catch (e: Exception) {
            ApiState.Exception(e, null)
        }
    }
}

sealed class ApiState<out T> {
    data object Loading : ApiState<Nothing>()
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error<T>(val message: String, val data: T?) : ApiState<T>()
    data class Exception<T>(val exception: Throwable, val data: T?) : ApiState<T>()
}

interface ApiService {
    @GET("todos")
    suspend fun fetchTodos(): Response<List<Todo>>

    @GET("todos")
    suspend fun fetchTodosById(@Query("userId") userId: Int): Response<List<Todo>>
}

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean,
)

object Constants {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
}
