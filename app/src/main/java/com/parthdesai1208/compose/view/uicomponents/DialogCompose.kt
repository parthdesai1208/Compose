package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.parthdesai1208.compose.view.theme.RainbowOrange
import com.parthdesai1208.compose.view.theme.RainbowRed

@androidx.compose.ui.tooling.preview.Preview(showSystemUi = true)
@Composable
fun DialogCompose() {
    var isAlertDialogOpened1 by remember { mutableStateOf(false) }
    var isAlertDialogOpened2 by remember { mutableStateOf(false) }
    var isAlertDialogOpened3 by remember { mutableStateOf(false) }
    var isAlertDialogOpened4 by remember { mutableStateOf(false) }
    var isAlertDialogOpened5 by remember { mutableStateOf(false) }
    var isAlertDialogOpened6 by remember { mutableStateOf(false) }
    var isPopUpOpened1 by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { isAlertDialogOpened1 = true }) {
                Text(text = "Alert Dialog")
            }
            if (isAlertDialogOpened1) {
                CommonAlertDialog(onDismissRequest = {
                    Toast.makeText(context, "onDismissRequest called", Toast.LENGTH_SHORT)
                        .show()
                }, buttons = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(onClick = {
                                Toast.makeText(context, "you choose 'no'", Toast.LENGTH_SHORT)
                                    .show()
                                isAlertDialogOpened1 = false
                            }) {
                                Text(text = "No")
                            }
                            TextButton(onClick = {
                                Toast.makeText(context, "you choose 'yes'", Toast.LENGTH_SHORT)
                                    .show()
                                isAlertDialogOpened1 = false
                            }) {
                                Text(text = "Yes")
                            }
                        }
                    }
                })
            }
            Button(onClick = { isAlertDialogOpened2 = true }) {
                Text(text = "Alert Dialog with custom color")
            }
            if (isAlertDialogOpened2) {
                CommonAlertDialog(onDismissRequest = { isAlertDialogOpened2 = false }, buttons = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(onClick = { isAlertDialogOpened2 = false }) {
                                Text(text = "No")
                            }
                            TextButton(onClick = { isAlertDialogOpened2 = false }) {
                                Text(text = "Yes")
                            }
                        }
                    }
                }, backgroundColor = RainbowRed, contentColor = RainbowOrange)
            }
            Button(onClick = { isAlertDialogOpened3 = true }) {
                Text(text = "Alert Dialog dismissOnBackPress off")
            }
            if (isAlertDialogOpened3) {
                CommonAlertDialog(onDismissRequest = { isAlertDialogOpened3 = false }, buttons = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(onClick = { isAlertDialogOpened3 = false }) {
                                Text(text = "No")
                            }
                            TextButton(onClick = { isAlertDialogOpened3 = false }) {
                                Text(text = "Yes")
                            }
                        }
                    }
                }, properties = DialogProperties(dismissOnBackPress = false))
            }
            Button(onClick = { isAlertDialogOpened4 = true }) {
                Text(text = "Alert Dialog dismissOnClickOutside off")
            }
            if (isAlertDialogOpened4) {
                CommonAlertDialog(onDismissRequest = { isAlertDialogOpened4 = false }, buttons = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(onClick = { isAlertDialogOpened4 = false }) {
                                Text(text = "No")
                            }
                            TextButton(onClick = { isAlertDialogOpened4 = false }) {
                                Text(text = "Yes")
                            }
                        }
                    }
                }, properties = DialogProperties(dismissOnClickOutside = false))
            }
            Button(onClick = { isAlertDialogOpened5 = true }) {
                Text(text = "Alert Dialog securePolicy off")
            }
            if (isAlertDialogOpened5) {
                CommonAlertDialog(onDismissRequest = { isAlertDialogOpened5 = false }, buttons = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { isAlertDialogOpened5 = false }) {
                            Text(text = "ok")
                        }
                    }
                }, properties = DialogProperties(securePolicy = SecureFlagPolicy.SecureOff),
                    bodyText = "you can take screenshot of this alertDialog"
                )
            }
            Button(onClick = { isAlertDialogOpened6 = true }) {
                Text(text = "Alert Dialog securePolicy on")
            }
            if (isAlertDialogOpened6) {
                CommonAlertDialog(onDismissRequest = { isAlertDialogOpened6 = false }, buttons = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { isAlertDialogOpened6 = false }) {
                            Text(text = "ok")
                        }
                    }
                }, properties = DialogProperties(securePolicy = SecureFlagPolicy.SecureOn),
                    bodyText = "you can not take screenshot of this alertDialog"
                )
            }
            Button(onClick = { isPopUpOpened1 = true }) {
                Text(text = "PopUp")
            }
            if (isPopUpOpened1) {
                Popup(
                    alignment = Alignment.Center,
                    onDismissRequest = { isPopUpOpened1 = false },
                    properties = PopupProperties(dismissOnBackPress = false)
                ) {
                    Row(
                        modifier = Modifier
                            .border(
                                width = 1.dp, color = MaterialTheme.colors.onSurface,
                                shape = RoundedCornerShape(5.dp)
                            )
                    ) {
                        TextButton(onClick = { isPopUpOpened1 = false }) {
                            Text(
                                text = "hello I am PopUp\nUsed for small things like\nCut/Copy/Paste banner",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommonAlertDialog(
    onDismissRequest: () -> Unit,
    title: String = "⚠️ Deletion!!!",
    bodyText: String = "Are you sure you want to delete your account?",
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
    buttons: @Composable () -> Unit,
) {
    AlertDialog(
        //Executes when the user tries to dismiss the Dialog by clicking outside
        // or pressing the back button
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = { Text(text = bodyText) },
        buttons = buttons,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        properties = properties
    )
}