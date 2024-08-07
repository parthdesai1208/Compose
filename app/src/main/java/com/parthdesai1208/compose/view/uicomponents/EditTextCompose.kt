package com.parthdesai1208.compose.view.uicomponents

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.utils.RainbowColors
import com.parthdesai1208.compose.utils.ToolBarWithIconAndTitle
import com.parthdesai1208.compose.utils.autofill
import com.parthdesai1208.compose.view.theme.GreyDark
import com.parthdesai1208.compose.view.theme.GreyLight
import com.parthdesai1208.compose.view.theme.red1000
import com.parthdesai1208.compose.viewmodel.ManageStateOnTextChangeViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Phone
@Composable
fun EditTextComposePreview() {
    EditTextCompose(rememberNavController(), vm = androidx.lifecycle.viewmodel.compose.viewModel())
}

@Composable
fun EditTextCompose(navHostController: NavHostController, vm: ManageStateOnTextChangeViewModel) {
    Surface {
        Column {
            ToolBarWithIconAndTitle(
                screenTitle = stringResource(id = R.string.edittext),
                onBackArrowClick = { navHostController.popBackStack() }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                BasicTextField2Compose()
                DividerTextCompose()
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
                KeyBoardTypeTextFieldPass(
                    keyboardType = KeyboardType.NumberPassword,
                    "Number Password"
                )
                DividerTextCompose()
                KeyBoardTypeTextField(keyboardType = KeyboardType.Number, "Number")
                DividerTextCompose()
                KeyBoardTypeTextField(keyboardType = KeyboardType.Phone, "Phone")
                DividerTextCompose()
                KeyBoardTypeTextField(keyboardType = KeyboardType.Email, "Email")
                DividerTextCompose()
                GainFocusEditTextCompose()
                DividerTextCompose()
                ImeOptionTextField(imeAction = ImeAction.Go, "Go ImeAction")
                DividerTextCompose()
                ImeOptionTextField(imeAction = ImeAction.Search, "Search ImeAction")
                DividerTextCompose()
                ImeOptionTextField(imeAction = ImeAction.Send, "Send ImeAction")
                DividerTextCompose()
                ImeOptionTextField(imeAction = ImeAction.Next, "Next ImeAction")
                DividerTextCompose()
                ImeOptionTextField(imeAction = ImeAction.Previous, "Previous ImeAction")
                DividerTextCompose()
                ImeOptionTextField(imeAction = ImeAction.Done, "Done ImeAction")
                DividerTextCompose()
                FocusOrderTextField()
                DividerTextCompose()
                GradientTextField()
                DividerTextCompose()
                NoLeadingZeroes()
                DividerTextCompose()
                PhoneNumberWithDash()
                DividerTextCompose()
                ErrorTextField()
                DividerTextCompose()
                ManageStateOnTextChange(vm)
                DividerTextCompose()
                OTPInputField()
                DividerTextCompose()
            }
        }
    }
}

@Composable
fun FocusOrderTextField() {
    val focusManager = LocalFocusManager.current
    val (a, b, c) = FocusRequester.createRefs()

    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp, alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(0.90f)) {
            TextField(
                label = { Text(text = "focus:a, next:c") },
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .focusRequester(a)
                    .focusProperties { next = c },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) })
            )
            TextField(
                label = { Text(text = "focus:b, last") },
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .focusRequester(b)
                    .focusProperties {
                        previous = c
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
            TextField(
                label = { Text(text = "focus:c, next:b") },
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .focusRequester(c)
                    .focusProperties {
                        previous = a
                        next = b
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
            )
        }
        Column(modifier = Modifier.weight(0.10f)) {
            Button(
                contentPadding = PaddingValues(0.dp),
                onClick = { focusManager.moveFocus(FocusDirection.Next) }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowRight, contentDescription = null)
            }
            Button(
                contentPadding = PaddingValues(0.dp),
                onClick = { focusManager.moveFocus(FocusDirection.Previous) }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowLeft, contentDescription = null)
            }
        }
    }
}

