package com.dinesh.compose.ui.component.toggle

import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.annotation.StringRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.graphics.*
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import kotlinx.coroutines.*
import androidx.navigation.compose.*
import androidx.navigation.*
import com.dinesh.xml.R


/**
 * # Switch
 *
 *  [alexzh](https://alexzh.com/jetpack-compose-switch/)
 *  [composables](https://www.composables.com/material3/switch/examples)
 *  [semicolonspace](https://semicolonspace.com/jetpack-compose-custom-switch-buttons/)
 */


@Preview(showBackground = true)
@Composable
fun MySwitchPreview() {
    var switchCheckedState by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        MySimpleSwitch()
        MySimpleSwitchWithColor()
        MySimpleSwitchWithIcon()
        MySimpleSwitchWithText(checked = switchCheckedState, onCheckedChange = { switchCheckedState = it })
        MyCustomSwitch1()
        MyCustomSwitch2()
        MyCustomSwitch3()

        MyCustomSwitchPreview()
    }

}


@Composable
fun MySimpleSwitch() {
    var switchCheckedState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Switch(
            modifier = Modifier.semantics { contentDescription = "Demo" },
            checked = switchCheckedState,
            onCheckedChange = { switchCheckedState = it }
        )
    }
}

@Composable
fun MySimpleSwitchWithColor() {
    var switchCheckedState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Switch(
            checked = switchCheckedState,
            onCheckedChange = { switchCheckedState = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Green,
                checkedTrackColor = Color.LightGray,
                uncheckedThumbColor = Color.Red,
                uncheckedTrackColor = Color.LightGray,
                disabledCheckedThumbColor = Color.Green.copy(alpha = ContentAlpha.disabled),
                disabledCheckedTrackColor = Color.LightGray.copy(alpha = ContentAlpha.disabled),
                disabledUncheckedThumbColor = Color.Red.copy(alpha = ContentAlpha.disabled),
                disabledUncheckedTrackColor = Color.LightGray.copy(alpha = ContentAlpha.disabled),
            )
        )
    }
}

@Composable
fun MySimpleSwitchWithIcon() {
    var switchCheckedState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Switch(
            checked = switchCheckedState,
            onCheckedChange = { switchCheckedState = it },
            thumbContent = {
                Icon(
                    imageVector = if (switchCheckedState) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
private fun MySimpleSwitchWithText(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    @StringRes title: Int = R.string.app_name,
    @StringRes description: Int = R.string.app_name,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = onCheckedChange
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1.0f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val contentAlpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled

            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                modifier = Modifier.alpha(contentAlpha)
            )
            Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.alpha(contentAlpha)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled
        )
    }
}





@Composable
fun MyCustomSwitch1(
    width: Dp = 72.dp,
    height: Dp = 40.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFFe0e0e0),
    gapBetweenThumbAndTrackEdge: Dp = 8.dp,
    borderWidth: Dp = 4.dp,
    cornerSize: Int = 50,
    iconInnerPadding: Dp = 4.dp,
    thumbSize: Dp = 24.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    var switchOn by remember { mutableStateOf(true) }
    val alignment by animateAlignmentAsState(if (switchOn) 1f else -1f)

    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(
                width = borderWidth,
                color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                shape = RoundedCornerShape(percent = cornerSize)
            ).clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                switchOn = !switchOn
            },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .padding(
                    start = gapBetweenThumbAndTrackEdge,
                    end = gapBetweenThumbAndTrackEdge
                )
                .fillMaxSize(),
            contentAlignment = alignment
        ) {

            Icon(
                imageVector = if (switchOn) Icons.Filled.Done else Icons.Filled.Close,
                contentDescription = if (switchOn) "Enabled" else "Disabled",
                modifier = Modifier
                    .size(size = thumbSize)
                    .background(
                        color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                        shape = CircleShape
                    )
                    .padding(all = iconInnerPadding),
                tint = Color.White
            )
        }
    }

    Spacer(modifier = Modifier.height(height = 28.dp))

