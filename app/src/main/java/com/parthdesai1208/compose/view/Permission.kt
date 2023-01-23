package com.parthdesai1208.compose.view


import android.Manifest
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity


@Composable
fun PermissionScreen() {
    val context = LocalContext.current
    var isDialogOpened by rememberSaveable { mutableStateOf(false) }
    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { wasGranted ->
        if (wasGranted) {
            Toast.makeText(context, "Permission approved", Toast.LENGTH_SHORT).show()
            //permission granted
        } else {
            isDialogOpened = true
            //denied permission
            //show dialog with explanation how much permission is required for the task
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { requestPermission.launch(Manifest.permission.CAMERA) }) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = null)
        }
    }, floatingActionButtonPosition = FabPosition.End, content = {

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = isDialogOpened, enter = fadeIn(), exit = fadeOut()) {
                AlertDialog(
                    //Executes when the user tries to dismiss the Dialog by clicking outside
                    // or pressing the back button
                    onDismissRequest = { },
                    title = { Text(text = "Permission required") },
                    text = { Text(text = "This is required in order for the app to take pictures") },
                    buttons = {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = {
                                isDialogOpened = false
                                //open settings screen directly
                                val intent =
                                    Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data =
                                            Uri.fromParts("package", context.packageName, null)
                                    }
                                startActivity(context, intent, null)
                            }) {
                                Text(text = "OK")
                            }
                        }
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = false, dismissOnClickOutside = false
                    )
                )
            }
        }

    })

}
