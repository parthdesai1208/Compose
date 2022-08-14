package com.parthdesai1208.compose.view.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.ComposeTheme

val toeColor = Color(230, 149, 53)
val birdGreen = Color(120, 201, 60)
val birdLightGreen = Color(152, 216, 71)
val birdBeak = Color(246, 196, 52)
val birdBeakBelow = Color(230, 149, 53)

@Composable
fun DuolingoBird(modifier: Modifier = Modifier) {

    var scale by remember {
        mutableStateOf(1f)
    }

    val scalingAnim = remember {
        Animatable(scale)
    }

    val repeatableAnim = rememberInfiniteTransition()

    val rotateLeft by repeatableAnim.animateFloat(
        initialValue = 8f, targetValue = -4f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val beakSpace by repeatableAnim.animateFloat(
        initialValue = 16f, targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val eyeBallsMoveLeft by repeatableAnim.animateFloat(
        initialValue = -16f, targetValue = 16f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val eyeBallsMoveRight by repeatableAnim.animateFloat(
        initialValue = 16f, targetValue = -16f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val toeRotateLeft by repeatableAnim.animateFloat(
        initialValue = 0f,
        targetValue = 20f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val toeRotateRight by repeatableAnim.animateFloat(
        initialValue = 0f,
        targetValue = 30f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val bodyRotateRight by repeatableAnim.animateFloat(
        initialValue = 0f,
        targetValue = 40f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val bodyTranslationY by repeatableAnim.animateFloat(
        initialValue = 0f,
        targetValue = -40f, animationSpec = InfiniteRepeatableSpec(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = scale, block = {
        scalingAnim.animateTo(
            scale,
            spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow)
        )
    })

    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .fillMaxHeight()
        .wrapContentHeight(align = Alignment.CenterVertically)
        .clickable {
            scale = if (scale == 1f) 56f else 1f
        }
        .graphicsLayer(translationY = scalingAnim.value)) {
        //two toes at bottom
        BirdToes(toeRotateLeft, toeRotateRight)
        Box(
            modifier = Modifier.graphicsLayer(
                rotationZ = bodyRotateRight,
                translationY = bodyTranslationY
            )
        ) {
            BirdBody(rotateLeft, 0f, eyeBallsMoveLeft, eyeBallsMoveRight, beakSpace)
        }
    }
}

@Composable
private fun BirdToes(toeRotateLeft: Float, toeRotateRight: Float) {
    //left toe
    BirdToes(
        Modifier
            .rotate(toeRotateRight)
            .offset(x = 15.dp, y = (40).dp)
    )
    //right toe
    BirdToes(
        Modifier
            .rotate(toeRotateLeft)
            .offset(x = (-30).dp, y = (40).dp)
    )
}

@Composable
private fun BoxScope.BirdBody(
    rotateLeft: Float,
    rotateRight: Float,
    eyeBallsMoveLeft: Float,
    eyeBallsMoveRight: Float,
    beakSpace: Float
) {
    BirdHands(rotateLeft, rotateRight)
    BirdMainShape(Modifier.offset(y = (-120).dp, x = (-80).dp))
    BirdFaceEyeBG(Modifier.offset(y = (-120).dp, x = (-80).dp))
    BirdEye(Modifier.offset(y = (-75).dp, x = (-38).dp), eyeBallsMoveLeft)
    BirdEye(Modifier.offset(y = (-75).dp, x = 25.dp), eyeBallsMoveRight)
    BirdBeak(beakSpace)
    BirdCenterPatch()
}

@Composable
private fun BirdCenterPatch() {
    BirdCenterPatch(Modifier.offset(y = (-115).dp, x = (-70).dp)) //bottom patch
    BirdCenterPatch(Modifier.offset(y = (-130).dp, x = (-50).dp)) //top right patch
    BirdCenterPatch(Modifier.offset(y = (-130).dp, x = (-90).dp)) //top left patch
}

@Composable
private fun BirdBeak(beakSpace: Float) {
    BirdBeakTop(Modifier.offset(y = (-120).dp, x = (-80).dp))
    BirdBreakBelow(
        Modifier
            .offset(y = (-118).dp, x = (-80).dp)
            .graphicsLayer(translationY = beakSpace)
    )
}

@Composable
private fun BirdHands(rotateLeft: Float, rotateRight: Float) {
    //left hand
    BirdHands(
        Modifier
            .offset(y = (-130).dp, x = (110).dp)
            .mirror()
            .graphicsLayer(rotationZ = rotateLeft),
    )
    //right hand
    BirdHands(
        Modifier
            .offset(y = (-130).dp, x = (-85).dp)
            .graphicsLayer(rotationZ = rotateRight)
    )
}

@Composable
fun BirdBeakTop(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xMul = 1f
            val xOff = 1f
            val yMul = 1f
            val yOff = 1f
            lineTo(255 * xMul + xOff, 249 * yMul + yOff)
            lineTo(222 * xMul + xOff, 241 * yMul + yOff)
            cubicTo(
                225 * xMul + xOff, 209 * yMul + yOff, 276 * xMul + xOff, 212 * yMul + yOff,
                256 * xMul + xOff, 212 * yMul + yOff
            )
            cubicTo(
                285 * xMul + xOff, 216 * yMul + yOff, 293 * xMul + xOff, 241 * yMul + yOff,
                289 * xMul + xOff, 242 * yMul + yOff
            )
            lineTo(255 * xMul + xOff, 249 * yMul + yOff)

            drawPath(this, birdBeak)

        }
    })
}

@Composable
fun BirdBreakBelow(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xMul = 1f
            val xOff = 1f
            val yMul = 1f
            val yOff = 1f
            lineTo(279 * xMul + xOff, 244 * yMul + yOff)
            lineTo(255 * xMul + xOff, 248 * yMul + yOff)
            lineTo(232 * xMul + xOff, 242 * yMul + yOff)
            cubicTo(
                233 * xMul + xOff, 244 * yMul + yOff, 226 * xMul + xOff, 265 * yMul + yOff,
                255 * xMul + xOff, 271 * yMul + yOff
            )
            cubicTo(
                276 * xMul + xOff, 273 * yMul + yOff, 280 * xMul + xOff, 246 * yMul + yOff,
                282 * xMul + xOff, 246 * yMul + yOff
            )

            drawPath(this, birdBeakBelow)

        }
    })
}

