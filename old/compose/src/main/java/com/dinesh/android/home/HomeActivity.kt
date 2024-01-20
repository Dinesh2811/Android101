package com.dinesh.android.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

private val TAG = "log_" + HomeActivity::class.java.name.split(HomeActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeViewModel

    var backButtonPressedTime = 0L
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentTime = System.currentTimeMillis()
            Log.e(TAG, "onBackPressedCallback: ")
            if (viewModel.selectedItem.value == null) {
                    if (backButtonPressedTime + 2000 > currentTime) {
                        finishAffinity()
                    } else {
                        Toast.makeText(this@HomeActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "onBackPressed: Press back again to exit")
                    }
                backButtonPressedTime = currentTime
            } else{
                viewModel.removeNavScreen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyLayoutView()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

}

@Composable
fun MyLayoutView() {
    MyHomeLayout()
}

@Preview(showBackground = true)
@Composable
private fun MyHomeLayout(){
    val viewModel: HomeViewModel = viewModel()
    val selectedItem by viewModel.selectedItem.collectAsState()

    Column {
        if (selectedItem == null) {
            MySearchLayout()
        } else {
            selectedItem?.screen?.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MySearchLayout() {
    val homeViewModel: HomeViewModel = viewModel()
    val searchViewModel: SearchViewModel = homeViewModel.searchViewModel
    val searchQuery by searchViewModel.searchText.collectAsState()
    val filteredList by searchViewModel.filteredList.collectAsState()

    Column {
        OutlinedTextField(value = searchQuery, onValueChange = { searchViewModel.setSearchQuery(it) }, label = { Text(text = "Search") }, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filteredList) { item ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (item.screen != null) {
                            homeViewModel.addNavScreen(item)
                            homeViewModel.updateSelectedItem(item)
                        }
                    }, verticalArrangement = Arrangement.Center) {
                    Text(text = item.title, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
