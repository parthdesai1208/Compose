package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.RainbowOrange
import com.parthdesai1208.compose.view.theme.RainbowRed

@Composable
fun DialogCompose(navHostController: NavHostController) {
    var isAlertDialogOpened1 by remember { mutableStateOf(false) }
    var isAlertDialogOpened2 by remember { mutableStateOf(false) }
    var isAlertDialogOpened3 by remember { mutableStateOf(false) }
    var isAlertDialogOpened4 by remember { mutableStateOf(false) }
    var isAlertDialogOpened5 by remember { mutableStateOf(false) }
    var isAlertDialogOpened6 by remember { mutableStateOf(false) }
    var isPopUpOpened1 by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = context.getString(R.string.dialogSample),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { isAlertDialogOpened1 = true }) {
                    Text(text = stringResource(R.string.alert_dialog))
                }
                if (isAlertDialogOpened1) {
                    CommonAlertDialog(onDismissRequest = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.ondismissrequest_called), Toast.LENGTH_SHORT
                        )
                            .show()
                    }, buttons = {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                TextButton(onClick = {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.you_choose_no),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    isAlertDialogOpened1 = false
                                }) {
                                    Text(text = stringResource(R.string.no))
                                }
                                TextButton(onClick = {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.you_choose_yes),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    isAlertDialogOpened1 = false
                                }) {
                                    Text(text = stringResource(R.string.yes))
                                }
                            }
                        }
                    })
                }
                Button(onClick = { isAlertDialogOpened2 = true }) {
                    Text(text = stringResource(R.string.alert_dialog_with_custom_color))
                }
                if (isAlertDialogOpened2) {
                    CommonAlertDialog(
                        onDismissRequest = { isAlertDialogOpened2 = false },
                        buttons = {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    TextButton(onClick = { isAlertDialogOpened2 = false }) {
                                        Text(text = stringResource(id = R.string.no))
                                    }
                                    TextButton(onClick = { isAlertDialogOpened2 = false }) {
                                        Text(text = stringResource(id = R.string.yes))
                                    }
                                }
                            }
                        },
                        backgroundColor = RainbowRed,
                        contentColor = RainbowOrange
                    )
                }
                Button(onClick = { isAlertDialogOpened3 = true }) {
                    Text(text = stringResource(R.string.alert_dialog_dismissonbackpress_off))
                }
                if (isAlertDialogOpened3) {
                    CommonAlertDialog(
                        onDismissRequest = { isAlertDialogOpened3 = false },
                        buttons = {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    TextButton(onClick = { isAlertDialogOpened3 = false }) {
                                        Text(text = stringResource(id = R.string.no))
                                    }
                                    TextButton(onClick = { isAlertDialogOpened3 = false }) {
                                        Text(text = stringResource(id = R.string.yes))
                                    }
                                }
                            }
                        },
                        properties = DialogProperties(dismissOnBackPress = false)
                    )
                }
                Button(onClick = { isAlertDialogOpened4 = true }) {
                    Text(text = stringResource(R.string.alert_dialog_dismissonclickoutside_off))
                }
                if (isAlertDialogOpened4) {
                    CommonAlertDialog(
                        onDismissRequest = { isAlertDialogOpened4 = false },
                        buttons = {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    TextButton(onClick = { isAlertDialogOpened4 = false }) {
                                        Text(text = stringResource(id = R.string.no))
                                    }
                                    TextButton(onClick = { isAlertDialogOpened4 = false }) {
                                        Text(text = stringResource(id = R.string.yes))
                                    }
                                }
                            }
                        },
                        properties = DialogProperties(dismissOnClickOutside = false)
                    )
                }
                Button(onClick = { isAlertDialogOpened5 = true }) {
                    Text(text = stringResource(R.string.alert_dialog_securepolicy_off))
                }
                if (isAlertDialogOpened5) {
                    CommonAlertDialog(
                        onDismissRequest = { isAlertDialogOpened5 = false },
                        buttons = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextButton(onClick = { isAlertDialogOpened5 = false }) {
                                    Text(text = stringResource(R.string.ok))
                                }
                            }
                        },
                        properties = DialogProperties(securePolicy = SecureFlagPolicy.SecureOff),
                        bodyText = stringResource(R.string.you_can_take_screenshot_of_this_alertdialog)
                    )
                }
                Button(onClick = { isAlertDialogOpened6 = true }) {
                    Text(text = stringResource(R.string.alert_dialog_securepolicy_on))
                }
                if (isAlertDialogOpened6) {
                    CommonAlertDialog(
                        onDismissRequest = { isAlertDialogOpened6 = false },
                        buttons = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextButton(onClick = { isAlertDialogOpened6 = false }) {
                                    Text(text = stringResource(id = R.string.ok))
                                }
                            }
                        },
                        properties = DialogProperties(securePolicy = SecureFlagPolicy.SecureOn),
                        bodyText = stringResource(R.string.you_can_not_take_screenshot_of_this_alertdialog)
                    )
                }
                Button(onClick = { isPopUpOpened1 = true }) {
                    Text(text = stringResource(R.string.popup))
                }
                if (isPopUpOpened1) {
                    Popup(
                        alignment = Alignment.BottomCenter,
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
                                    text = stringResource(R.string.popup_content_text),
                                    textAlign = TextAlign.Center
                                )
                            }
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
    title: String = stringResource(R.string.common_alert_dialog_title),
    bodyText: String = stringResource(R.string.common_alert_dialog_body_text),
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