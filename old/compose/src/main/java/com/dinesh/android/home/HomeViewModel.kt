package com.dinesh.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class HomeViewModel : ViewModel() {
    val searchViewModel: SearchViewModel = SearchViewModel()

    private val _selectedItem: MutableStateFlow<RvData?> = MutableStateFlow(null)
    val selectedItem: StateFlow<RvData?> = _selectedItem.asStateFlow()

    fun updateSelectedItem(newValue: RvData?) {
        viewModelScope.launch {
            _selectedItem.emit(newValue)
        }
    }

    private val _navScreen: MutableStateFlow<LinkedHashSet<RvData>> = MutableStateFlow(LinkedHashSet())
    val navScreen: StateFlow<LinkedHashSet<RvData>> = _navScreen.asStateFlow()

    fun addNavScreen(newValue: RvData) {
        viewModelScope.launch {
            _navScreen.emit(_navScreen.value.apply {
                add(newValue)
            })
        }
    }

    fun removeNavScreen(newValue: RvData? = null) {
        viewModelScope.launch {
            _navScreen.emit(_navScreen.value.apply {
                if (newValue == null){
                    remove(lastOrNull())
                } else{
                    remove(newValue)
                }
            })
            updateSelectedItem(_navScreen.value.lastOrNull())
        }
    }
}

class SearchViewModel : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            _searchText.emit(query)
            performSearchQuery(query)
        }
    }

    var filteredList: MutableStateFlow<List<RvData>> = MutableStateFlow(rvDataList)
    private fun performSearchQuery(query: String) {
        viewModelScope.launch {
            val list = rvDataList.filter {
                it.title.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault())) || it.description.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
            }
            filteredList.emit(list)
        }
    }
}