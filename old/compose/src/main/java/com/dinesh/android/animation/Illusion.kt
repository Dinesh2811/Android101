package com.dinesh.android.animation

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

private const val TAG = "Illusion"

@Preview(showBackground = true)
@Composable
fun Illusion() {
    //  Adapted from: https://gist.github.com/vikas-bh855/ab6cc193b21dfffdb932d44d5a7561e8
    val animateList: MutableList<Animatable<Float, AnimationVector1D>> = mutableListOf()
    val targetValueList = listOf(
            240000f,
            230000f,
            220000f,
            210000f,
            200000f,
            190000f,
            180000f,
            170000f,
            160000f,
            150000f,
            140000f,
            130000f,
            120000f,
            110000f,
            100000f,
            90000f,
            80000f,
            70000f
        )
    val boxSizeList = listOf(
        1.52f,
        1.62f,
        1.72f,
        1.85f,
        2f,
        2.18f,
        2.4f,
        2.65f,
        3f,
        3.4f,
        4f,
        4.7f,
        5.7f,
        6.8f,
        8.5f,
        11f,
        16f,
        50f
    )
    val offsetList = mutableListOf<Float>()
    boxSizeList.forEach {
        val offsetX = ((1080f - (1080 / it)) / 2f)
        offsetList.add(offsetX)
        animateList.add(remember { Animatable(0f) })
    }

    LaunchedEffect(animateList[0].value) {
        for (fl in targetValueList.indices) {
            launch {
                animateList[fl].animateTo(
                    targetValueList[fl],
                    tween(durationMillis = 5000)
                )
            }
        }
    }
    val sizeInPx = with(LocalDensity.current) { 1080.toDp() }
    Canvas(
        modifier = Modifier
            .width(sizeInPx)
            .height(sizeInPx)
    ) {
        Log.d(TAG, "Width==" + size.width + "Height==" + size.height)
        animateList.forEachIndexed { index, animatable ->
            rotate(degrees = animatable.value, block = {
                drawRect(
                    brush = Brush.radialGradient(
                        listOf(
                            Color.Magenta,
                            Color.Cyan,
                            Color.Magenta,
                            Color.Cyan
                        )
                    ),
                    style = Stroke(width = 8f),
                    topLeft = Offset(offsetList[index], offsetList[index]),
                    size = Size(1080 / boxSizeList[index], 1080 / boxSizeList[index])
                )
            })
        }
    }
}