package com.dinesh.android.compose.text_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
private fun MyTextField(value: String, onValueChanged: (String) -> Unit, label: String) {
    TextField(value = value, onValueChange = onValueChanged, label = { Text(label) }, modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth())
}


@Composable
private fun MyTextField(modifier: Modifier = Modifier, value: String, onValueChanged: (String) -> Unit, label: String = "Name", hintPlaceHolder: String = "Enter the name") {
    OutlinedTextField(value = value, onValueChange = onValueChanged, label = { Text(label) },
        placeholder = { Text(hintPlaceHolder) }, modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(modifier), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
}
