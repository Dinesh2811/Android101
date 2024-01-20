package com.dinesh.android.compose.state.view_model.v2


import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _textFieldValue = MutableStateFlow("")
    val textFieldValue: StateFlow<String> = _textFieldValue.asStateFlow()

    private val _checkboxValue = MutableStateFlow(false)
    val checkboxValue: StateFlow<Boolean> = _checkboxValue.asStateFlow()

    private val _radioGroupValue = MutableStateFlow(0)
    val radioGroupValue: StateFlow<Int> = _radioGroupValue.asStateFlow()

    fun onTextFieldValueChanged(value: String) {
        viewModelScope.launch { _textFieldValue.emit(value) }
    }

    fun onCheckboxValueChanged(value: Boolean) {
        viewModelScope.launch { _checkboxValue.emit(value) }
    }

    fun onRadioGroupValueChanged(value: Int) {
        viewModelScope.launch { _radioGroupValue.emit(value) }
    }

    fun performAction() {
        val text = _textFieldValue.value
        val checkboxValue = _checkboxValue.value
        val radioGroupValue = _radioGroupValue.value

        // Do something with the values
        // ...

        // Clear the state
        _textFieldValue.value = ""
        _checkboxValue.value = false
        _radioGroupValue.value = 0
    }
}

@Composable
private fun TextFieldExample(viewModel: MyViewModel) {
    val textFieldValue by viewModel.textFieldValue.collectAsState()

    TextField(
        value = textFieldValue,
        onValueChange = { viewModel.onTextFieldValueChanged(it) },
        label = { Text("Enter text") }
    )
}

@Composable
private fun CheckboxExample(viewModel: MyViewModel) {
    val checkboxValue by viewModel.checkboxValue.collectAsState()
    Checkbox(
        checked = checkboxValue,
        onCheckedChange = { viewModel.onCheckboxValueChanged(it) }
    )
}

@Composable
private fun RadioButtonExample(viewModel: MyViewModel) {
    val radioGroupValue by viewModel.radioGroupValue.collectAsState()
    Column {
        RadioButton(
            selected = radioGroupValue == 0,
            onClick = { viewModel.onRadioGroupValueChanged(0) }
        )
        RadioButton(
            selected = radioGroupValue == 1,
            onClick = { viewModel.onRadioGroupValueChanged(1) }
        )
        RadioButton(
            selected = radioGroupValue == 2,
            onClick = { viewModel.onRadioGroupValueChanged(2) }
        )
    }
}

@Composable
private fun ButtonExample(viewModel: MyViewModel) {
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
private fun Form(viewModel: MyViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextFieldExample(viewModel)
        CheckboxExample(viewModel)
        RadioButtonExample(viewModel)
        ButtonExample(viewModel)
    }
}

@Preview
@Composable
private fun PreviewForm() {
    val viewModel = viewModel<MyViewModel>()
    Form(viewModel)
}