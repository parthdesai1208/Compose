@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp

@Composable
fun EditTextCompose() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        SimpleFilledTextFieldSample()
        DividerTextCompose()
        SimpleOutlinedTextFieldSample()
        DividerTextCompose()
        SingleLineTextField()
        DividerTextCompose()
        MaxLineTextField()
        DividerTextCompose()
        TextStyleTextField()
        DividerTextCompose()
        CapitalizeAllCharTextField()
        DividerTextCompose()
        CapitalizeFirstCharOfWordTextField()
        DividerTextCompose()
        CapitalizeFirstCharOfSentenceTextField()
        DividerTextCompose()
        AutoCorrectTextField()
        DividerTextCompose()
        KeyBoardTypeTextField(keyboardType = KeyboardType.Password, "Password")
        DividerTextCompose()
        KeyBoardTypeTextField(keyboardType = KeyboardType.NumberPassword, "Number Password")
        DividerTextCompose()
        KeyBoardTypeTextField(keyboardType = KeyboardType.Number, "Number")
        DividerTextCompose()
        KeyBoardTypeTextField(keyboardType = KeyboardType.Phone, "Phone")
        DividerTextCompose()
        KeyBoardTypeTextField(keyboardType = KeyboardType.Email, "Email")
        DividerTextCompose()
        ImeOptionTextField(imeAction = ImeAction.Go, "Go ImeAction")
        DividerTextCompose()
        ImeOptionTextField(imeAction = ImeAction.Search, "Search ImeAction")
        DividerTextCompose()
        ImeOptionTextField(imeAction = ImeAction.Send, "Send ImeAction")
        DividerTextCompose()
        ImeOptionTextField(imeAction = ImeAction.Previous, "Previous ImeAction")
        DividerTextCompose()
        ImeOptionTextField(imeAction = ImeAction.Next, "Next ImeAction")
        DividerTextCompose()
        ImeOptionTextField(imeAction = ImeAction.Done, "Done ImeAction")
        DividerTextCompose()
    }
}

@Composable
fun SimpleFilledTextFieldSample() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Filled EditText", color = MaterialTheme.colors.onSurface) },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
}

@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Outlined EditText", color = MaterialTheme.colors.onSurface) },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
}

@Composable
fun SingleLineTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text("Single Line", color = MaterialTheme.colors.onSurface) },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
}

@Composable
fun MaxLineTextField() {
    var text by rememberSaveable { mutableStateOf("First line\nSecond line, scroll for 3rd line\nthird line") }

    TextField(
        value = text,
        maxLines = 2,
        onValueChange = { text = it },
        label = { Text("Max Line:2", color = MaterialTheme.colors.onSurface) },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
}

@Composable
fun TextStyleTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text("Max Line", color = MaterialTheme.colors.onSurface) },
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun CapitalizeAllCharTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text("Capitalize all characters", color = MaterialTheme.colors.onSurface) },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
    )
}

@Composable
fun CapitalizeFirstCharOfWordTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = {
            Text(
                "Capitalize first char of every word",
                color = MaterialTheme.colors.onSurface
            )
        },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
    )
}

@Composable
fun CapitalizeFirstCharOfSentenceTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = {
            Text(
                "Capitalize first char of every sentence",
                color = MaterialTheme.colors.onSurface
            )
        },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
    )
}

@Composable
fun AutoCorrectTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text("AutoCorrect true by default", color = MaterialTheme.colors.onSurface) },
        keyboardOptions = KeyboardOptions(autoCorrect = true)
    )
}

@Composable
fun KeyBoardTypeTextField(keyboardType: KeyboardType, text: String) {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text) },
        visualTransformation = if (text.lowercase()
                .contains("pass")
        ) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImeOptionTextField(imeAction: ImeAction, text: String) {
    var text1 by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TextField(
        value = text1,
        modifier = Modifier.focusRequester(focusRequester),
        onValueChange = { text1 = it },
        label = { Text(text) },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onGo = {
                Toast.makeText(context, "Go...", Toast.LENGTH_SHORT).show()
                keyboardController?.hide()
            },
            onDone = {
                Toast.makeText(context, "Done...", Toast.LENGTH_SHORT).show()
                keyboardController?.hide()
                focusManager.clearFocus(true)
            },
            onSearch = {
                Toast.makeText(context, "Search...", Toast.LENGTH_SHORT).show()
                keyboardController?.hide()
            },
            onSend = {
                Toast.makeText(context, "Send...", Toast.LENGTH_SHORT).show()
                keyboardController?.hide()
            },
            onPrevious = {
                Toast.makeText(context, "Previous...", Toast.LENGTH_SHORT).show()
                keyboardController?.hide()
            },
            onNext = {
                Toast.makeText(context, "Next...", Toast.LENGTH_SHORT).show()
                keyboardController?.hide()
            })
    )
}