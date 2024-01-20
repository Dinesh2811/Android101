package com.dinesh.android.scaffold_layout.top_app_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    MyScaffoldLayout(true)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffoldLayout(showTopAppBar: Boolean = true) {
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isCollapsed by remember { derivedStateOf { (scrollBehavior.state.heightOffset) != 0F } }
    val surfaceColor = if (isCollapsed) MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp) else MaterialTheme.colorScheme.surface

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (showTopAppBar) {
                MyTopAppBar(scrollBehavior, surfaceColor)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MyScreen("TopAppBar")
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyTopAppBar(scrollBehavior: TopAppBarScrollBehavior, surfaceColor: Color) {
    MediumTopAppBar(
        title = {
            Text(text = "Jetpack Compose TabLayout {scrollBehavior.state.heightOffset}", maxLines = 1,
                overflow = TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = surfaceColor),
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) { Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back") }
        }, actions = {
            IconButton(onClick = { /*TODO*/ }) { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite") }
            IconButton(onClick = { /*TODO*/ }) { Icon(imageVector = Icons.Default.Info, contentDescription = "Info") }
            IconButton(onClick = { /*TODO*/ }) { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings") }
        }, scrollBehavior = scrollBehavior
    )
}

@Composable
fun MyScreen(text: String){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(100) { index ->
            Text(text = "$text : $index", modifier = Modifier.padding(8.dp))
        }
    }
}


