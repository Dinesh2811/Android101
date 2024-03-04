package com.dinesh.android.basic.radio_button

import android.os.Build
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dinesh.android.R
import kotlin.math.log

@Preview(showBackground = true)
@Composable
fun CustomRadioButtonIndicator_WithIconToggleButton() {
    MaterialTheme {
        val selectedValue = remember { mutableStateOf("") }
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        Column(Modifier.padding(8.dp)) {
            Text(text = "Selected value: ${selectedValue.value.ifEmpty { "NONE" }}")
            items.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = (selectedValue.value == item),
                            onClick = { selectedValue.value = item },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                ) {
                    IconToggleButton(
                        checked = selectedValue.value == item,
                        onCheckedChange = { selectedValue.value = item },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (selectedValue.value == item) {
                                Icons.Outlined.CheckCircleOutline
                            } else {
                                Icons.Outlined.Circle
                            },
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = item,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MySelectThemeLayout() {
    var themeTypeValue by remember { mutableStateOf("Default") }
    var selectedValue by remember { mutableStateOf(R.style.Theme_Material3) }

    val themeMap = mapOf(
        "Light" to getLightThemePairs(),
        "Dark" to getDarkThemePairs(),
        "Default" to getDefaultThemePairs()
    )

    MySelectThemePreview(
        themeTypeValue = themeTypeValue,
        selectedValue = selectedValue,
        onThemeTypeChange = { newThemeType ->
            themeTypeValue = newThemeType
            selectedValue = themeMap[newThemeType]?.firstOrNull()?.first ?: selectedValue
        },
        onSelectedValueChange = { newSelectedValue ->
            selectedValue = newSelectedValue
            themeTypeValue = themeMap.entries.firstOrNull { it.value.any { pair -> pair.first == newSelectedValue } }?.key ?: themeTypeValue
        }
    )
}

@Composable
fun MySelectThemePreview(
    themeTypeValue: String,
    selectedValue: Int,
    onThemeTypeChange: (String) -> Unit,
    onSelectedValueChange: (Int) -> Unit
) {
    MaterialTheme {
        val themeTypes = listOf("Default", "Light", "Dark")
        val themePairs = getThemePairs(themeTypes)
        Column(Modifier.padding(8.dp)) {
            themePairs.forEach { (themeType, pairs) ->
                ThemeTypeRow(themeType, themeTypeValue, onThemeTypeChange)
                pairs.forEach { (theme, displayValue) ->
                    ThemePairRow(theme, displayValue, selectedValue, onSelectedValueChange)
                }
            }
        }
    }
}

@Composable
fun ThemeTypeRow(themeType: String, themeTypeValue: String, onThemeTypeChange: (String) -> Unit) {
    SelectableRow(
        selected = (themeTypeValue == themeType),
        onClick = { onThemeTypeChange(themeType) },
        text = themeType,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun ThemePairRow(theme: Int, displayValue: String, selectedValue: Int, onSelectedValueChange: (Int) -> Unit) {
    SelectableRow(
        selected = (selectedValue == theme),
        onClick = { onSelectedValueChange(theme) },
        text = displayValue,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
fun SelectableRow(selected: Boolean, onClick: () -> Unit, text: String, style: TextStyle, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .selectable(selected = selected, onClick = onClick, role = Role.RadioButton)
            .padding(vertical = 8.dp)
            .padding(top = 4.dp)
    ) {
        IconToggleButton(
            checked = selected,
            onCheckedChange = { onClick() },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = if (selected) {
                    Icons.Outlined.CheckCircleOutline
                } else {
                    Icons.Outlined.Circle
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            style = style
        )
    }
}

@Composable
private fun getThemePairs(themeTypes: List<String>): List<Pair<String, List<Pair<Int, String>>>> {
    return themeTypes.map { themeType -> themeType to getThemePairsByType(themeType) }
}

private fun getThemePairsByType(themeType: String): List<Pair<Int, String>> {
    return when (themeType) {
        "Light" -> getLightThemePairs()
        "Dark" -> getDarkThemePairs()
        else -> getDefaultThemePairs()
    }
}

private fun getLightThemePairs(): List<Pair<Int, String>> {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
        listOf(
            Pair(R.style.Theme_Dynamic_Light, "Light"),
//            Pair(R.style.Theme_Dynamic_PureWhite, "PureWhite"),
//            Pair(R.style.Theme_Dynamic_PureWhite_V2, "PureWhite v2"),
        )
    } else {
        listOf(
            Pair(R.style.Theme_Light, "Light"),
//            Pair(R.style.Theme_PureWhite, "PureWhite"),
//            Pair(R.style.Theme_PureWhite_V2, "PureWhite v2"),
        )
    }
}

private fun getDarkThemePairs(): List<Pair<Int, String>> {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
        listOf(
            Pair(R.style.Theme_Dynamic_Dark, "Dark"),
            Pair(R.style.Theme_Dynamic_PureBlack, "PureBlack"),
            Pair(R.style.Theme_Dynamic_PureBlack_V2, "PureBlack v2"),
        )
    } else {
        listOf(
            Pair(R.style.Theme_Dark, "Dark"),
            Pair(R.style.Theme_PureBlack, "PureBlack"),
            Pair(R.style.Theme_PureBlack_V2, "PureBlack v2"),
        )
    }
}

private fun getDefaultThemePairs(): List<Pair<Int, String>> {
    return listOf(
        Pair(R.style.Theme_Material3, "Default"),
//        Pair(R.style.Theme_OldTheme, "Old Theme"),
    )
}



@Preview(showBackground = true)
@Composable
fun TestingLayout() {
    var themeTypeValue by remember { mutableStateOf("Default") }
    var selectedValue by remember { mutableIntStateOf(R.style.Theme_Material3) }

    Testing(
        themeTypeValue = themeTypeValue,
        selectedValue = selectedValue,
        onThemeTypeChange = { newThemeType ->
            themeTypeValue = newThemeType
        },
        onSelectedValueChange = { newSelectedValue ->
            selectedValue = newSelectedValue
        }
    )

}

@Composable
fun Testing(
    themeTypeValue: String,
    selectedValue: Int,
    onThemeTypeChange: (String) -> Unit,
    onSelectedValueChange: (Int) -> Unit
) {
    MaterialTheme {
        val selectedThemeList = remember { mutableListOf<SelectedTheme>() }
        val context = LocalContext.current

            LazyColumn() {

                items(myThemes) { myTheme ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .selectable(
                                selected = (themeTypeValue == myTheme.themeType),
                                onClick = {
                                    selectedThemeList.clear()
                                    selectedThemeList.addAll(myTheme.selectedTheme)
                                    onThemeTypeChange(myTheme.themeType)
                                },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 8.dp)
                            .padding(top = 4.dp)
                    ) {
                        IconToggleButton(
                            checked = themeTypeValue == myTheme.themeType,
                            onCheckedChange = {
                                selectedThemeList.clear()
                                selectedThemeList.addAll(myTheme.selectedTheme)
                                onThemeTypeChange(myTheme.themeType)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (themeTypeValue == myTheme.themeType) {
                                    Icons.Outlined.CheckCircleOutline
                                } else {
                                    Icons.Outlined.Circle
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = myTheme.themeType,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }


                    selectedThemeList.forEach {  selectedTheme ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .selectable(
                                        selected = (selectedValue == selectedTheme.theme),
                                        onClick = { onSelectedValueChange(selectedTheme.theme) },
                                        role = Role.RadioButton
                                    )
                                    .padding(8.dp)
                                    .padding(start = 16.dp)
                            ) {
                                IconToggleButton(
                                    checked = selectedValue == selectedTheme.theme,
                                    onCheckedChange = { onSelectedValueChange(selectedTheme.theme) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = if (selectedValue == selectedTheme.theme) {
                                            Icons.Outlined.CheckCircleOutline
                                        } else {
                                            Icons.Outlined.Circle
                                        },
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Text(
                                    text = selectedTheme.themeDisplayName,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }

                }

            }
        }

}

val myThemes: List<MyTheme> = listOf(
    MyTheme("Default", listOf(
        SelectedTheme(R.style.Theme_Material3, "Default"),
        SelectedTheme(R.style.Theme_Material3, "Old Theme"),
    )), MyTheme("Light", listOf(
        SelectedTheme(R.style.Theme_Dynamic_Light, "Light"),
        SelectedTheme(R.style.Theme_Dynamic_Light, "PureWhite"),
        SelectedTheme(R.style.Theme_Dynamic_Light, "PureWhite v2"),
    )), MyTheme("Dark", listOf(
        SelectedTheme(R.style.Theme_Dynamic_Dark, "Dark"),
        SelectedTheme(R.style.Theme_Dynamic_Dark, "PureBlack"),
        SelectedTheme(R.style.Theme_Dynamic_Dark, "PureBlack v2"),
    ))
)

data class MyTheme(
    val themeType: String,
    val selectedTheme: List<SelectedTheme>
)

data class SelectedTheme(
    val theme: Int,
    val themeDisplayName: String
)