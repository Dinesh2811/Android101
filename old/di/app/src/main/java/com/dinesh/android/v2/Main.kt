package com.dinesh.android.v2

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val TAG = "log_API_v3"

class Main : AppCompatActivity(), ApiStateCallback<List<Todo>> {
    private val apiService = ApiClient.getApiInterface<ApiService>(Constants.BASE_URL)
    private val apiRepositoryImpl: ApiRepositoryImpl = ApiRepositoryImpl(apiService, 40)
    private val apiViewModel: ApiViewModel by viewModels {
        ApiViewModelFactory(apiRepositoryImpl, this)
    }

    private var todosList: List<Todo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            apiViewModel.getTodos()
        }

        apiViewModel.todosLiveData.observe(this) { todos ->
            Log.e(TAG, "onCreate: ${todos[7]}")
        }

        apiViewModel.todosFlow.onEach { todos ->
            todosList = todos
        }.catch { exception ->
            Log.e(TAG, "Error observing todosFlow: ${exception.message}")
        }.launchIn(lifecycleScope)

        lifecycleScope.launch(Dispatchers.Main) {
            Log.i(TAG, "getTodosById: ${apiViewModel.getTodosById(2)?.size}")
            Log.d(TAG, "getTodosByPosition: ${apiViewModel.getTodosByPosition()}")
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
        Log.i(TAG, "handleLoadingState")
    }

    private fun updateUI(todos: List<Todo>?) {
        Log.d(TAG, "updateUI: ${todos?.get(0)}")
    }

    private fun showError(errorMessage: String) {
        Log.w(TAG, "showError: $errorMessage" )
    }

    private fun handleException(exception: Throwable) {
        Log.e(TAG, "handleException: ${exception.message}")
    }

}

interface ApiStateCallback<T> {
    fun onApiStateChanged(state: ApiState<T?>)
}

class ApiViewModel(private val repository: ApiRepository, private val apiStateCallback: ApiStateCallback<List<Todo>>) : ViewModel() {
    private val _todosLiveData = MutableLiveData<List<Todo>>()
    val todosLiveData: LiveData<List<Todo>> = _todosLiveData

    private val _todosFlow: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
    val todosFlow: StateFlow<List<Todo>> = _todosFlow.asStateFlow()

    fun getTodos() {
        viewModelScope.launch {
            apiStateCallback.onApiStateChanged(ApiState.Loading)
            val apiState = repository.getTodosState()
            if (apiState is ApiState.Success) {
                apiState.data?.let { todosList ->
                    _todosLiveData.value = todosList
                    viewModelScope.launch { _todosFlow.emit(todosList) }
                }
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

class ApiViewModelFactory(private val repository: ApiRepository, private val apiStateCallback: ApiStateCallback<List<Todo>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ApiViewModel(repository, apiStateCallback) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

interface ApiRepository {
    suspend fun getTodosState(): ApiState<List<Todo>?>
    suspend fun getTodosByIdState(userId: Int): ApiState<List<Todo>?>
    suspend fun getTodosByPositionState(): ApiState<Todo>
}

class ApiRepositoryImpl(private val apiService: ApiService, private val position: Int) : ApiRepository {

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
            if (response.isSuccessful) {
                val todos: List<Todo>? = response.body()
                if (todos?.isNotEmpty() == true) {
                    if (position >= 0 && position < todos.size) {
                        ApiState.Success(todos[position])
                    } else{
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
    data class Success<T>(val  data: T) : ApiState<T>()
    data class Error<T>(val message: String, val data: T?) : ApiState<T>()
    data class Exception<T>(val exception: Throwable, val data: T?) : ApiState<T>()
}

object ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> getApiInterface(apiInterface: Class<T>, baseUrl: String = "http://10.0.2.2/"): T {
        return createRetrofit(baseUrl).create(apiInterface)
    }

    inline fun <reified T> getApiInterface(baseUrl: String = "http://10.0.2.2/"): T {
        return getApiInterface(T::class.java, baseUrl)
    }
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
    val completed: Boolean
)

object Constants {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
}
