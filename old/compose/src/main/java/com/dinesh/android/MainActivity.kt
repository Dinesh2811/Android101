package com.dinesh.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dinesh.android.animation.line.MyLayoutView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    com.dinesh.android.basic.text_field.MyLayoutView()
                    MyBox()
                }
            }
        }

//        startActivity(Intent(this, com.dinesh.android.home.HomeActivity::class.java))
    }
}

@Preview(showBackground = true)
@Composable
fun MyBox() {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Box(modifier = Modifier.width(200.dp).height(200.dp)) {
        Box(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(0.7F)) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.error) {

            }
        }
    }
}


//@Composable
//fun VerticalEndBarrierExample(
//    modifier: Modifier = Modifier,
//    texts: List<Pair<String, String>>,
//    textStyle: TextStyle
//) {
//    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
//        val refs = mutableListOf<ConstrainedLayoutReference>()
//        val textRefs = mutableListOf<ConstrainedLayoutReference>()
//
//        texts.forEachIndexed { index, (text1, text2) ->
//            val ref1 = createRef()
//            val ref2 = createRef()
//
//            Text(
//                text = text1,
//                style = textStyle,
//                modifier = Modifier.constrainAs(ref1) {
//                    top.linkTo(parent.top, margin = 16.dp * (index + 1))
//                    start.linkTo(parent.start, margin = 16.dp)
//                }
//            )
//
//            refs.add(ref1)
//            textRefs.add(ref2)
//
//            Text(
//                text = "",
//                style = textStyle,
//                modifier = Modifier.constrainAs(ref2) {
//                    top.linkTo(ref1.top)
//                    start.linkTo(ref1.end, margin = 16.dp)
//                }
//            )
//        }
//
//        val barrier = createEndBarrier(*refs.toTypedArray(), margin = 16.dp)
//        textRefs.forEachIndexed { index, ref ->
//            val ref2 = createRef()
//            Text(
//                text = texts[index].second,
//                style = textStyle,
//                modifier = Modifier.constrainAs(ref2) {
//                    top.linkTo(ref.top)
//                    start.linkTo(barrier)
//                }
//            )
//        }
//
//    }
//}

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