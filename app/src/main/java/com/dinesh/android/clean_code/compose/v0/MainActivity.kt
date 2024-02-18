package com.dinesh.android.clean_code.compose.v0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class User(val name: String, val age: Int)

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun updateUser(name: String, age: Int) {
        _user.value = User(name, age)
    }
}

@Composable
fun UserScreen(userViewModel: UserViewModel) {
    val user by userViewModel.user.observeAsState()
    Column {
        user?.let { user ->
            Text("Name: ${user.name}")
            Text("Age: ${user.age}")
        }
        Button(onClick = { userViewModel.updateUser("John", 30) }) {
            Text("Update User")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MyApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            DetailScreen(id)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column {
        Text("Home Screen")
        Button(onClick = { navController.navigate("detail/10") }) {
            Text("Go to Detail Screen")
        }
    }
}

@Composable
fun DetailScreen(id: String?) {
    Text("Detail Screen: $id")
}

@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    Counter(count) { count++ }
}

@Composable
fun Counter(count: Int, onClick: () -> Unit) {
    Column {
        Text("Count: $count")
        Button(onClick = onClick) {
            Text("Increment")
        }
    }
}
