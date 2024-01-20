package com.dinesh.android.kotlin.retrofit.v6

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

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

//        apiViewModel.todosLiveData.observe(this) { todos ->
//            Log.e(TAG, "onCreate: ${todos[7]}")
//        }

        lifecycleScope.launch(Dispatchers.Main) {
            apiViewModel.todosStateFlow.onEach { state ->
                // Perform side effects (e.g., logging) here
//                    Log.i(TAG, "onCreate: $state")
            }.flowOn(Dispatchers.IO).catch { exception ->
                Log.e(TAG, "Error observing Flow: ${exception.message}")
            }.collect { state ->
                // Handle the collected data and update the UI
                onApiStateChanged(state)
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            apiViewModel.getTodosById(2).flowOn(Dispatchers.IO)
                .catch { exception ->
                    Log.e(TAG, "Error in getTodosById: ${exception.message}")
                }.collect{ todos ->
                    todos?.let {
//                        Log.i(TAG, "getTodosById: ${it.size}")
                    }
                }
//            Log.d(TAG, "getTodosByPosition: ${apiViewModel.getTodosByPosition()}")
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

    private val _todosStateFlow: MutableStateFlow<ApiState<List<Todo>?>> = MutableStateFlow(ApiState.Loading)
    val todosStateFlow: StateFlow<ApiState<List<Todo>?>> = _todosStateFlow.asStateFlow()

    fun getTodos() {
        viewModelScope.launch {
            repository.getTodosState()
                .flowOn(Dispatchers.IO)
                .onStart { _todosStateFlow.value = ApiState.Loading }
                .collect { apiState ->
                    when (apiState) {
                        is ApiState.Loading -> { _todosStateFlow.value = ApiState.Loading }
                        is ApiState.Success -> {
                            apiState.data?.let { todosList ->
                                _todosLiveData.value = todosList
                            }
                            _todosStateFlow.value = apiState
                        }
                        is ApiState.Error -> {
                            _todosStateFlow.value = apiState
                        }
                        is ApiState.Exception -> {
                            _todosStateFlow.value = apiState
                        }
                    }
                }
        }
    }

    private var todosById: List<Todo>? = null
    private var todoByPosition: Todo? = null

    fun getTodosById(userId: Int): Flow<List<Todo>?> {
        return flow {
            repository.getTodosByIdState(userId)
                .flowOn(Dispatchers.IO)
//                .onStart { apiStateCallback.onApiStateChanged(ApiState.Loading) }
                .collect { apiState ->
                    apiStateCallback.onApiStateChanged(apiState)
                    if (apiState is ApiState.Success) {
//                        apiStateCallback.onApiStateChanged(ApiState.Success(apiState.data))
                        todosById = apiState.data
                        emit(todosById)
                    }
                }
        }
    }

    suspend fun getTodosByPosition(): Todo? {
        repository.getTodosByPositionState()
            .flowOn(Dispatchers.IO)
            .onStart { apiStateCallback.onApiStateChanged(ApiState.Loading) }
            .collect { apiState ->
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
    suspend fun getTodosState(): Flow<ApiState<List<Todo>?>>
    suspend fun getTodosByIdState(userId: Int): Flow<ApiState<List<Todo>?>>
    suspend fun getTodosByPositionState(): Flow<ApiState<Todo>>
}

class ApiRepositoryImpl(private val apiService: ApiService, private val position: Int) : ApiRepository {

    override suspend fun getTodosState(): Flow<ApiState<List<Todo>?>> {
        return flow {
            emit(ApiState.Loading)
            try {
                val response: Response<List<Todo>> = apiService.fetchTodos()
                if (response.isSuccessful) {
                    val todosList: List<Todo> = response.body() ?: emptyList()
                    if (todosList.isNotEmpty()) {
                        emit(ApiState.Success(todosList))
                    } else {
                        emit(ApiState.Error("No todos available", null))
                    }
                } else {
                    emit(ApiState.Error("API call failed", null))
                }
            } catch (e: Exception) {
                emit(ApiState.Exception(e, null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTodosByIdState(userId: Int): Flow<ApiState<List<Todo>?>> {
        return flow {
            emit(ApiState.Loading)
            try {
                val response: Response<List<Todo>> = apiService.fetchTodosById(userId)
                if (response.isSuccessful) {
                    val todosList: List<Todo> = response.body() ?: emptyList()
                    if (todosList.isNotEmpty()) {
                        emit(ApiState.Success(todosList))
                    } else {
                        emit(ApiState.Error("Empty list", null))
                    }
                } else {
                    emit(ApiState.Error("API call failed", null))
                }
            } catch (e: Exception) {
                emit(ApiState.Exception(e, null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTodosByPositionState(): Flow<ApiState<Todo>> {
        return flow {
            try {
                val response: Response<List<Todo>> = apiService.fetchTodos()
                if (response.isSuccessful) {
                    val todos: List<Todo> = response.body()?: emptyList()
                    if (todos.isNotEmpty()) {
                        if (position >= 0 && position < todos.size) {
                            val todo: Todo = todos[position]
                            emit(ApiState.Success(todo))
                        } else {
                            emit(ApiState.Error("Invalid position", null))
                        }
                    } else {
                        emit(ApiState.Error("Empty list", null))
                    }
                } else {
                    emit(ApiState.Error("API call failed", null))
                }
            } catch (e: Exception) {
                emit(ApiState.Exception(e, null))
            }
        }.flowOn(Dispatchers.IO)
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