@Composable
fun GainFocusEditTextCompose() {
    Column {
        val focusRequester = remember { FocusRequester() }
        var value by rememberSaveable { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        TextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = value,
            onValueChange = {
                value = it
            },
            label = { Text("you will get focus here") },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Button(onClick = {
            focusRequester.requestFocus()
        }) {
            Text("Gain focus")
        }
    }
}

@Composable
fun BasicTextField2Compose() {
    val textState = rememberTextFieldState()
    val textState2 = rememberTextFieldState(initialText = stringResource(id = R.string.lorem_ipsum))
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "New BasicTextField2")
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            state = textState,
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "ScrollState using BasicTextField2")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                state = textState2,
                scrollState = scrollState,
                lineLimits = TextFieldLineLimits.MultiLine(3, 7),
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                modifier = Modifier.weight(0.95f),
            )
            Spacer(modifier = Modifier.width(5.dp))

            val direction = LayoutDirection.Rtl
            CompositionLocalProvider(LocalLayoutDirection provides direction) {
                Slider(
                    modifier = Modifier
                        .graphicsLayer {
                            rotationZ = 270f
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(
                                Constraints(
                                    minWidth = constraints.minHeight,
                                    maxWidth = constraints.maxHeight,
                                    minHeight = constraints.minWidth,
                                    maxHeight = constraints.maxHeight,
                                )
                            )
                            layout(placeable.height, placeable.width) {
                                placeable.place(-placeable.width, 0)
                            }
                        }
                        .weight(0.05f),
                    value = scrollState.value.toFloat(),
                    onValueChange = {
                        scope.launch { scrollState.scrollTo(it.roundToInt()) }
                    },
                    valueRange = 0f..scrollState.maxValue.toFloat()
                )
            }
        }

    }

}

@Composable
fun SimpleFilledTextFieldSample() {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(value = text, onValueChange = { text = it }, label = { Text("Filled EditText") })
}

@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(value = text,
        onValueChange = { text = it },
        label = { Text("Outlined EditText") })
}

@Composable
fun SingleLineTextField() {
    val textState3 = rememberTextFieldState()
    BasicTextField(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(0.3f),
                RoundedCornerShape(8.dp)
            ),
        state = textState3,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (textState3.text.isEmpty()) {
                    Text(
                        text = "Single Line",
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
                } else {
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun MaxLineTextField() {
    var text by rememberSaveable { mutableStateOf("First line\nSecond line, scroll for 3rd line\nthird line") }

    TextField(value = text,
        maxLines = 2,
        onValueChange = { text = it },
        label = { Text("Max Line:2") })
}

@Composable
fun TextStyleTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text("Text style") },
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
        label = { Text("Capitalize all characters") },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
    )
}

@Composable
fun CapitalizeFirstCharOfWordTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(value = text, singleLine = true, onValueChange = { text = it }, label = {
        Text("Capitalize first char of every word")
    }, keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
    )
}

@Composable
fun CapitalizeFirstCharOfSentenceTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(value = text, singleLine = true, onValueChange = { text = it }, label = {
        Text("Capitalize first char of every sentence")
    }, keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
    )
}

@Composable
fun AutoCorrectTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        singleLine = true,
        onValueChange = { text = it },
        label = { Text("AutoCorrect true by default") },
        keyboardOptions = KeyboardOptions(autoCorrectEnabled = true)
    )
}

@Composable
fun KeyBoardTypeTextFieldPass(keyboardType: KeyboardType, text: String) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(value = password,
        onValueChange = { password = it },
        label = { Text(text) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        })
}

