package com.dinesh.android.testing

import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.*
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.rotate
import kotlinx.coroutines.*
import androidx.navigation.compose.*
import androidx.navigation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout



@Preview(showBackground = true)
@Composable
fun CarInfo() {
    val data = listOf(
        Pair("Make", "BMW"),
        Pair("Model", "3 Series")
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text(text = "Car", modifier = Modifier.padding(8.dp))
            Divider()
        }

        items(data) { pair ->
            InfoField(pair.first, pair.second)
        }
    }
}

@Composable
fun InfoField(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            modifier = Modifier.width(IntrinsicSize.Max)
        )
        Text(text = ":")
        Text(text = value, modifier = Modifier.weight(1f))
    }
}



/*

@Preview(showBackground = true)
@Composable
fun CarInfo() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Car", modifier = Modifier.padding(8.dp))
        Divider()
        InfoField("Make", "BMW")
        InfoField("Model", "3 Series")
        InfoField("Additional Details", "3 Series")
    }
}

@Composable
fun InfoField(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(text = ":", modifier = Modifier.padding(end = 8.dp))
        Text(text = value, modifier = Modifier.weight(3f))
    }
}

 */