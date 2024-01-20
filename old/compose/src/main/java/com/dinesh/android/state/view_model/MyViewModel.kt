package com.dinesh.android.state.view_model

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.*
import androidx.compose.ui.text.input.KeyboardType

private val TAG = "log_" + MyViewModel::class.java.name.split(MyViewModel::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]


@Preview(showBackground = true)
@Composable
fun MyLayoutView(viewModel: MyViewModel = viewModel<MyViewModel>()) {
    MyUI()
}

@Preview(showBackground = true)
@Composable
fun MyUI() {
    val viewModel = viewModel<MyViewModel>()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text(text = viewModel.textFieldValue.collectAsState().value)
        MyTextFieldPreview()
        MyButtonViewPreview()

//        Text(text = viewModel.textFieldValue.collectAsState().value)
//        MyTextField(value = viewModel.textFieldValue.collectAsState().value, onValueChanged = { viewModel.updateTextFieldValue(it) })
//        MyButtonView(onClick = { viewModel.updateTextValue(viewModel.textFieldValue.value) }) { Text(text = "Submit") }
    }
}

class MyViewModel : ViewModel() {
    private val _textValue: MutableStateFlow<String> = MutableStateFlow("")
    val textValue: StateFlow<String> = _textValue.asStateFlow()

    fun updateTextValue(newValue: String) {
        viewModelScope.launch { _textValue.emit(newValue) }
    }

    private val _textFieldValue: MutableStateFlow<String> = MutableStateFlow("")
    val textFieldValue: StateFlow<String> = _textFieldValue.asStateFlow()
    fun updateTextFieldValue(newValue: String) {
        viewModelScope.launch { _textFieldValue.emit(newValue) }
    }

    private val _textFieldValue1: MutableStateFlow<String> = MutableStateFlow("")
    val textFieldValue1: StateFlow<String> = _textFieldValue1.asStateFlow()
    fun updateTextFieldValue1(newValue: String) {
        viewModelScope.launch { _textFieldValue1.emit(newValue) }
    }

    fun onSubmitBtnClicked() {
        Log.e(TAG, "onSubmitBtnClicked: ${textFieldValue.value}")
        updateTextValue(textFieldValue.value)
    }
}

@Preview(showBackground = true)
@Composable
private fun MyTextFieldPreview() {
    val viewModel = viewModel<MyViewModel>()
    MyTextField(value = viewModel.textFieldValue.collectAsState().value, onValueChanged = { viewModel.updateTextFieldValue(it) })
}

@Preview(showBackground = true)
@Composable
private fun MyButtonViewPreview() {
    val viewModel = viewModel<MyViewModel>()
    MyButtonView(onClick = { viewModel.updateTextValue(viewModel.textFieldValue.value) }) { Text(text = "Submit") }
}

@Composable
private fun MyTextField(modifier: Modifier = Modifier, value: String, onValueChanged: (String) -> Unit, label: String = "Name", hintPlaceHolder: String = "Enter the name") {
    OutlinedTextField(value = value, onValueChange = onValueChanged, label = { Text(label) }, placeholder = { Text(hintPlaceHolder) }, modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .then(modifier), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
}

@Composable
fun MyButtonView(modifier: Modifier = Modifier, onClick: () -> Unit, content: @Composable RowScope.() -> Unit = { Text(text = "Button") }) {
    Button(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .then(modifier), onClick = onClick, content = content)
}