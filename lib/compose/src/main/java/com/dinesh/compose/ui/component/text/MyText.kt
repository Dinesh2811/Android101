package com.dinesh.compose.ui.component.text

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    group = "Light",
    name = "Light",
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    group = "Dark",
    name = "Dark",
)
annotation class MyPreviews


@MyPreviews
@Composable
fun MyText(
    text: String = "Text",
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    color: Color = Color(0xFF646464),
    fontWeight: FontWeight = FontWeight.W500,
    fontSize: TextUnit = 14.sp,
    fontResId: Int? = null,
    onClick: (() -> Unit)? = null
) {
    val customModifier = modifier.wrapContentSize()
    val fontFamily = if (fontResId != null) FontFamily(Font(fontResId)) else null

    Text(
        text = text,
        modifier = customModifier.clickable { onClick?.invoke() }.then(modifier),
        fontFamily = fontFamily,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize
    )

}
