package com.dinesh.basic

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class RowItem(val id: Int, val title: String)
data class ColumnItem(val id: Int, val rows: List<RowItem>)

@Composable
fun NestedLazyColumnAndRow(columnItems: List<ColumnItem>, onClick: (RowItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(columnItems) { columnItem ->
            Text(text = "Column ${columnItem.id}")
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(columnItem.rows) { rowItem ->
                    Text(text = rowItem.title, modifier = Modifier.clickable { onClick(rowItem) })
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNestedLazyColumnAndRow() {
    NestedLazyColumnAndRow(dummyData) { rowItem ->
        println("Clicked on ${rowItem.title}")
    }
}

val dummyData = List(100) { i ->
    ColumnItem(i, List(10) { j -> RowItem(j, "Row$j") })
}
