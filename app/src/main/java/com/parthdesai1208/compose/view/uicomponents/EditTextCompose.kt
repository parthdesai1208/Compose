@file:Suppress("OPT_IN_IS_NOT_ENABLED")
@file:OptIn(ExperimentalTextApi::class)

package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.utils.RainbowColors

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
        KeyBoardTypeTextFieldPass(keyboardType = KeyboardType.Password, "Password")
        DividerTextCompose()
        KeyBoardTypeTextFieldPass(keyboardType = KeyboardType.NumberPassword, "Number Password")
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
        GradientTextField()
        DividerTextCompose()
        NoLeadingZeroes()
        DividerTextCompose()
        PhoneNumberWithDash()
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
        label = { Text("Text style", color = MaterialTheme.colors.onSurface) },
        textStyle = TextStyle(color = Color.Cyan.copy(alpha = .5f), fontWeight = FontWeight.Bold),
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
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
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
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
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
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
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
        keyboardOptions = KeyboardOptions(autoCorrect = true),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
}

@Composable
fun KeyBoardTypeTextFieldPass(keyboardType: KeyboardType, text: String) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        }
    )
}

@Composable
fun KeyBoardTypeTextField(keyboardType: KeyboardType, text: String) {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
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
            }),
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
}

@Composable
fun GradientTextField() {

    var text by remember { mutableStateOf("") }
    val brush = remember {
        Brush.linearGradient(
            colors = RainbowColors
        )
    }

    TextField(
        value = text,
        label = { Text(text = "Gradient Text Field") },
        onValueChange = { text = it },
        textStyle = TextStyle(brush = brush)
    )
}

@Composable
fun NoLeadingZeroes() {
    var input by rememberSaveable { mutableStateOf("") }
    TextField(
        value = input,
        onValueChange = { newText ->
            //trimStart = Returns a string with matching expression removed from it.
            input = newText.trimStart { it == '0' }
        },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneNumberWithDash() {
    var input by rememberSaveable { mutableStateOf("") }
    val maxChar = 11
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = input,
            onValueChange = { newText ->
                input = if (newText.length <= maxChar) newText.trim() else input.trim()
            },
            label = {
                Text(
                    text = "Phone number with special format",
                    color = MaterialTheme.colors.onSurface
                )
            },
            visualTransformation = PhoneNumberTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                    if (input.isNotBlank()) Toast.makeText(context, input, Toast.LENGTH_SHORT)
                        .show()
                }
            ),
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
        )
        Text(
            text = "${input.length} / $maxChar", color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.End, style = MaterialTheme.typography.caption,
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(end = 50.dp)
        )
    }
}

class PhoneNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return phoneNumFilter(text)
    }
}

fun phoneNumFilter(text: AnnotatedString): TransformedText {

    // +X(XXX)_XXX_XX_XX
    val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
    var out = ""
    for (i in trimmed.indices) {
        if (i == 0) out += "+"
        if (i == 1) out += "("
        out += trimmed[i]
        if (i == 3) out += ") "
        if (i == 6 || i == 8) out += " "

    }
    // +X(XXX)_XXX_XX_XX
    // +1(234) 567 89 11
    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            //offset = means cursor position
            //execute when user starts typing from left to right
            if (offset <= 0) return offset
            if (offset <= 1) return offset + 1 //user type 1st num,cursor will go to (1+1)2nd position
            if (offset <= 3) return offset + 2 //user type 2nd num,cursor will go to (2+2)4th position
            //user type 3rd num,cursor will go to (3+2)5th position
            if (offset <= 7) return offset + 4 //user type 4th num,cursor will go to (4+4)8th position
            //user type 5th num,cursor will go to (5+4)9th position
            //user type 6th num,cursor will go to (6+4)10th position
            //user type 7th num,cursor will go to (7+4)11th position
            if (offset <= 9) return offset + 5 //user type 8th num,cursor will go to (8+5)13th position
            //user type 9th num,cursor will go to (9+5)14th position
            if (offset <= 11) return offset + 6 //user type 10th num,cursor will go to (10+6)16th position
            //user type 11th num,cursor will go to (11+6)17th position
            return 17                           //else 17th position

        }

        // +X(XXX)_XXX_XX_XX
        // +1(234) 567 89 11
        override fun transformedToOriginal(offset: Int): Int {
            //execute when user erase the input from right to left
            if (offset <= 0) return offset
            if (offset <= 2) return offset - 1  //user erased from 2nd pos,cursor will go to (2-1)1st position
            if (offset <= 7) return offset - 2  //user erased from 4th pos,cursor will go to (4-2)2nd position
            //user erased from 5th pos,cursor will go to (5-2)3rd position
            if (offset <= 12) return offset - 4 //user erased from 8th pos,cursor will go to (8-4)4th position
            //user erased from 9th pos,cursor will go to (9-4)5th position
            //user erased from 10th pos,cursor will go to (10-4)6th position
            //user erased from 11th pos,cursor will go to (11-4)7th position
            if (offset <= 15) return offset - 5 //user erased from 13th pos,cursor will go to (13-5)8th position
            //user erased from 14th pos,cursor will go to (14-5)9th position
            if (offset <= 18) return offset - 6 //user erased from 16th pos,cursor will go to (16-6)10th position
            //user erased from 17th pos,cursor will go to (17-6)11th position
            return 11
        }
    }

    return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
}