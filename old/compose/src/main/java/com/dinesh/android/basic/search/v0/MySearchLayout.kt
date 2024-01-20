package com.dinesh.android.basic.search.v0

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    val viewModel = viewModel<SearchViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val persons by viewModel.persons.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        TextField(value = searchText, onValueChange = viewModel::onSearchTextChange, modifier = Modifier.fillMaxWidth(), placeholder = { Text(text = "Search") })
        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                items(persons) { person ->
                    Text(text = "${person.firstName} ${person.lastName}", modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp))
                }
            }
        }
    }
}

class SearchViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allPersons)
    val persons = searchText
        .debounce(1000L)
        .onEach {
            _isSearching.update { true }
        }
        .combine(_persons) { text, persons ->
            if (text.isBlank()) {
                persons
            } else {
                // TODO: Network call
                delay(2000L)
                persons.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _persons.value)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

data class Person(
    val firstName: String,
    val lastName: String,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allPersons = listOf(
    Person(firstName = "Philipp", lastName = "Lackner"),
    Person(firstName = "Beff", lastName = "Jezos"),
    Person(firstName = "Chris P.", lastName = "Bacon"),
    Person(firstName = "Jeve", lastName = "Stops"),
)