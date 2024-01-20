package com.dinesh.android.compose.scaffold_layout.bottom_app_bar

import android.graphics.drawable.ClipDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class ShowBottomAppBar(
    val showBottomAppBar: Boolean,
    val bottomAppBarItem: BottomAppBarItem,
    val onBottomAppBarItemClicked: (BottomAppBarItem) -> Unit
)

@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    var bottomAppBarItem by remember { mutableStateOf(bottomAppBarItems[0]) }

    MyScaffoldLayout(
        bottomAppBar = ShowBottomAppBar(showBottomAppBar = true, bottomAppBarItem = bottomAppBarItem, onBottomAppBarItemClicked = { bottomAppBarItem = it })
    )
}

@Composable
fun MyScaffoldLayout(bottomAppBar: ShowBottomAppBar) {
    Scaffold(
        bottomBar = {
            if (bottomAppBar.showBottomAppBar) {
                MyBottomAppBarLayout(
                    bottomAppBarItem = bottomAppBar.bottomAppBarItem,
                    onBottomAppBarItemClicked = bottomAppBar.onBottomAppBarItemClicked
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (bottomAppBar.showBottomAppBar) {
                bottomAppBar.bottomAppBarItem.screen()
            }
        }
    }
}

@Composable
private fun MyBottomAppBarLayout(bottomAppBarItem: BottomAppBarItem, onBottomAppBarItemClicked: (BottomAppBarItem) -> Unit) {
    BottomAppBar(
        actions = {
            bottomAppBarItems.forEachIndexed { index, item ->
//                val selectedColor = if (bottomAppBarItem == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
//                val iconTint = rememberUpdatedState(selectedColor)
                val imageVector = item.icon

                IconButton(onClick = { onBottomAppBarItemClicked(item) }) { Icon(imageVector = imageVector, contentDescription = item.title) }
            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { /* doSomething */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        })
}

val bottomAppBarItems = listOf(
    BottomAppBarItem(title = "Home", icon = Icons.Default.Home, screen = { MyScreen("Home Screen") }),
    BottomAppBarItem(title = "Favorite", icon = Icons.Default.Favorite, screen = { MyScreen("Favorite Screen") }),
    BottomAppBarItem(title = "Create", icon = Icons.Default.Create, screen = { MyScreen("Create Screen") }),
    BottomAppBarItem(title = "Settings", icon = Icons.Default.Settings, screen = { MyScreen("Settings Screen") })
)

data class BottomAppBarItem(val title: String, val icon: ImageVector, val screen: @Composable () -> Unit)

@Composable
fun MyScreen(text: String) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = text)
    }
}

