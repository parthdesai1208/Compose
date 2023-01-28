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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.R


enum class PermissionListingEnumType(val buttonTitle: Int, val func: @Composable () -> Unit) {
    SinglePermission(R.string.singlepermission, { SinglePermissionScreen() })
}

object PermissionListingDestinations {
    const val PERMISSION_LISTING_MAIN_SCREEN = "PERMISSION_LISTING_MAIN_SCREEN"
    const val PERMISSION_LISTING_SCREEN_ROUTE_PREFIX = "PERMISSION_LISTING_SCREEN_ROUTE_PREFIX"
    const val PERMISSION_LISTING_SCREEN_ROUTE_POSTFIX = "PERMISSION_LISTING_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun PermissionListNavGraph(startDestination: String = PermissionListingDestinations.PERMISSION_LISTING_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = PermissionListingDestinations.PERMISSION_LISTING_MAIN_SCREEN) {
            PermissionListingScreen(navController = navController)
        }

        composable(
            route = "${PermissionListingDestinations.PERMISSION_LISTING_SCREEN_ROUTE_PREFIX}/{${PermissionListingDestinations.PERMISSION_LISTING_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(PermissionListingDestinations.PERMISSION_LISTING_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildPermissionScreen(
                arguments.getString(PermissionListingDestinations.PERMISSION_LISTING_SCREEN_ROUTE_POSTFIX)
            )
        }
    }
}

@Composable
fun PermissionListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: PermissionListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${PermissionListingDestinations.PERMISSION_LISTING_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(stringResource(id = title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Text(
                text = "Networking Samples",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
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
        }
    }
}

@Composable
fun ChildPermissionScreen(onClickButtonTitle: String?) {
    enumValues<PermissionListingEnumType>().first { it.buttonTitle.toString() == onClickButtonTitle }.func.invoke()
}

//region single permission screen
@Composable
fun SinglePermissionScreen() {
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
//endregion