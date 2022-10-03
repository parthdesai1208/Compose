package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.parthdesai1208.compose.view.theme.RainbowOrange
import com.parthdesai1208.compose.view.theme.RainbowRed

@androidx.compose.ui.tooling.preview.Preview(showSystemUi = true)
@Composable
fun DialogCompose() {
    var isAlertDialogOpened1 by remember { mutableStateOf(false) }
    var isAlertDialogOpened2 by remember { mutableStateOf(false) }
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