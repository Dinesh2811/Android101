package com.dinesh.android.compose.sticky_headers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyStickyHeaders() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
    ) {
        repeat(5) {
            stickyHeader {
                Text(
                    text = "Header ${it + 1}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF464549))
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(10) { index ->
                Text(
                    text = "Item $index",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )
            }
        }
    }
}
