package com.dinesh.android

import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.cardview.widget.CardView
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch

//
//@Composable
//fun PlaidAssetsInnerListWithVerticalEndBarrier(
//    modifier: Modifier = Modifier,
//    texts: List<Pair<String, String>>
//) {
//    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
//        val refs1 = mutableListOf<ConstrainedLayoutReference>()
//        val refs2 = mutableListOf<ConstrainedLayoutReference>()
//        val refs = mutableListOf<ConstrainedLayoutReference>()
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
////                        top.linkTo(refs2[index - 1].bottom, margin = 8.dp)
//                        top.linkTo(refs2[index - 1].bottom)
//                    }
//                    start.linkTo(parent.start, margin = 0.dp)
//                })
//
//            refs1.add(ref1)
//
//            val barrier = createEndBarrier(*refs.toTypedArray(), margin = 8.dp)
////            val barrier = createEndBarrier(*refs1.toTypedArray(), margin = 8.dp)
//
//            refs.forEachIndexed { index, ref ->
//                Text(
//                    text = texts[index].second,
//                    modifier = Modifier.constrainAs(createRef()) {
//                        top.linkTo(ref.top)
//                        start.linkTo(barrier)
//                    }
//                )
//            }
//
////            // After Text: Text2
////            Text(
////                text = text2,
////                modifier = Modifier.constrainAs(ref2) {
////                    top.linkTo(ref1.top)
////                    start.linkTo(barrier)
////                }
////            )
////
//            refs2.add(ref2)
//
//        }
//        val barrier = createEndBarrier(*refs.toTypedArray(), margin = 8.dp)
////            val barrier = createEndBarrier(*refs1.toTypedArray(), margin = 8.dp)
//
//        refs.forEachIndexed { index, ref ->
//            Text(
//                text = texts[index].second,
//                modifier = Modifier.constrainAs(createRef()) {
//                    top.linkTo(ref.top)
//                    start.linkTo(barrier)
//                }
//            )
//        }
//    }
//}
//
