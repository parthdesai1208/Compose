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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.navigation.PermissionListingScreenPath


enum class PermissionListingEnumType(
    val buttonTitle: Int, val func: @Composable (NavHostController) -> Unit
) {
    SinglePermission(R.string.singlePermission, { SinglePermissionScreen(it) }), MultiplePermission(
        R.string.multiplePermission,
        { MultiplePermissionScreen(it) }),
}

@Composable
fun PermissionListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: PermissionListingEnumType
    ) {
        Button(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .padding(8.dp),
            onClick = { navController.navigate(PermissionListingScreenPath(title.buttonTitle)) }) {
            Text(stringResource(id = title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    BuildTopBarWithScreen(title = stringResource(id = R.string.permission_samples), screen = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            enumValues<PermissionListingEnumType>().forEach {
                MyButton(it)
            }
        }
    }, onBackIconClick = {
        navController.popBackStack()
    })
}

@Composable
fun ChildPermissionScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<PermissionListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

//region single permission screen
@Composable
fun SinglePermissionScreen(navHostController: NavHostController) {
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

    BuildTopBarWithScreen(title = stringResource(id = R.string.singlePermission), screen = {
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
                AnimatedVisibility(
                    visible = isDialogOpened, enter = fadeIn(), exit = fadeOut()
                ) {
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
                                            data = Uri.fromParts(
                                                "package", context.packageName, null
                                            )
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
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}
//endregion


//region multiple permission screen
@Composable
fun MultiplePermissionScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    var isDialogOpened by rememberSaveable { mutableStateOf(false) }
    var requiredPermissionName by rememberSaveable { mutableStateOf("") }

    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { wasGranted ->
        wasGranted.forEach {
            if (it.value) {
                //permission granted
                /* Toast.makeText(
                     context,
                     "${it.key.split(".").last()} Permission approved",
                     Toast.LENGTH_SHORT
                 ).show()*/
            } else {
                //denied permission
                //show dialog with explanation how much permission is required for the task
                requiredPermissionName = it.key.split(".").last()
                isDialogOpened = true
                return@rememberLauncherForActivityResult
            }
        }
        if (!wasGranted.values.contains(false)) {
            Toast.makeText(
                context, "Done with Permissions, now proceed with task", Toast.LENGTH_SHORT
            ).show()
        }
    }
    BuildTopBarWithScreen(title = stringResource(id = R.string.multiplePermission), screen = {
        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {
                requestPermission.launch(
                    arrayOf(
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }) {
                Icon(imageVector = Icons.Default.Camera, contentDescription = null)
            }
        }, floatingActionButtonPosition = FabPosition.End, content = {
            Surface(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = isDialogOpened, enter = fadeIn(), exit = fadeOut()
                ) {
                    AlertDialog(
                        //Executes when the user tries to dismiss the Dialog by clicking outside
                        // or pressing the back button
                        onDismissRequest = { }, title = {
                            Text(
                                text = "$requiredPermissionName Permission required",
                                style = MaterialTheme.typography.body1
                            )
                        }, text = {
                            Text(
                                text = "This is required in order for the app to work properly",
                                style = MaterialTheme.typography.body1
                            )
                        }, buttons = {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(onClick = {
                                    isDialogOpened = false
                                    //open settings screen directly
                                    val intent =
                                        Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data = Uri.fromParts(
                                                "package", context.packageName, null
                                            )
                                        }
                                    startActivity(context, intent, null)
                                }) {
                                    Text(text = "OK", style = MaterialTheme.typography.button)
                                }
                            }
                        }, properties = DialogProperties(
                            dismissOnBackPress = false, dismissOnClickOutside = false
                        )
                    )
                }
            }
        })
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}
//endregion