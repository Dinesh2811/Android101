package com.dinesh.android.pager.swipe_card.v0

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue


private val TAG = "log_SwipeCard"

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MySwipeCardMainScreen(modifier: Modifier = Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pageWidth = (screenWidth / 3f).dp

    val listOfCardData = mutableListOf<CardData>()
    repeat(10) { listOfCardData.add(CardData("Str1 -> $it", "Str2 -> $it")) }

    MaterialTheme {
        Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                MyAnimatedViewPager(modifier = Modifier.fillMaxWidth(), pageSize = pageWidth, listOfCardData = listOfCardData)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MyAnimatedViewPager(modifier: Modifier = Modifier, pageSize: Dp, listOfCardData: List<CardData>) {
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f, pageCount = { listOfCardData.size })

    HorizontalPager(modifier = modifier, state = pagerState, contentPadding = PaddingValues(horizontal = pageSize), verticalAlignment = Alignment.CenterVertically) { thisPageIndex ->
        val pageOffset = (pagerState.currentPage - thisPageIndex) + pagerState.currentPageOffsetFraction
        val scale = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f) * 0.5f

        MyPageLayout(
            modifier = Modifier
                .size(size = pageSize)
                .graphicsLayer {
                    alpha = lerp(start = 0.4f, stop = 1f, fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
                    cameraDistance = 8 * density
                    scaleX = scale
                    scaleY = scale
                }, cardData = listOfCardData[thisPageIndex]
        )

    }
}

@Composable
private fun MyPageLayout(modifier: Modifier = Modifier, cardData: CardData) {
    Card(modifier = modifier) {
        Column(modifier = modifier
            .fillMaxSize()) {
            Text(text = cardData.str1, modifier = Modifier.clickable { Log.i(TAG, "PageLayout: ${cardData.str1}") })
            Text(text = cardData.str2, modifier = Modifier.clickable { Log.i(TAG, "PageLayout: ${cardData.str2}") })
        }
    }
}

private data class CardData(var str1: String, var str2: String)
