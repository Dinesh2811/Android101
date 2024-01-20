package com.dinesh.android.basic.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Preview(showBackground = true)
@Composable
fun MyLayoutView(){
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Text", style = MaterialTheme.typography.displayMedium)
        Text(text = "Text", style = MaterialTheme.typography.titleMedium)
        Text(text = "Text", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Text", style = MaterialTheme.typography.labelMedium)
    }
}