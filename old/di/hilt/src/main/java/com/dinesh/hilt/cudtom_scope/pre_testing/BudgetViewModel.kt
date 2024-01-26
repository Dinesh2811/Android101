//package com.dinesh.hilt.cudtom_scope.pre_testing
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class BudgetViewModel @Inject constructor() : ViewModel() {
//
//    private val _token: MutableStateFlow<String> = MutableStateFlow("")
//    val token: StateFlow<String> = _token.asStateFlow()
//    fun updateToken(newValue: String) {
//        viewModelScope.launch { _token.emit(newValue) }
//    }
//
//    fun getApiToken() {
//        viewModelScope.launch {
//            _token.value = "apiState"
//        }
//    }
//
//}