@Composable
fun BoxScope.BirdEye(
    modifier: Modifier = Modifier,
    eyeBallsMove: Float
) {
    //eye white portion
    Canvas(modifier = modifier
        .width(40.dp)
        .height(50.dp)
        .scale(0.8f), onDraw = {
        drawOval(Color.White)
    })
    //eye black portion
    Canvas(modifier = modifier
        .align(Alignment.Center)
        .width(40.dp)
        .height(50.dp)
        .scale(0.4f)
        .graphicsLayer(translationX = eyeBallsMove), onDraw = {
        drawOval(Color.Black)
    })
}

@Composable
fun BirdFaceEyeBG(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xMul = 1f
            val xOff = 1f
            val yMul = 1f
            val yOff = 1f
            moveTo(232 * xMul + xOff, 245 * yMul + yOff)
            cubicTo(
                175 * xMul + xOff, 328 * yMul + yOff, 109 * xMul + xOff, 257 * yMul + yOff,
                106 * xMul + xOff, 241 * yMul + yOff
            )
            cubicTo(
                102.3142292989963f * xMul + xOff,
                221.34255626131358f * yMul + yOff,
                85 * xMul + xOff,
                137 * yMul + yOff,
                129 * xMul + xOff,
                123 * yMul + yOff
            )
            cubicTo(
                133 * xMul + xOff, 143 * yMul + yOff, 121 * xMul + xOff, 90 * yMul + yOff,
                139 * xMul + xOff, 92 * yMul + yOff
            )
            cubicTo(
                119 * xMul + xOff, 76 * yMul + yOff, 158 * xMul + xOff, 101 * yMul + yOff,
                160 * xMul + xOff, 110 * yMul + yOff
            )
            cubicTo(
                158 * xMul + xOff,
                64 * yMul + yOff,
                179 * xMul + xOff,
                73 * yMul + yOff,
                169 * xMul + xOff,
                77 * yMul + yOff
            )
            cubicTo(
                225 * xMul + xOff, 115 * yMul + yOff, 212 * xMul + xOff, 143 * yMul + yOff,
                255 * xMul + xOff, 145 * yMul + yOff
            )
            cubicTo(
                317 * xMul + xOff, 129 * yMul + yOff, 311 * xMul + xOff, 95 * yMul + yOff,
                342 * xMul + xOff, 78 * yMul + yOff
            )
            cubicTo(
                350 * xMul + xOff, 66 * yMul + yOff, 356 * xMul + xOff, 113 * yMul + yOff,
                357 * xMul + xOff, 110 * yMul + yOff
            )
            cubicTo(
                369 * xMul + xOff,
                79 * yMul + yOff,
                395 * xMul + xOff,
                91 * yMul + yOff,
                375 * xMul + xOff,
                91 * yMul + yOff
            )
            cubicTo(
                385 * xMul + xOff, 84 * yMul + yOff, 389 * xMul + xOff, 130 * yMul + yOff,
                385 * xMul + xOff, 122 * yMul + yOff
            )
            cubicTo(
                394 * xMul + xOff, 119 * yMul + yOff, 421 * xMul + xOff, 158 * yMul + yOff,
                412 * xMul + xOff, 218 * yMul + yOff
            )
            cubicTo(
                399 * xMul + xOff, 285 * yMul + yOff, 358 * xMul + xOff, 281 * yMul + yOff,
                338 * xMul + xOff, 281 * yMul + yOff
            )
            cubicTo(
                318 * xMul + xOff, 281 * yMul + yOff, 288 * xMul + xOff, 269 * yMul + yOff,
                282 * xMul + xOff, 243 * yMul + yOff
            )
            drawPath(this, birdLightGreen)

        }
    })
}

