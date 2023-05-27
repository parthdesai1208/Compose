package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.*


@Composable
fun RightShadow3DLayout() {
    ThreeDimensionalLayout(content = {

        Surface {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.lorem_ipsum),
                    textAlign = TextAlign.Start,
                    style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                )
                Row(horizontalArrangement = Arrangement.Center) {
                    Text("ok", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                }
            }
        }
    })
}

sealed class Perspective {
    data class Left(
        val bottomEdgeColor: Color, val rightEdgeColor: Color,
    ) : Perspective()

    data class Right(
        val topEdgeColor: Color, val leftEdgeColor: Color,
    ) : Perspective()

    data class Top(
        val bottomEdgeColor: Color,
    ) : Perspective()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThreeDimensionalLayout(
    perspective: Perspective = Perspective.Left(
        bottomEdgeColor = MaterialTheme.colors.onSurface,
        rightEdgeColor = MaterialTheme.colors.onSurface
    ),
    edgeOffset: Dp = 16.dp, content: @Composable () -> Unit,
) {
    val offsetInPx = with(LocalDensity.current) {
        edgeOffset.toPx()
    }
    val myInteractionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by myInteractionSource.collectIsPressedAsState()

    val elevationOffset by remember {
        derivedStateOf {
            if (isPressed) {
                IntOffset(offsetInPx.toInt(), offsetInPx.toInt())
            } else {
                IntOffset.Zero
            }
        }
    }

    val localHapticFeedback = LocalHapticFeedback.current

    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()
        .padding(32.dp)
        .combinedClickable(
            interactionSource = myInteractionSource,
            indication = null,
            onClick = {
                localHapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        )
        .graphicsLayer {
            rotationX = when (perspective) {
                is Perspective.Top -> {
                    16f
                }
                else -> {
                    0f
                }
            }
        }
        .drawBehind {
            if (isPressed.not()) {
                when (perspective) {
                    is Perspective.Left -> {
                        val rightEdge = Path().apply {
                            moveTo(size.width, 0f)       //move to top right
                            lineTo(size.width + offsetInPx, offsetInPx) //line to back side
                            lineTo(
                                size.width + offsetInPx,
                                size.height + offsetInPx
                            ) //straight line to bottom
                            lineTo(size.width, size.height)     //line to bottom right
                            close()
                        }
                        val bottomEdge = Path().apply {
                            moveTo(size.width, size.height)   //move to bottom right
                            lineTo(
                                size.width + offsetInPx,
                                size.height + offsetInPx
                            ) //line to back side
                            lineTo(offsetInPx, size.height + offsetInPx)  //line to left
                            lineTo(0f, size.height)         //line to bottom left
                            close()
                        }
                        drawPath(path = rightEdge, color = perspective.rightEdgeColor)
                        drawPath(path = bottomEdge, color = perspective.bottomEdgeColor)
                    }
                    is Perspective.Top -> {
                        // bottom edge
                        val bottomEdge = Path().apply {
                            moveTo(0f, size.height)
                            lineTo(size.width, size.height)
                            lineTo(size.width - offsetInPx, size.height + offsetInPx)
                            lineTo(offsetInPx, size.height + offsetInPx)
                            close()
                        }
                        drawPath(path = bottomEdge, color = perspective.bottomEdgeColor)
                    }
                    is Perspective.Right -> {
                        val topEdge = Path().apply {
                            moveTo(-offsetInPx, -offsetInPx)
                            lineTo(size.width - offsetInPx, -offsetInPx)
                            lineTo(size.width, 0f)
                            lineTo(0f, 0f)
                            close()
                        }
                        val leftEdge = Path().apply {
                            moveTo(-offsetInPx, -offsetInPx)
                            lineTo(0f, 0f)
                            lineTo(0f, size.height)
                            lineTo(-offsetInPx, size.height - offsetInPx)
                            close()
                        }
                        drawPath(path = topEdge, color = perspective.topEdgeColor)
                        drawPath(path = leftEdge, color = perspective.leftEdgeColor)
                    }
                }
            }
        }, content = {
        Box(modifier = Modifier.offset {
            elevationOffset
        }, contentAlignment = Alignment.Center) {
            content()
        }
    })
}


@Composable
fun LeftShadow3DLayout() {
    ThreeDimensionalLayout(
        perspective = Perspective.Right(
            topEdgeColor = MaterialTheme.colors.onSurface,
            leftEdgeColor = MaterialTheme.colors.onSurface
        ), content = {

            Surface {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.lorem_ipsum),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    )
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            "ok",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        )
                    }
                }
            }
        })
}

@Composable
fun BottomShadow3DLayout() {
    ThreeDimensionalLayout(
        perspective = Perspective.Top(
            bottomEdgeColor = MaterialTheme.colors.onSurface
        ), content = {

            Surface {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.lorem_ipsum),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    )
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            "ok",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        )
                    }
                }
            }
        })
}

