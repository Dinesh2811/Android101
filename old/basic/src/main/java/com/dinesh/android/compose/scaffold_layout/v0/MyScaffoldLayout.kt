package com.dinesh.android.compose.scaffold_layout.v0

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun MyScaffoldLayout() {
    val rememberScaffold = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val contextForToast = LocalContext.current.applicationContext

    androidx.compose.material.Scaffold(topBar = {
        TopAppBar(title = {
            Text("App title")
        }, actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        })
    }, bottomBar = {
        androidx.compose.material.BottomNavigation(backgroundColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.onPrimary) {
            BottomNavigationItem(selected = true, icon = { Icon(Icons.Filled.Home, contentDescription = null) }, label = { Text("Home") }, onClick = { /* do something */ })
            BottomNavigationItem(selected = false, icon = { Icon(Icons.Filled.Settings, contentDescription = null) }, label = { Text("Settings") }, onClick = { /* do something */ })
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Add, contentDescription = "Add")
        }
    }, floatingActionButtonPosition = FabPosition.End, isFloatingActionButtonDocked = true,

        drawerContent = {
            Column(Modifier.fillMaxSize()) {
                Text(text = "Drawer Header", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                Text(text = "Item 1", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
                Text(text = "Item 2", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
            }
        }

    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(20) { index ->
                Text("Item $index")
            }
        }
    }

}