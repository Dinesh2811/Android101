package com.dinesh.android.compose.state.view_model.v0


import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class MyViewModel : ViewModel() {
    val textFieldValue = mutableStateOf("")
    val checkboxValue = mutableStateOf(false)
    val radioGroupValue = mutableIntStateOf(0)

    fun performAction() {
        val text = textFieldValue.value
        val checkboxValue = checkboxValue.value
        val radioGroupValue = radioGroupValue.intValue

        // Do something with the values
        // ...

        // Clear the state
//        textFieldValue.value = ""
//        checkboxValue.value = false
//        radioGroupValue.value = 0
    }
}


@Composable
fun TextFieldExample(viewModel: MyViewModel) {
    TextField(
        value = viewModel.textFieldValue.value,
        onValueChange = { viewModel.textFieldValue.value = it },
        label = { Text("Enter text") }
    )
}

@Composable
fun CheckboxExample(viewModel: MyViewModel) {
    Checkbox(
        checked = viewModel.checkboxValue.value,
        onCheckedChange = { viewModel.checkboxValue.value = it }
    )
}

@Composable
fun RadioButtonExample(viewModel: MyViewModel) {
    Column {
        RadioButton(
            selected = viewModel.radioGroupValue.intValue == 0,
            onClick = { viewModel.radioGroupValue.intValue = 0 }
        )
        RadioButton(
            selected = viewModel.radioGroupValue.intValue == 1,
            onClick = { viewModel.radioGroupValue.intValue = 1 }
        )
        RadioButton(
            selected = viewModel.radioGroupValue.intValue == 2,
            onClick = { viewModel.radioGroupValue.intValue = 2 }
        )
    }
}

@Composable
fun ButtonExample(viewModel: MyViewModel) {
    Button(
        onClick = {
            viewModel.performAction()
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Submit")
    }
}

@Composable
fun Form(viewModel: MyViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextFieldExample(viewModel)
        CheckboxExample(viewModel)
        RadioButtonExample(viewModel)
        ButtonExample(viewModel)
    }
}

@Preview
@Composable
fun PreviewForm() {
    val viewModel = viewModel<MyViewModel>()
    Form(viewModel)
}