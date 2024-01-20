package com.dinesh.android.animation.line

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dinesh.android.R

@Preview(showBackground = true)
@Composable
private fun MyLayoutView2() {
    //  Adapted from: https://gist.github.com/giovanileitevitor/b8073eb79ee27fab8ae8855b952cd6e8
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text(
            text = "Text",
            modifier = Modifier.padding(16.dp)
                .dashedBorder(
                    color = Color.Blue,
                    strokeLength = 40.dp,
                    strokeWidth = 2.dp,
                    animate = true
                )
        )

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Image",
            modifier = Modifier.padding(16.dp).dashedBorder(
                color = Color.Red,
                strokeLength = 40.dp,
                strokeWidth = 2.dp,
                animate = true
            )
        )

    }
}

fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp,
    strokeLength: Dp,
    animate: Boolean = true,
) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val strokeLengthPx = density.run { strokeLength.toPx() }
        var lastAnimValue by remember { mutableFloatStateOf(0f) }
        val anim = remember(animate) { Animatable(lastAnimValue) }

        LaunchedEffect(animate) {
            if (animate) {
                anim.animateTo(
                    targetValue = (strokeLengthPx * 2 + lastAnimValue),
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart,
                    )
                ) {
                    lastAnimValue = value // store the anim value
                }
            }
        }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(strokeLengthPx, strokeLengthPx),
                            phase = anim.value
                        )
                    )
                    drawRect(
                        color = color,
                        style = stroke
                    )
                }
            }
        )
    }
)

