package com.dinesh.android.compose.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyButtonView(modifier: Modifier = Modifier, onClick: () -> Unit, content: @Composable RowScope.() -> Unit = { Text(text = "Button") }) {
    Button(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .then(modifier), onClick = onClick, content = content)
}