//    Text(text = if (switchOn) "ON" else "OFF")
}

@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue, label = "")
    return remember { derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) } }
}


@Composable
fun MyCustomSwitch2(
    scale: Float = 2f,
    width: Dp = 36.dp,
    height: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFFe0e0e0),
    gapBetweenThumbAndTrackEdge: Dp = 4.dp
) {

    val switchON = remember {
        mutableStateOf(true)
    }

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (switchON.value)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }, label = ""
    )

    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        switchON.value = !switchON.value
                    }
                )
            }
    ) {
        // Track
        drawRoundRect(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),
            style = Stroke(width = strokeWidth.toPx())
        )

        // Thumb
        drawCircle(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }

    Spacer(modifier = Modifier.height(height = 28.dp))

//    Text(text = if (switchON.value) "ON" else "OFF")
}



@Composable
fun MyCustomSwitch3(
    scale: Float = 2f,
    width: Dp = 36.dp,
    height: Dp = 20.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFFe0e0e0),
    thumbColor: Color = Color.White,
    gapBetweenThumbAndTrackEdge: Dp = 4.dp
) {

    val switchON = remember {
        mutableStateOf(true)
    }

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move the thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (switchON.value)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }, label = ""
    )

    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        switchON.value = !switchON.value
                    }
                )
            }
    ) {

        // Track
        drawRoundRect(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx())
        )

        // Thumb
        drawCircle(
            color = thumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )

    }

    Spacer(modifier = Modifier.height(height = 28.dp))

//    Text(text = if (switchON.value) "ON" else "OFF")
}



@Preview(showBackground = true)
@Composable
fun MyCustomSwitchPreview() {
    var isSwitchOn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SwitchWithText(
            isSwitchOn = isSwitchOn,
            onSwitchChanged = { newState ->
                isSwitchOn = newState
            }
        )
    }
}

@Composable
fun SwitchWithText(
    isSwitchOn: Boolean,
    onSwitchChanged: (Boolean) -> Unit
) {
    val contentAlpha by animateFloatAsState(if (isSwitchOn) ContentAlpha.high else ContentAlpha.disabled, label = "")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.Text(
            text = if (isSwitchOn) "ON" else "OFF",
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .alpha(contentAlpha)
        )
        MyCustomSwitch(
            isSwitchOn = isSwitchOn,
            onSwitchChanged = onSwitchChanged
        )
    }
}

@Composable
fun MyCustomSwitch(
    isSwitchOn: Boolean,
    onSwitchChanged: (Boolean) -> Unit,
    width: Dp = 72.dp,
    height: Dp = 40.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFFe0e0e0),
    gapBetweenThumbAndTrackEdge: Dp = 8.dp,
    borderWidth: Dp = 4.dp,
    cornerSize: Int = 50,
    iconInnerPadding: Dp = 4.dp,
    thumbSize: Dp = 24.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val alignment by animateAlignmentAsState(if (isSwitchOn) 1f else -1f)

    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(
                width = borderWidth,
                color = if (isSwitchOn) checkedTrackColor else uncheckedTrackColor,
                shape = RoundedCornerShape(percent = cornerSize)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onSwitchChanged(!isSwitchOn)
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = gapBetweenThumbAndTrackEdge,
                    end = gapBetweenThumbAndTrackEdge
                )
                .fillMaxSize(),
            contentAlignment = alignment
        ) {
            androidx.compose.material.Icon(
                imageVector = if (isSwitchOn) Icons.Filled.Done else Icons.Filled.Close,
                contentDescription = if (isSwitchOn) "Enabled" else "Disabled",
                modifier = Modifier
                    .size(size = thumbSize)
                    .background(
                        color = if (isSwitchOn) checkedTrackColor else uncheckedTrackColor,
                        shape = CircleShape
                    )
                    .padding(all = iconInnerPadding),
                tint = Color.White
            )
        }
    }
}