@Composable
fun KeyBoardTypeTextField(keyboardType: KeyboardType, text: String) {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

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
        keyboardActions = KeyboardActions(onGo = {
            Toast.makeText(context, "Go...", Toast.LENGTH_SHORT).show()
            keyboardController?.hide()
        }, onDone = {
            Toast.makeText(context, "Done...", Toast.LENGTH_SHORT).show()
            keyboardController?.hide()
            focusManager.clearFocus(true)
        }, onSearch = {
            Toast.makeText(context, "Search...", Toast.LENGTH_SHORT).show()
            keyboardController?.hide()
        }, onSend = {
            Toast.makeText(context, "Send...", Toast.LENGTH_SHORT).show()
            keyboardController?.hide()
        }, onPrevious = {
            Toast.makeText(context, "Previous...", Toast.LENGTH_SHORT).show()
            keyboardController?.hide()
            focusManager.moveFocus(focusDirection = FocusDirection.Up)
        }, onNext = {
            Toast.makeText(context, "Next...", Toast.LENGTH_SHORT).show()
            keyboardController?.hide()
            focusManager.moveFocus(focusDirection = FocusDirection.Down)
        })
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
    TextField(value = input,
        label = { Text(text = "No Leading Zero") },
        onValueChange = { newText ->
            //trimStart = Returns a string with matching expression removed from it.
            input = newText.trimStart { it == '0' }
        })
}

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
        TextField(value = input,
            onValueChange = { newText ->
                input = if (newText.length <= maxChar) newText.trim() else input.trim()
            },
            label = {
                Text(text = "Phone number with special format")
            },
            visualTransformation = PhoneNumberTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus(true)
                if (input.isNotBlank()) Toast.makeText(context, input, Toast.LENGTH_SHORT).show()
            }),
            leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null) })
        Text(
            text = "${input.length} / $maxChar",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
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

/*enum class CursorSelectionBehaviour {
    START, END, SELECT_ALL
}*/

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun ErrorTextField() {
    var text by rememberSaveable { mutableStateOf("") }
    var isErrorTextFieldVisible by remember { mutableStateOf(false) }
    val invalidInput = text.count() < 5 || '@' !in text
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutine = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()
    val view = LocalView.current
    var kbOpened: () -> Unit = {}
    var kbClosed: () -> Unit = {}

    //region detect keyboard is open/closed?
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                kbOpened()
            } else {
                kbClosed()
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }
    kbOpened = {
        if (isErrorTextFieldVisible) {
            coroutine.launch {
                bringIntoViewRequester.bringIntoView()
            }
        }
    }
    kbClosed = {

    }
    //endregion
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .autofill(autofillTypes = listOf(AutofillType.EmailAddress), onFill = {
                    text = it
                })
                .onFocusChanged {
                    isErrorTextFieldVisible = it.isFocused
                },
            label = {
                val label = if (invalidInput) "Email*" else "Email"
                Text(label)
            },
            isError = invalidInput,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(onSend = {
                if (!invalidInput && text.isNotBlank()) Toast.makeText(
                    context,
                    "Email sent to $text id",
                    Toast.LENGTH_SHORT
                ).show()
                keyboardController?.hide()
                focusManager.clearFocus(true)
            })
        )

        val textColor = if (invalidInput) {
            MaterialTheme.colors.error
        } else {
            MaterialTheme.colors.onSurface
        }
        Text(
            textAlign = TextAlign.Center,
            text = if (invalidInput) "Requires '@' and at least 5 symbols" else "Helper message",
            style = MaterialTheme.typography.caption.copy(color = textColor),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .bringIntoViewRequester(bringIntoViewRequester)
        ) {
            Text(
                text = "when you click on Above editText i will jump up with keyboard",
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ManageStateOnTextChange(vm: ManageStateOnTextChangeViewModel) {

    OutlinedTextField(value = vm.userName, onValueChange = {
        vm.updateUserName(it)
    }, label = { Text(text = "Manage State on TextChange") })
    val userNameHasError by vm.isUserNameHasError.collectAsStateWithLifecycle()
    if (userNameHasError) {
        Text(
            text = "Username not available. Please choose a different one.",
            color = red1000,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    } else {
        if (vm.userName.isNotBlank()) {
            Text(text = "Checking...", color = MaterialTheme.colors.onSurface)
        }
    }
}

//region otp input field
@Composable
fun OTPInputField() {
    var otpValue by rememberSaveable { mutableStateOf("") }

    OtpTextField(otpText = otpValue, onOtpTextChange = { value, _ ->
        otpValue = value
    })
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    BasicTextField(modifier = modifier, value = otpText, onValueChange = {
        if (it.length <= otpCount) {
            onOtpTextChange.invoke(it, it.length == otpCount)
        }
    }, keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done
    ), decorationBox = {  //allows to add decorations around text field
        Row(horizontalArrangement = Arrangement.Center) {
            repeat(otpCount) { index ->
                CharView(
                    index = index, text = otpText
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    })
}

@Composable
private fun CharView(
    index: Int, text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused) GreyDark else GreyLight,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.h4,
        color = if (isFocused) {
            GreyLight
        } else {
            GreyDark
        },
        textAlign = TextAlign.Center
    )
}
//endregion