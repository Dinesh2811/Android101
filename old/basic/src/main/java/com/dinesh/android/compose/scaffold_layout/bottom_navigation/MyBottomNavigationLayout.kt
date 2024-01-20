package com.dinesh.android.compose.scaffold_layout.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ShowBottomNav(
    val showBottomNav: Boolean,
    val bottomNavItem: BottomNavItem,
    val onNavItemClicked: (BottomNavItem) -> Unit
)

@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    var bottomNavItem by remember { mutableStateOf(bottomNavItems[0]) }

    MyScaffoldLayout(
        bottomNav = ShowBottomNav(showBottomNav = true, bottomNavItem = bottomNavItem, onNavItemClicked = { bottomNavItem = it })
    )
}

@Composable
fun MyScaffoldLayout(bottomNav: ShowBottomNav) {
    Scaffold(
        bottomBar = {
            if (bottomNav.showBottomNav) {
                MyBottomNavigationLayout(
                    currentNavItem = bottomNav.bottomNavItem,
                    onNavItemClicked = bottomNav.onNavItemClicked
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (bottomNav.showBottomNav) {
                bottomNav.bottomNavItem.screen()
            }
        }
    }
}

@Composable
private fun MyBottomNavigationLayout(currentNavItem: BottomNavItem, onNavItemClicked: (BottomNavItem) -> Unit) {
    BottomNavigation(elevation = 8.dp, backgroundColor = MaterialTheme.colorScheme.surfaceContainer) {
        bottomNavItems.forEachIndexed { index, item ->
            val selectedColor = if (currentNavItem == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            val iconTint = rememberUpdatedState(selectedColor)
            val imageVector = item.icon

//            BottomNavigationItem(
//                selected = currentNavItem == item,
//                alwaysShowLabel = false,
//                icon = { Icon(imageVector = imageVector, contentDescription = item.title, tint = iconTint.value) },
//                label = { Text(text = item.title, color = selectedColor, style = MaterialTheme.typography.bodyMedium) },
//                onClick = { onNavItemClicked(item) }
//            )


            BottomNavigationItem(
                selected = currentNavItem == item,
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(badge = { Badge { Text("8") } }) {Icon(imageVector = imageVector, contentDescription = item.title, /* tint = iconTint.value */) }
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.secondary,
                label = { Text(text = item.title, style = MaterialTheme.typography.bodyMedium,/* color = selectedColor */) },
                onClick = { onNavItemClicked(item) }
            )
            
        }
    }
}

val bottomNavItems = listOf(
    BottomNavItem(title = "Home", icon = Icons.Default.Home, screen = { MyScreen("Home Screen") }),
    BottomNavItem(title = "Favorite", icon = Icons.Default.Favorite, screen = { MyScreen("Favorite Screen") }),
    BottomNavItem(title = "Create", icon = Icons.Default.Create, screen = { MyScreen("Create Screen") }),
    BottomNavItem(title = "Settings", icon = Icons.Default.Settings, screen = { MyScreen("Settings Screen") })
)

data class BottomNavItem(val title: String, val icon: ImageVector, val screen: @Composable () -> Unit)

@Composable
fun MyScreen(text: String){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = text)
    }
}


