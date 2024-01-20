package com.dinesh.android.navigation.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.*
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.rotate
import kotlinx.coroutines.*
import androidx.compose.material.*
import androidx.navigation.compose.*
import androidx.navigation.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.rout
                    ) {
                        composable(Screen.Home.rout) {
                            HomeScreen(navController = navController)
                        }

                        composable(
                            Screen.Second.rout + "/{url}/{counter}",
                            arguments = listOf(
                                navArgument("url") { type = NavType.StringType },
                                navArgument("counter") { type = NavType.IntType },
                            )
                        ) { backStackEntry ->

                            SecondScreen(
                                backStackEntry = backStackEntry,
                                navController = navController
                            )
                        }

                        composable(Screen.Last.rout) {
                            LastScreen(navController = navController)
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.Second.rout + "/www.url.com/44")
        }) {
            Text(
                text = "Go To Second",
            )
        }
    }
}

@Composable
fun SecondScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry
) {

    val url = backStackEntry.arguments?.getString("url")
    val counter = backStackEntry.arguments?.getInt("counter")


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Second Screen, url: $url - counter: $counter",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.Last.rout)
        }) {
            Text(
                text = "Go To Last",
            )
        }
    }
}

@Composable
fun LastScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Last Screen",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.popBackStack()
            navController.popBackStack()
        }) {
            Text(
                text = "Go Back",
            )
        }
    }
}

sealed class Screen(val rout: String) {
    object Home : Screen("home")
    object Second : Screen("second")
    object Last : Screen("last")
}