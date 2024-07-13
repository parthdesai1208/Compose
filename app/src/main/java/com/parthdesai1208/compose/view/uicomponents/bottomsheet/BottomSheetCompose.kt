@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.parthdesai1208.compose.view.uicomponents.bottomsheet

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.AddBackIconToScreen
import com.parthdesai1208.compose.view.navigation.BottomsheetListingScreenPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//region BottomSheet Listing Screen
enum class BottomSheetListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    SimpleBottomSheet(R.string.simple_bs_using_scaffold, { SimpleBottomSheet(it) }),
    BottomSheetWithPeekHeight(
        R.string.bs_scaffold_with_peek_height,
        { BottomSheetWithPeekHeight(it) }),
    BottomSheetWithAnimation(R.string.bs_scaffold_with_animation, { BottomSheetWithAnimation(it) }),

    //    BottomSheetUsingExtension(R.string.bs_using_extension, { BottomSheetUsingExtension(it) }),
    GoogleMapsLikeBottomSheet(R.string.google_maps_bs, { GoogleMapsLikeBottomSheet(it) }),
}

@Composable
fun ChildBottomSheetScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<BottomSheetListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

@Composable
fun BottomSheetListingScreen(navController: NavController) {
    @Composable
    fun MyButton(
        title: BottomSheetListingEnumType
    ) {
        val context = LocalContext.current
        Button(
            onClick = { navController.navigate(BottomsheetListingScreenPath(pathPostFix = title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Bottom Sheet Samples",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<BottomSheetListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
//endregion

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SimpleBottomSheet(navHostController: NavHostController) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SimpleBottomSheetContent()
        }, sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp
    ) {
        AddBackIconToScreen(screen = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                Button(onClick = {
                    scope.launch {
                        if (sheetState.isCollapsed) {
                            sheetState.expand()
                        } else {
                            sheetState.collapse()
                        }
                    }
                }) {
                    Text(text = "BS using Scaffold", color = MaterialTheme.colors.onPrimary)
                }
            }
        }) {
            navHostController.popBackStack()
        }
    }

}

@Composable
fun SimpleBottomSheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Green, shape = RoundedCornerShape(32.dp))
            .height(300.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bottom Sheet",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h3
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetWithPeekHeight(navHostController: NavHostController) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Green, shape = RoundedCornerShape(32.dp))
                    .height(300.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "56.dp", style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.Top),
                    color = MaterialTheme.colors.onPrimary,
                )
                Text(
                    text = "Bottom Sheet",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h3
                )
            }
        }, sheetBackgroundColor = Color.Green
    ) {
        AddBackIconToScreen(screen = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                Button(onClick = {
                    scope.launch {
                        if (sheetState.isCollapsed) {
                            sheetState.expand()
                        } else {
                            sheetState.collapse()
                        }
                    }
                }) {
                    Text(
                        text = "BS with peek height(56.dp)",
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }) {
            navHostController.popBackStack()
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetWithAnimation(navHostController: NavHostController) {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SimpleBottomSheetContent()
        },
        sheetBackgroundColor = Color.Green,
    ) {
        AddBackIconToScreen(screen = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                Button(onClick = {
                    scope.launch {
                        if (sheetState.isCollapsed) {
                            sheetState.expand()
                        } else {
                            sheetState.collapse()
                        }
                    }
                }) {
                    Text(
                        text = "BS Scaffold with animation",
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }) {
            navHostController.popBackStack()
        }
    }

}

//region Bottom sheet extension
fun Activity.showAsBottomSheet(
    wrapWithBottomSheetUI: Boolean = false,
    content: @Composable (() -> Unit) -> Unit
) {
    val viewGroup = this.findViewById(android.R.id.content) as ViewGroup
    addContentToView(wrapWithBottomSheetUI, viewGroup, content)
}

fun Fragment.showAsBottomSheet(
    wrapWithBottomSheetUI: Boolean = false,
    content: @Composable (() -> Unit) -> Unit
) {
    val viewGroup = requireActivity().findViewById(android.R.id.content) as ViewGroup
    addContentToView(wrapWithBottomSheetUI, viewGroup, content)
}

private fun addContentToView(
    wrapWithBottomSheetUI: Boolean,
    viewGroup: ViewGroup,
    content: @Composable (() -> Unit) -> Unit
) {
    viewGroup.addView(
        ComposeView(viewGroup.context).apply {
            setContent {
                BottomSheetWrapper(wrapWithBottomSheetUI, viewGroup, this, content)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetWrapper(
    wrapWithBottomSheetUI: Boolean,
    parent: ViewGroup,
    composeView: ComposeView,
    content: @Composable (() -> Unit) -> Unit
) {
    val tag = parent::class.java.simpleName
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(
            ModalBottomSheetValue.Hidden,
            confirmStateChange = {
                it != ModalBottomSheetValue.HalfExpanded
            }
        )
    var isSheetOpened by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.Transparent,
        sheetState = modalBottomSheetState,
        sheetContent = {
            when {
                wrapWithBottomSheetUI -> {
                    BottomSheetUIWrapper(coroutineScope, modalBottomSheetState) {
                        content {
                            animateHideBottomSheet(coroutineScope, modalBottomSheetState)
                        }
                    }
                }

                else -> content {
                    animateHideBottomSheet(coroutineScope, modalBottomSheetState)
                }
            }
        }
    ) {}


    BackHandler {
        animateHideBottomSheet(coroutineScope, modalBottomSheetState)
    }

    // Take action based on hidden state
    LaunchedEffect(modalBottomSheetState.currentValue) {
        when (modalBottomSheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> {
                when {
                    isSheetOpened -> parent.removeView(composeView)
                    else -> {
                        isSheetOpened = true
                        modalBottomSheetState.show()
                    }
                }
            }

            else -> {
                Log.i(tag, "Bottom sheet ${modalBottomSheetState.currentValue} state")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun animateHideBottomSheet(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState
) {
    coroutineScope.launch {
        modalBottomSheetState.hide() // will trigger the LaunchedEffect
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetUIWrapper(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(Color.White)
    ) {
        Box(Modifier.padding(top = 25.dp)) {
            content()
        }

        Divider(
            color = Color.Gray,
            thickness = 5.dp,
            modifier = Modifier
                .padding(top = 15.dp)
                .align(Alignment.TopCenter)
                .width(80.dp)
                .clip(RoundedCornerShape(50.dp))
        )

        CloseButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
        ) {
            coroutineScope.launch {
                modalBottomSheetState.hide()
            }
        }
    }
}

typealias OnClickedClose = () -> Unit

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onClickedClose: OnClickedClose
) {
    Box(
        modifier.then(
            Modifier.padding(end = 13.dp, top = 13.dp)
        )
    ) {
        Button(
            onClick = { onClickedClose() },
            modifier = Modifier.size(26.dp),
            shape = CircleShape,
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = colorResource(id = R.color.cloud))
        ) {
            Icon(
                modifier = Modifier.align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_x_2),
                contentDescription = "CLOSE_BUTTON_DESCRIPTION",
                tint = colorResource(id = R.color.gray_B7B7B7)
            )
        }
    }
}
//endregion

@Composable
fun BottomSheetUsingExtension(navHostController: NavHostController) {
    val context = LocalContext.current
    context.startActivity(Intent(context, BottomSheetActivity::class.java))
    /* val activity = context.getActivityOrNull()
     AddBackIconToScreen(screen = {
         Column(
             modifier = Modifier.fillMaxSize(),
             horizontalAlignment = Alignment.CenterHorizontally,
             verticalArrangement = Arrangement.Center,
         ) {
             Button(onClick = {
                 activity?.showAsBottomSheet {
                     SimpleBottomSheetContent()
                 }
             }) {
                 Text(text = "BS using Extension", color = MaterialTheme.colors.onPrimary)
             }
         }
     }) {
         navHostController.popBackStack()
     }*/
}