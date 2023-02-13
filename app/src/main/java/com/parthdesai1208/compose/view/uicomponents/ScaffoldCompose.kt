package com.parthdesai1208.compose.view.uicomponents

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScaffoldCompose() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var topBarState by rememberSaveable { mutableStateOf(true) }
    var bottomBarState by rememberSaveable { mutableStateOf(true) }
    var fabButtonState by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(scaffoldState = scaffoldState, content = {
            ContentForScaffold(padding = it, topBarClick = {
                coroutineScope.launch {
                    topBarState = !topBarState
                }
            }, bottomBarClick = {
                coroutineScope.launch {
                    bottomBarState = !bottomBarState
                }
            }, fabButtonClick = {
                coroutineScope.launch {
                    fabButtonState = !fabButtonState
                }
            })
        }, topBar = {
            TopBar(state = topBarState) {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        }, bottomBar = {
            BottomBar(state = bottomBarState)
        }, floatingActionButtonPosition = FabPosition.End, floatingActionButton = {
            if (fabButtonState) FloatingActionButton(onFabClick = {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("FAB Action Button Clicked")
                }
            })
        }, drawerContent = {
            DrawerContent()
        })
    }

    //Note: whatever boolean we pass in "enabled" if its true then block of code will execute otherwise system back press will execute
    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
        coroutineScope.launch {
            scaffoldState.drawerState.close()
        }
    }
}

@Composable
fun ContentForScaffold(
    padding: PaddingValues,
    topBarClick: () -> Unit,
    bottomBarClick: () -> Unit,
    fabButtonClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        Text(text = "Top Start", modifier = Modifier.align(Alignment.TopStart))
        Text(text = "Top End", modifier = Modifier.align(Alignment.TopEnd))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = topBarClick
            ) {
                Text("Top Bar")
            }
            Button(
                onClick = fabButtonClick
            ) {
                Text("Floating Action Button")
            }
            Button(
                onClick = bottomBarClick
            ) {
                Text("Bottom Bar")
            }
        }
        Text(text = "Bottom Start", modifier = Modifier.align(Alignment.BottomStart))
        Text(text = "Bottom End", modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun TopBar(state: Boolean, iconClick: () -> Unit) {

    AnimatedVisibility(visible = state,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(title = { Text("Top Bar") }, navigationIcon = {
                IconButton(
                    onClick = iconClick
                ) {
                    Icon(
                        Icons.Filled.Menu, contentDescription = "Localized description"
                    )
                }
            })
        })
}

@Composable
fun BottomBar(state: Boolean) {
    AnimatedVisibility(visible = state,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar {
                Text(
                    "Bottom Bar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            }
        })
}

@Composable
fun FloatingActionButton(onFabClick: () -> Unit) {
    FloatingActionButton(onClick = onFabClick) {
        Text("FAB")
    }
}

@Composable
fun DrawerContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Drawer Content")
    }
}
