package com.parthdesai1208.compose.view.animation

import android.graphics.Paint
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SweepGradientShader
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen

val CardRoundedCornerSize = 16.dp
const val OffsetSlowDownFactor = 30f

@Composable
fun ThreeDCardMoving(navHostController: NavHostController) {
    BuildTopBarWithScreen(title = stringResource(id = R.string._3d_card_moving), screen = {
        Box(contentAlignment = Alignment.Center) {
            BackgroundGlowSurface()
            ForegroundClickableSurface()
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Composable
private fun BackgroundGlowSurface() {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(2f)
        .padding(20.dp)
        .drawBehind {
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawRoundRect(0f,
                    0f,
                    size.width, //size = size of the current drawing shape
                    size.height,
                    CardRoundedCornerSize.toPx(),
                    CardRoundedCornerSize.toPx(),
                    Paint().apply {
                        shader = SweepGradientShader(
                            colors = listOf(
                                Color.Magenta,
                                Color.Red,
                                Color.Blue,
                                Color.Green,
                                Color.Yellow,
                                Color.Cyan,
                            ), center = size.center
                        )
                        setShadowLayer(
                            8f, 0f, 0f, Color.Green.toArgb()
                        )
                    })
            }
        },
        color = Color.Transparent, //set color as Transparent so that paint will apply its own color
        content = {})
}

@Composable
private fun ForegroundClickableSurface() {
    var cardCenterOffset: Offset? by remember { mutableStateOf(null) }
    var currentOffset: Offset by remember { mutableStateOf(Offset.Zero) }
    val animatedOffset by animateOffsetAsState(targetValue = currentOffset)

    Surface(modifier = Modifier
        .fillMaxSize()
        .aspectRatio(2f)
        .zIndex(1f)
        .padding(30.dp)
        //for keeping surface in center
        .onPlaced { coordinates -> cardCenterOffset = coordinates.centerOffset() }
        //for rotation of surface
        .graphicsLayer {
            rotationX =
                -animatedOffset.y // Because Modifier.pointerInput gives Offset(y,x) instead of Offset(x,y)
                    .times(2)
                    .limitAngleValue()

            rotationY = animatedOffset.x.limitAngleValue()
        }
        //for user tap & drag
        .pointerInput(Unit) {
            detectTapGestures(onPress = { offset ->
                currentOffset = offset.centerOffsetAdjustedValue(cardCenterOffset)

                awaitRelease() //Waits for the press to be released before returning

                currentOffset = Offset.Zero

                detectDragGestures(
                    onDrag = { change, _ ->
                        currentOffset = change.position.centerOffsetAdjustedValue(cardCenterOffset)
                    },
                    onDragStart = { dragStartOffset ->
                        currentOffset = dragStartOffset
                    },
                    onDragEnd = {
                        currentOffset = Offset.Zero
                    },
                    onDragCancel = {
                        currentOffset = Offset.Zero
                    },
                )
            })
        }
        .clip(shape = RoundedCornerShape(CardRoundedCornerSize)), content = {
        Text(
            text = stringResource(R.string.you_can_drag_me_but_can_t_remove_drag_out_from_parent_surface),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    })
}

private fun Float.limitAngleValue(maxValue: Float = 20f) =
    //Ensures that this value lies in the specified range
    coerceIn(minimumValue = -maxValue, maximumValue = maxValue)

private fun LayoutCoordinates.centerOffset() = Offset(x = size.width / 2f, y = size.height / 2f)

private fun Offset.centerOffsetAdjustedValue(centerOffset: Offset?) =
    (this - centerOffset!!) / OffsetSlowDownFactor
