package com.dinesh.android.animation.line

import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.material.*

@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        AnimatedBorderCard {
            Text(text = "AnimatedBorderCard", modifier = Modifier.fillMaxSize().padding(16.dp))
        }
    }
}

@Composable
private fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 16.dp),
    borderWidth: Dp = 2.dp,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Magenta, Color.Cyan)),
    animationDuration: Int = 10000,
    onCardClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    //  Adapted from: https://gist.github.com/stevdza-san/0e336399024f1047c5746b39556e3433
    //  https://www.youtube.com/watch?v=KvW4BTL7yMs&t=218s

    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Surface(
        modifier = modifier.clickable { onCardClick() },
        shape = shape
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(borderWidth)
                .drawWithContent {
                    rotate(degrees = degrees) {
                        drawCircle(
                            brush = gradient,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = MaterialTheme.colorScheme.surface,
            shape = shape
        ) {
            content()
        }
    }
}