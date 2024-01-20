package com.dinesh.android.constraint_layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout


@Preview(showBackground = true)
@Composable
fun MyScreen() {
    val rows = listOf(
        Pair("shortField.", "value1"),
        Pair("mediumField...", "value2"),
        Pair("longField..........", "value3")
    )
    VerticalEndBarrierExample(
        texts = rows,
        textStyle = TextStyle(fontSize = 16.sp)
    )
}


@Composable
fun VerticalEndBarrierExample(
    modifier: Modifier = Modifier,
    texts: List<Pair<String, String>>,
    textStyle: TextStyle
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val refs = mutableListOf<ConstrainedLayoutReference>()

        texts.forEachIndexed { index, (text1, text2) ->
            val ref1 = createRef()

            Text(
                text = text1,
                style = textStyle,
                modifier = Modifier.constrainAs(ref1) {
                    top.linkTo(parent.top, margin = 16.dp * (index + 1))
                    start.linkTo(parent.start, margin = 16.dp)
                }
            )

            refs.add(ref1)
        }

        val barrier = createEndBarrier(*refs.toTypedArray(), margin = 16.dp)
        refs.forEachIndexed { index, ref ->
            Text(
                text = texts[index].second,
                style = textStyle,
                modifier = Modifier.constrainAs(createRef()) {
                    top.linkTo(ref.top)
                    start.linkTo(barrier)
                }
            )
        }
    }
}