@Phone
@Composable
fun PreviewDialog3D() {
    ComposeTheme {
        Dialog3D()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Dialog3D(edgeOffset: Dp = 16.dp) {
    var exploreMoreTextFieldSize by remember { mutableStateOf(Size.Zero) }
    var skipTextFieldSize by remember { mutableStateOf(Size.Zero) }
    var columnSize by remember { mutableStateOf(Size.Zero) }

    val offsetInPx = with(LocalDensity.current) {
        edgeOffset.toPx()
    }

    val interactionSourceForExplore = remember {
        MutableInteractionSource()
    }

    val interactionSourceForSkip = remember {
        MutableInteractionSource()
    }

    val isExplorePressed by interactionSourceForExplore.collectIsPressedAsState()
    val isSkipPressed by interactionSourceForSkip.collectIsPressedAsState()

    val elevationOffsetForExplore by remember {
        derivedStateOf {
            if (isExplorePressed) {
                IntOffset(offsetInPx.toInt(), offsetInPx.toInt())
            } else {
                IntOffset.Zero
            }
        }
    }

    val elevationOffsetForSkip by remember {
        derivedStateOf {
            if (isSkipPressed) {
                IntOffset(offsetInPx.toInt(), offsetInPx.toInt())
            } else {
                IntOffset.Zero
            }
        }
    }

    val localHapticFeedback = LocalHapticFeedback.current
    val rightEdgeColor = LightDarkGrey.copy(alpha = 0.80f)

    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize()
        .padding(32.dp)
        .drawBehind {
            val rightEdge = Path().apply {
                moveTo(columnSize.width, 0f)
                lineTo(columnSize.width + offsetInPx, offsetInPx)
                lineTo(
                    columnSize.width + offsetInPx,
                    (columnSize.height - skipTextFieldSize.height) + offsetInPx
                )
                lineTo(columnSize.width, columnSize.height - skipTextFieldSize.height)
                close()
            }
            drawPath(path = rightEdge, color = rightEdgeColor)

            if (isExplorePressed.not()) {
                val exploreEdge = Path().apply {
                    val differenceOfColumnAndExploreText =
                        columnSize.height - exploreMoreTextFieldSize.height
                    moveTo(
                        exploreMoreTextFieldSize.width,
                        differenceOfColumnAndExploreText + exploreMoreTextFieldSize.height
                    )
                    lineTo(
                        exploreMoreTextFieldSize.width + offsetInPx,
                        differenceOfColumnAndExploreText + exploreMoreTextFieldSize.height + offsetInPx
                    )
                    lineTo(
                        offsetInPx,
                        differenceOfColumnAndExploreText + exploreMoreTextFieldSize.height + offsetInPx
                    )
                    lineTo(
                        0f,
                        differenceOfColumnAndExploreText + exploreMoreTextFieldSize.height
                    )
                }
                drawPath(path = exploreEdge, color = RainbowOrangeDark)
            }
            if (isSkipPressed.not()) {
                val skipEdge = Path().apply {
                    val differenceOfColumnAndSkipText = columnSize.height - skipTextFieldSize.height
                    moveTo(
                        skipTextFieldSize.width + exploreMoreTextFieldSize.width,
                        differenceOfColumnAndSkipText
                    )
                    lineTo(
                        exploreMoreTextFieldSize.width + skipTextFieldSize.width + offsetInPx,
                        differenceOfColumnAndSkipText + offsetInPx
                    )
                    lineTo(
                        exploreMoreTextFieldSize.width + skipTextFieldSize.width + offsetInPx,
                        differenceOfColumnAndSkipText + skipTextFieldSize.height + offsetInPx
                    )
                    lineTo(
                        exploreMoreTextFieldSize.width + offsetInPx,
                        differenceOfColumnAndSkipText + skipTextFieldSize.height + offsetInPx
                    )
                    lineTo(
                        exploreMoreTextFieldSize.width,
                        differenceOfColumnAndSkipText + skipTextFieldSize.height
                    )
                }
                drawPath(path = skipEdge, color = GreyDark)
            }

        }
        .onGloballyPositioned {
            columnSize = it.size.toSize()
        }, content = {
        Surface {
            Column(
                modifier = Modifier
                    .background(color = LightDarkGrey),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Text(
                        modifier = Modifier.padding(all = 8.dp),
                        text = stringResource(id = R.string.lorem_ipsum),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    )
                }
                Row {
                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .weight(0.70f)
                            .offset {
                                elevationOffsetForExplore
                            }
                            .background(color = RainbowOrange)
                            .combinedClickable(
                                interactionSource = interactionSourceForExplore,
                                indication = null,
                                onClick = {
                                    localHapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                })
                            .onGloballyPositioned {
                                exploreMoreTextFieldSize = it.size.toSize()
                            }
                    ) {
                        Text(
                            "Explore More?",
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight(align = Alignment.CenterVertically)
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                            color = RainbowOrangeDark
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .weight(0.30f)
                            .offset {
                                elevationOffsetForSkip
                            }
                            .background(color = GreyLight)
                            .combinedClickable(
                                interactionSource = interactionSourceForSkip,
                                indication = null,
                                onClick = {
                                    localHapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                })
                            .onGloballyPositioned {
                                skipTextFieldSize = it.size.toSize()
                            }
                    ) {
                        Text(
                            "Skip ->",
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight(align = Alignment.CenterVertically)
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                            color = GreyDark
                        )
                    }
                }
            }
        }
    })
}