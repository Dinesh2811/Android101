package com.dinesh.android.basic.autocomplete

import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.*

@Preview(showBackground = true)
@Composable
fun AutoComplete() {
    val categories = listOf("Food", "Beverages", "Sports", "Learning", "Travel", "Rent", "Bills", "Fees", "Others")

    var category by remember { mutableStateOf("") }
    val heightTextFields by remember { mutableStateOf(55.dp) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier
        .padding(30.dp)
        .fillMaxWidth()
        .clickable(interactionSource = interactionSource, indication = null,
            onClick = {
                expanded = false
            }
        )) {
        Text(modifier = Modifier.padding(start = 3.dp, bottom = 2.dp), text = "Category", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Medium)

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(heightTextFields)
                    .border(width = 1.8.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                    value = category,
                    onValueChange = {
                        category = it
                        expanded = true
                    },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, cursorColor = Color.Black),
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(modifier = Modifier.size(24.dp), imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "arrow", tint = Color.Black)
                        }
                    })
            }

            AnimatedVisibility(visible = expanded) {
                Card(modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .width(textFieldSize.width.dp), elevation = 15.dp, shape = RoundedCornerShape(10.dp)) {

                    LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                        if (category.isNotEmpty()) {
                            items(categories.filter {
                                it.lowercase().contains(category.lowercase()) || it.lowercase().contains("others")
                            }.sorted()) {
                                CategoryItems(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        } else {
                            items(categories.sorted()) {
                                CategoryItems(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        }

                    }
                }
            }

        }

    }

}

@Composable
fun CategoryItems(title: String, onSelect: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelect(title) }
        .padding(10.dp)) {
        Text(text = title, fontSize = 16.sp)
    }
}