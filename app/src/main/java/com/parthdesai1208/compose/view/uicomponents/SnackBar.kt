package com.parthdesai1208.compose.view.uicomponents

import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.parthdesai1208.compose.view.theme.ComposeTheme
import kotlinx.coroutines.launch

@Composable
fun SnackBarCompose() {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val customSnackBarState = remember { SnackbarHostState() }

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {

        val (column, snack,customSnack) = createRefs()
        Column(horizontalAlignment = Alignment.CenterHorizontally ,modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .constrainAs(column) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = snack.top, bias = 0f, bottomMargin = 8.dp)
            }) {
            Button(onClick = {
                scope.launch {
                    snackBarHostState.showSnackbar(message = "this is Simple short SnackBar", duration = SnackbarDuration.Short)
                }
            }){
                Text("short SnackBar")
            }
            Button(onClick = {
                scope.launch {
                    snackBarHostState.showSnackbar(message = "this is Simple Long SnackBar", duration = SnackbarDuration.Long)
                }
            }){
                Text("Long SnackBar")
            }
            Button(onClick = {
                scope.launch {
                    snackBarHostState.showSnackbar(message = "this is Indefinite SnackBar", duration = SnackbarDuration.Indefinite)
                }
            }){
                Text("Indefinite SnackBar")
            }
            Button(onClick = {
                scope.launch {
                    val snackResult =  snackBarHostState.showSnackbar(message = "SnackBar with action button", duration = SnackbarDuration.Long,
                    actionLabel = "OK")
                    if(snackResult == SnackbarResult.ActionPerformed){
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }){
                Text("SnackBar with Action button")
            }
            Button(onClick = {
                scope.launch {
                    customSnackBarState.showSnackbar(message = "Custom at middle of the screen", duration = SnackbarDuration.Long)
                }
            }){
                Text("Custom SnackBar at middle of the screen")
            }
        }

        SnackbarHost(hostState = customSnackBarState,
        modifier = Modifier.constrainAs(customSnack){
            linkTo(parent.start,parent.end)
            linkTo(parent.top,parent.bottom)
        }, snackbar = {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentSize()
                , backgroundColor = MaterialTheme.colors.onSurface
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Notifications
                            , tint = MaterialTheme.colors.surface, contentDescription = "")
                        Text(text = it.message, color = MaterialTheme.colors.surface)
                    }
                }
            })

        SnackbarHost(hostState = snackBarHostState, modifier = Modifier.constrainAs(snack){
            linkTo(parent.start,parent.end)
            bottom.linkTo(parent.bottom,8.dp)
        })
    }
}

@Preview(name = "light", showSystemUi = true)
@Composable
fun PreviewSnackBarCompose() {
    ComposeTheme {
        SnackBarCompose()
    }
}