@Composable
fun BirdCenterPatch(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xmul = 1f
            val xoff = 1f
            val ymul = 1f
            val yoff = 1f
            lineTo(192 * xmul + xoff, 340 * ymul + yoff)
            lineTo(245 * xmul + xoff, 340 * ymul + yoff)
            cubicTo(
                250 * xmul + xoff, 340 * ymul + yoff, 238 * xmul + xoff, 362 * ymul + yoff,
                218 * xmul + xoff, 362 * ymul + yoff
            )
            cubicTo(
                198 * xmul + xoff, 362 * ymul + yoff, 195 * xmul + xoff, 340 * ymul + yoff,
                192 * xmul + xoff, 340 * ymul + yoff
            )
            drawPath(this, birdLightGreen)
        }
    })
}

@Composable
fun BirdMainShape(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xMul = 1f
            val xOff = 1f
            val yMul = 1f
            val yOff = 1f
            lineTo(83 * xMul + xOff, 132 * yMul + yOff)
            cubicTo(
                84 * xMul + xOff,
                121 * yMul + yOff,
                59 * xMul + xOff,
                383 * yMul + yOff,
                141 * xMul + xOff,
                411 * yMul + yOff
            )
            cubicTo(
                191 * xMul + xOff, 480 * yMul + yOff, 342 * xMul + xOff, 476 * yMul + yOff,
                395 * xMul + xOff, 391 * yMul + yOff
            )
            cubicTo(
                405.5820258211916f * xMul + xOff,
                374.0288265131832f * yMul + yOff,
                439 * xMul + xOff,
                282 * yMul + yOff,
                429 * xMul + xOff,
                133 * yMul + yOff
            )
            cubicTo(
                437 * xMul + xOff,
                29 * yMul + yOff,
                353 * xMul + xOff,
                44 * yMul + yOff,
                335 * xMul + xOff,
                57 * yMul + yOff
            )
            cubicTo(
                318.7864154320024f * xMul + xOff,
                68.70981107688718f * yMul + yOff,
                278 * xMul + xOff,
                82 * yMul + yOff,
                258 * xMul + xOff,
                82 * yMul + yOff
            )
            cubicTo(
                234 * xMul + xOff,
                93 * yMul + yOff,
                163 * xMul + xOff,
                33 * yMul + yOff,
                124 * xMul + xOff,
                48 * yMul + yOff
            )
            cubicTo(
                49 * xMul + xOff,
                88 * yMul + yOff,
                102 * xMul + xOff,
                145 * yMul + yOff,
                83 * xMul + xOff,
                132 * yMul + yOff
            )
            drawPath(this, birdGreen)
        }
    })
}

@Composable
fun BirdHands(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        Path().apply {
            val xMul = 1f
            val xOff = 1f
            val yMul = 1f
            val yOff = 1f

            moveTo(426 * xMul + xOff, 247 * yMul + yOff)
            cubicTo(
                418 * xMul + xOff, 251 * yMul + yOff, 510 * xMul + xOff, 378 * yMul + yOff,
                500 * xMul + xOff, 390 * yMul + yOff
            )
            cubicTo(
                457 * xMul + xOff, 447 * yMul + yOff, 302 * xMul + xOff, 360 * yMul + yOff,
                298 * xMul + xOff, 360 * yMul + yOff
            )

            drawPath(this, color = birdGreen)
        }
    })
}

@Composable
fun BirdToes(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier
        .width(40.dp)
        .height(20.dp), onDraw = {
        drawRoundRect(color = toeColor, cornerRadius = CornerRadius(24f, 24f))
    })
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun PreviewDuolingoBird() {
    ComposeTheme {
        Surface(
            Modifier
                .fillMaxSize()
        ) {
            DuolingoBird()
        }
    }
}

@Stable
fun Modifier.mirror(): Modifier {
    return this.scale(scaleX = -1f, scaleY = 1f)
}