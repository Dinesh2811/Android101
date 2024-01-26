package com.dinesh.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*

private val TAG = "log_" + MainCompose::class.java.name.split(MainCompose::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class MainCompose: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyLayoutView()
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyLayoutView() {
    val rows = listOf(
        Pair("Account Name", "HDFC"),
        Pair("Type", "Savings"),
        Pair("Type", "Savings "),
        Pair("Available ", "Savings"),
        Pair("Current", "6,000.00")
    )

    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                rows?.let {
                    MyAssetsInnerListWithVerticalEndBarrier(
                        texts = it
                    )
                }
            }
        }
    }
}

@Composable
private fun MyAssetsInnerListWithVerticalEndBarrier(
    modifier: Modifier = Modifier,
    texts: List<Pair<String, String>>
) {
    Column(modifier = modifier.fillMaxWidth()) {
        texts.forEach { (text1, text2) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = text1,
                    modifier = Modifier.weight(0.3f)
                )
                Text(
                    text = ":",
                )
                Text(
                    text = text2,
                    modifier = Modifier.weight(0.5f).padding(start = 16.dp)
                )
            }
        }
    }
}



//@Composable
//private fun PlaidAssetsInnerListWithVerticalEndBarrier(
//    modifier: Modifier = Modifier,
//    texts: List<Pair<String, String>>
//) {
//    Column(modifier = modifier.fillMaxWidth()) {
//        texts.forEach { (text1, text2) ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                verticalAlignment = Alignment.Top
//            ) {
//                Text(
//                    text = text1,
//                    modifier = Modifier.weight(0.3f)
//                )
//                Text(
//                    text = ":",
//                )
//                Text(
//                    text = text2,
//                    modifier = Modifier.weight(0.5f).padding(start = 8.dp)
//                )
//            }
//        }
//    }
//}



//@Composable
//private fun PlaidAssetsInnerListWithVerticalEndBarrier(
//    modifier: Modifier = Modifier,
//    texts: List<Pair<String, String>>
//) {
//    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
//        val refs = mutableListOf<ConstrainedLayoutReference>()
//
//        texts.forEachIndexed { index, (text1, text2) ->
//            val ref1 = createRef()
//            Text(
//                text = text1,
//                modifier = Modifier.constrainAs(ref1) {
//                    if (index == 0) {
//                        top.linkTo(parent.top, margin = 8.dp)
//                    } else {
//                        top.linkTo(refs[index - 1].bottom, margin = 8.dp)
//                    }
//                    start.linkTo(parent.start, margin = 0.dp)
//                })
//
//            refs.add(ref1)
//        }
//
//        val barrier = createEndBarrier(*refs.toTypedArray(), margin = 8.dp)
//        refs.forEachIndexed { index, ref ->
//            Text(
//                text = texts[index].second,
//                modifier = Modifier.constrainAs(createRef()) {
//                    top.linkTo(ref.top)
//                    start.linkTo(barrier)
//                }
//            )
//        }
//
//    }
//}



/*

@Composable
private fun PlaidAssetsInnerListWithVerticalEndBarrier(
    modifier: Modifier = Modifier,
    texts: List<Pair<String, String>>
) {
    // Calculate the maximum width of 'Text1'
    val maxWidth = remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

//    texts.forEach { (text1, text2) ->
//        Text(
//            text = text1,
//            onTextLayout = { layoutResult ->
//                val textWidth = with(density) { layoutResult.size.width.toDp() }
//                if (textWidth > maxWidth.value) {
//                    maxWidth.value = textWidth
//                }
//            },
//            modifier = Modifier.layout { measurable, constraints ->
//                val placeable = measurable.measure(constraints)
//                layout(placeable.width, placeable.height) {
//                    placeable.placeRelative(0, 0)
//                }
//            }
//        )
//    }

    // Display the texts
    Column(modifier = modifier.fillMaxWidth()) {
        texts.forEach { (text1, text2) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = text1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = text2,
                    modifier = Modifier.padding(start = maxWidth.value)
                )
            }
        }
    }
}
*/



/*


@Composable
private fun PlaidAssetsInnerListWithVerticalEndBarrier(
    modifier: Modifier = Modifier,
    texts: List<Pair<String, String>>
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val refs = mutableListOf<ConstrainedLayoutReference>()

        texts.forEachIndexed { index, (text1, text2) ->
            val ref1 = createRef()
            Text(
                text = text1,
                modifier = Modifier.constrainAs(ref1) {
                    if (index == 0) {
                        top.linkTo(parent.top, margin = 8.dp)
                    } else {
                        top.linkTo(refs[index - 1].bottom, margin = 8.dp)
                    }
                    start.linkTo(parent.start, margin = 0.dp)
                })

            refs.add(ref1)
        }

        val barrier = createEndBarrier(*refs.toTypedArray(), margin = 8.dp)
        refs.forEachIndexed { index, ref ->
            Text(
                text = texts[index].second,
                modifier = Modifier.constrainAs(createRef()) {
                    top.linkTo(ref.top)
                    start.linkTo(barrier)
                }
            )
        }

    }
}


*/
//
//@Composable
//private fun PlaidAssetsInnerListWithVerticalEndBarrier(
//    modifier: Modifier = Modifier,
//    texts: List<Pair<String, String>>
//) {
//    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
//        val refs1 = mutableListOf<ConstrainedLayoutReference>()
//        val refs2 = mutableListOf<ConstrainedLayoutReference>()
//
//        texts.forEachIndexed { index, (text1, text2) ->
//            val ref1 = createRef()
//            val ref2 = createRef()
//
//            // Start Text: Text1
//            Text(
//                text = text1,
//                modifier = Modifier.constrainAs(ref1) {
//                    if (index == 0) {
//                        top.linkTo(parent.top, margin = 8.dp)
//                    } else {
//                        top.linkTo(refs2[index - 1].bottom, margin = 8.dp)
//                    }
//                    start.linkTo(parent.start, margin = 0.dp)
//                })
//
//            refs1.add(ref1)
//
//            val barrier = createEndBarrier(*refs1.toTypedArray(), margin = 8.dp)
//
//            // After Text: Text2
//            refs2.add(ref2)
//            Text(
//                text = text2,
//                modifier = Modifier.constrainAs(ref2) {
//                    top.linkTo(ref1.top)
//                    start.linkTo(barrier)
//                }
//            )
//
//        }
//
//    }
//}
