package com.dinesh.android.constraint_layout.v0

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import com.dinesh.android.R


@Composable
fun PlaidAssetsList(
    plaidAssets: List<GetPlaidAssetsResponse.Data>,
    onItemClick: (GetPlaidAssetsResponse.Data) -> Unit
) {
    LazyColumn {
        items(plaidAssets) { plaidAsset ->
            PlaidAssetItem(plaidAsset = plaidAsset, onItemClick = onItemClick)
        }
    }
}

@Composable
fun PlaidAssetItem(
    plaidAsset: GetPlaidAssetsResponse.Data,
    onItemClick: (GetPlaidAssetsResponse.Data) -> Unit
) {
    val rows = listOf(
        Pair("Account Name", " :     ${plaidAsset.assetAccount.first().name}"),
        Pair("Type", " :     ${plaidAsset.assetAccount.first().type}"),
        Pair("Sub-type", " :     ${plaidAsset.assetAccount.first().subtype}"),
        Pair("Available balance", " :     ${plaidAsset.assetAccount.first().balanceAvailable}"),
        Pair("Current balance", " :     ${plaidAsset.assetAccount.first().balanceCurrent}"),
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(plaidAsset) }
            .padding(16.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = plaidAsset.plaidItem.insName, fontWeight = FontWeight.Bold)
            VerticalEndBarrierExample(
                texts = rows
            )
        }
    }
}

//@Composable
//fun PlaidAssetsScreen(viewModel: AssetsViewModel = hiltViewModel()) {
//    val plaidAssets by viewModel.getPlaidAssetsData.collectAsState()
//
//    plaidAssets?.data?.let {
//        PlaidAssetsList(plaidAssets = it) { plaidAsset ->
//            // Handle item click here
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PlaidAssetsScreenPreview() {
    val plaidAssets = hardcodedPlaidAssetsResponse

    plaidAssets?.data?.let {
        PlaidAssetsList(plaidAssets = it) { plaidAsset ->
            // Handle item click here
        }
    }
}


@Composable
fun VerticalEndBarrierExample(
    modifier: Modifier = Modifier,
    texts: List<Pair<String, String>>
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val refs = mutableListOf<ConstrainedLayoutReference>()

        texts.forEachIndexed { index, (text1, text2) ->
            val ref1 = createRef()
            MyTextView(
                text = text1,
                modifier = Modifier.constrainAs(ref1) {
                    if (index == 0) {
                        top.linkTo(parent.top, margin = 16.dp)
                    } else {
                        top.linkTo(refs[index - 1].bottom, margin = 8.dp)
                    }
                    start.linkTo(parent.start, margin = 0.dp)
                })

            refs.add(ref1)
        }

        val barrier = createEndBarrier(*refs.toTypedArray(), margin = 8.dp)
        refs.forEachIndexed { index, ref ->
            MyTextView(
                text = texts[index].second,
                modifier = Modifier.constrainAs(createRef()) {
                    top.linkTo(ref.top)
                    start.linkTo(barrier)
                }
            )
        }

    }
}


@Composable
fun MyTextView(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF646464),
    fontWeight: FontWeight = FontWeight.W500,
    fontSize: TextUnit = 14.sp,
    fontResId: Int? = R.font.poppins_regular,
    onClick: (() -> Unit)? = null
) {

    val customModifier = modifier.wrapContentSize().defaultMinSize(minHeight = 24.dp)
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


@Composable
fun MyMaterialCardView(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val customModifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clickable { onClick?.invoke() }
        .padding(12.dp)

    Card(
        modifier = customModifier,
        shape = RoundedCornerShape(12.dp),
        content = content,
        elevation = 12.dp
    )
}

