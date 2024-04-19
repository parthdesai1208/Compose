package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.utils.ToolBarWithIconAndTitle
import com.parthdesai1208.compose.view.theme.ComposeTheme
import kotlinx.coroutines.launch

@Composable
fun SnackBarCompose(navHostController: NavHostController) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val customSnackBarState = remember { SnackbarHostState() }

    Surface {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (column, snack, customSnack, toolbar) = createRefs()
            ToolBarWithIconAndTitle(
                screenTitle = stringResource(id = R.string.snackbar),
                onBackArrowClick = { navHostController.popBackStack() },
                modifier = Modifier.constrainAs(toolbar) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top)
                }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .wrapContentSize()
                .constrainAs(column) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(
                        top = toolbar.bottom,
                        bottom = parent.bottom,
                        bias = 0f,
                        bottomMargin = 8.dp
                    )
                }) {
                Button(onClick = {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "this is Simple short SnackBar",
                            duration = SnackbarDuration.Short
                        )
                    }
                }) {
                    Text("short SnackBar")
                }
                Button(onClick = {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "this is Simple Long SnackBar",
                            duration = SnackbarDuration.Long
                        )
                    }
                }) {
                    Text("Long SnackBar")
                }
                Button(onClick = {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "this is Indefinite SnackBar",
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }) {
                    Text("Indefinite SnackBar")
                }
                Button(onClick = {
                    scope.launch {
                        val snackResult = snackBarHostState.showSnackbar(
                            message = "SnackBar with action button",
                            duration = SnackbarDuration.Long,
                            actionLabel = "OK"
                        )
                        if (snackResult == SnackbarResult.ActionPerformed) {
                            snackBarHostState.currentSnackbarData?.dismiss()
                        }
                    }
                }) {
                    Text("SnackBar with Action button")
                }
                Button(onClick = {
                    scope.launch {
                        customSnackBarState.showSnackbar(
                            message = "Custom at middle of the screen",
                            duration = SnackbarDuration.Long
                        )
                    }
                }) {
                    Text("Custom SnackBar at middle of the screen")
                }
            }

            SnackbarHost(hostState = customSnackBarState,
                modifier = Modifier.constrainAs(customSnack) {
                    linkTo(parent.start, parent.end)
                    linkTo(parent.top, parent.bottom)
                }, snackbar = {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentSize(), backgroundColor = MaterialTheme.colors.onSurface
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                tint = MaterialTheme.colors.surface,
                                contentDescription = ""
                            )
                            Text(text = it.message, color = MaterialTheme.colors.surface)
                        }
                    }
                })

            SnackbarHost(hostState = snackBarHostState, modifier = Modifier.constrainAs(snack) {
                linkTo(parent.start, parent.end)
                bottom.linkTo(parent.bottom, 8.dp)
            })
        }
    }
}

@Phone
@Composable
fun PreviewSnackBarCompose() {
    ComposeTheme {
        SnackBarCompose(rememberNavController())
    }
}

