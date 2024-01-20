package com.dinesh.android.basic.text_field

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Preview(showBackground = true)
@Composable
fun MyLayoutView(){
    var inputValue by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { focusManager.clearFocus() }
            )
        },
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        MyTextField(value = inputValue, onValueChanged = { inputValue = it })
        MyTextField()
    }
}

@Composable
private fun MyTextField(value: String, onValueChanged: (String) -> Unit, label: String) {
    TextField(value = value, onValueChange = onValueChanged, label = { Text(label) }, modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth())
}


@Composable
private fun MyTextField(modifier: Modifier = Modifier, value: String, onValueChanged: (String) -> Unit, label: String = "Name", hintPlaceHolder: String = "Enter the name") {
    OutlinedTextField(value = value, onValueChange = onValueChanged, label = { Text(label) },
        placeholder = { Text(hintPlaceHolder) }, modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(modifier), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
}


@Composable
private fun MyTextField(){
    val keyboardManager = LocalSoftwareKeyboardController.current
    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(key1 = true){
        keyboardManager?.show()
        delay(100)
        focusRequester.requestFocus()
    }

    var inputValue by remember { mutableStateOf("") }

   Column(modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
       MyTextField(value = inputValue, onValueChanged = { inputValue = it }, modifier = Modifier.focusRequester(focusRequester))
   }

}