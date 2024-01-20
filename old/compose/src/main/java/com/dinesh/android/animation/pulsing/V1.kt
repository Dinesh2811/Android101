package com.dinesh.android.animation.pulsing

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
private fun MyLayoutView() {
    //  Adapted from: https://gist.github.com/anangkur/49b0d80973e23882deced5ce82766066
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        ExpandableFabAnimateColorSizePosition()
    }
}

@ExperimentalAnimationApi
@Composable
private fun ExpandableFabAnimateColorSizePosition(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "")
    val contentColor = transition.animateColor(
        targetValue = Color.Green,
        initialValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val contentWidth = transition.animateFloat(
        targetValue = 300f,
        initialValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "",
    )
    val contentHeight = transition.animateFloat(
        targetValue = 20f,
        initialValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "",
    )
    val contentPosition = transition.animateFloat(
        targetValue = 20f,
        initialValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "",
    )

    val iconColor = transition.animateColor(
        targetValue = Color.Black,
        initialValue = Color.White,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    FloatingActionButton(
        modifier = modifier,
        onClick = {},
        content = {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = iconColor.value
                )
                Text(
                    text = "getString(R.string.button_add_item)",
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .absoluteOffset(x = contentPosition.value.dp)
                        .size(
                            height = contentHeight.value.dp,
                            width = contentWidth.value.dp
                        )
                )
            }
        },
        backgroundColor = contentColor.value
    )
}
