package com.dinesh.android.state.state_holder


import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.*

@Preview
@Composable
fun PreviewForm() {
    val stateHolder = StateHolder()
    Form(stateHolder)
}

// Composable function to display the form
@Composable
fun Form(stateHolder: StateHolder) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextFieldExample(stateHolder)
        CheckboxExample(stateHolder)
        RadioButtonExample(stateHolder)
        ButtonExample(stateHolder)
    }
}

// State holder class
class StateHolder {
    var textFieldValue by mutableStateOf("")
    var checkboxValue by mutableStateOf(false)
    var radioGroupValue by mutableIntStateOf(0)
}

// Composable function for TextField
@Composable
fun TextFieldExample(stateHolder: StateHolder) {
    TextField(
        value = stateHolder.textFieldValue,
        onValueChange = { stateHolder.textFieldValue = it },
        label = { Text("Enter text") }
    )
}

// Composable function for Checkbox
@Composable
fun CheckboxExample(stateHolder: StateHolder) {
    Checkbox(
        checked = stateHolder.checkboxValue,
        onCheckedChange = { stateHolder.checkboxValue = it }
    )
}

// Composable function for RadioButton
@Composable
fun RadioButtonExample(stateHolder: StateHolder) {
    Column {
        RadioButton(
            selected = stateHolder.radioGroupValue == 0,
            onClick = { stateHolder.radioGroupValue = 0 }
        )
        RadioButton(
            selected = stateHolder.radioGroupValue == 1,
            onClick = { stateHolder.radioGroupValue = 1 }
        )
        RadioButton(
            selected = stateHolder.radioGroupValue == 2,
            onClick = { stateHolder.radioGroupValue = 2 }
        )
    }
}

// Composable function for Button
@Composable
fun ButtonExample(stateHolder: StateHolder) {
    Button(
        onClick = {
            // Perform action using the state values
            val text = stateHolder.textFieldValue
            val checkboxValue = stateHolder.checkboxValue
            val radioGroupValue = stateHolder.radioGroupValue

            // Do something with the values
            // ...

            // Clear the state
            stateHolder.textFieldValue = ""
            stateHolder.checkboxValue = false
            stateHolder.radioGroupValue = 0
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Submit")
    }
}
