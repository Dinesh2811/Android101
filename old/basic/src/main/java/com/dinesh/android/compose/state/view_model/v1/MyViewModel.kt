package com.dinesh.android.compose.state.view_model.v1


import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch



//class MyViewModel : ViewModel() {
//    private val _textFieldValue = MutableLiveData("")
//    val textFieldValue: LiveData<String> = _textFieldValue
//
//    private val _checkboxValue = MutableLiveData(false)
//    val checkboxValue: LiveData<Boolean> = _checkboxValue
//
//    private val _radioGroupValue = MutableLiveData(0)
//    val radioGroupValue: LiveData<Int> = _radioGroupValue
//
//    fun performAction() {
//        val text = _textFieldValue.value
//        val checkboxValue = _checkboxValue.value
//        val radioGroupValue = _radioGroupValue.value
//
//        // Do something with the values
//        // ...
//
//        // Clear the state
//        _textFieldValue.value = ""
//        _checkboxValue.value = false
//        _radioGroupValue.value = 0
//    }
//}

class MyViewModel : ViewModel() {
    private val _textFieldValue = MutableLiveData<String>("")
    val textFieldValue: LiveData<String> = _textFieldValue

    private val _checkboxValue = MutableLiveData<Boolean>()
    val checkboxValue: LiveData<Boolean> = _checkboxValue

    private val _radioGroupValue = MutableLiveData<Int>()
    val radioGroupValue: LiveData<Int> = _radioGroupValue

    init {
        _textFieldValue.value = ""
        _checkboxValue.value = false
        _radioGroupValue.value = 0
    }

    fun updateTextFieldValue(newValue: String) {
        _textFieldValue.value = newValue
    }

    fun updateCheckboxValue(newValue: Boolean) {
        _checkboxValue.value = newValue
    }

    fun updateRadioGroupValue(newValue: Int) {
        _radioGroupValue.value = newValue
    }

    fun performAction() {
        val text = textFieldValue.value ?: ""
        val checkboxValue = checkboxValue.value ?: false
        val radioGroupValue = radioGroupValue.value ?: 0

        // Do something with the values
        // ...

        // Clear the state
        updateTextFieldValue("")
        updateCheckboxValue(false)
        updateRadioGroupValue(0)
    }
}

@Composable
fun TextFieldExample(viewModel: MyViewModel) {
    val textFieldValue by viewModel.textFieldValue.observeAsState("")

    TextField(
        value = textFieldValue,
        onValueChange = { viewModel.updateTextFieldValue(it) },
        label = { Text("Enter text") }
    )
}

@Composable
fun CheckboxExample(viewModel: MyViewModel) {
    val checkboxValue by viewModel.checkboxValue.observeAsState(false)

    Checkbox(
        checked = checkboxValue,
        onCheckedChange = { viewModel.updateCheckboxValue(it) }
    )
}

@Composable
fun RadioButtonExample(viewModel: MyViewModel) {
    val radioGroupValue by viewModel.radioGroupValue.observeAsState(0)

    Column {
        RadioButton(
            selected = radioGroupValue == 0,
            onClick = { viewModel.updateRadioGroupValue(0) }
        )
        RadioButton(
            selected = radioGroupValue == 1,
            onClick = { viewModel.updateRadioGroupValue(1) }
        )
        RadioButton(
            selected = radioGroupValue == 2,
            onClick = { viewModel.updateRadioGroupValue(2) }
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