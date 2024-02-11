package com.dinesh.basic

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class RowItem(val id: Int, val title: String)
data class ColumnItem(val id: Int, val rows: List<RowItem>)

@Composable
fun NestedLazyColumnAndRow(columnItems: List<ColumnItem>, onClick: (RowItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(columnItems) { columnItem ->
            val context = LocalContext.current

//            Row(modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Absolute.Right,
//                verticalAlignment = Alignment.CenterVertically) {
//                Text(text = "Column ${columnItem.id}", modifier = Modifier.padding(vertical = 16.dp))
//                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, modifier = Modifier.wrapContentSize())
//            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Column Column Column Column Column Column Column Column",
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize(Alignment.Center)
                        .background(Color.LightGray),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .padding(48.dp)
                        .wrapContentSize(Alignment.Center)
                        .clickable {
                            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                            Log.i("log_", "NestedLazyColumnAndRow: testing")
                        }

                )
            }


            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(columnItem.rows) { rowItem ->
                    Text(text = rowItem.title, modifier = Modifier.clickable { onClick(rowItem) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNestedLazyColumnAndRow() {
    NestedLazyColumnAndRow(dummyData) { rowItem ->
        println("Clicked on ${rowItem.title}")
    }
}

val dummyData = List(100) { i ->
    ColumnItem(i, List(10) { j -> RowItem(j, "Row$j") })
}
