package com.parthdesai1208.compose.view.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.*


@Phone
@Composable
fun ProgressAnimationPreview() {
    ComposeTheme {
        ProgressAnimation()
    }
}

@Composable
fun ProgressAnimation() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            val infiniteTransition = rememberInfiniteTransition()
            TwinCircleAnimation(infiniteTransition)
            CircleOffsetAnimation(infiniteTransition)
            PacmanAnimation(infiniteTransition)
            ArcRotationAnimation(infiniteTransition)
        }
    }
}


@Composable
fun TwinCircleAnimation(infiniteTransition: InfiniteTransition) {

    val scaleAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Row(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(color = MaterialTheme.colors.onSurface),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .scale(scaleAnimation)
                .clip(CircleShape)
                .background(color = LightDarkRed)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Box(
            modifier = Modifier
                .size(15.dp)
                .scale(scaleAnimation)
                .clip(CircleShape)
                .background(color = LightDarkRed)
        )
    }
}

@Composable
fun CircleOffsetAnimation(infiniteTransition: InfiniteTransition) {
    val easing = LinearOutSlowInEasing
    val lightDarkGreyColor = LightDarkGrey

    val color1 by infiniteTransition.animateColor(
        initialValue = CircleOffsetInitialColor,
        targetValue = CircleOffsetTargetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = easing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = CircleOffsetTargetColor,
        targetValue = CircleOffsetInitialColor,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = easing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offsetX by animateValues(
        values = listOf(
            0f,
            100f,
            -100f,
            0f
        ),//circle will move from 0 to 100 in x direction, then 100 to -100 , -100 to 0 (so total 3 moves)
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = easing),
            repeatMode = RepeatMode.Restart
        )
    )

    val scale by animateValues(
        values = listOf(
            1f,
            10f,
            10f,
            10f,
            1f
        ),//first circle will scale from  1 to 10(at 0f offset),10 to 10(at 100f offset),10 to 10(at -100f offset),10 to 1(at 0f offset)
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = easing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.size(100.dp)) {
        drawCircle(color = lightDarkGreyColor)

        drawCircle(
            color = color1,
            radius = 80f + scale * 4f,
            center = Offset(-offsetX + this.center.x, this.center.y)
        )
        drawCircle(
            color = color2,
            radius = 80f + scale * 4f,
            center = Offset(offsetX + this.center.x, this.center.y)
        )
    }
}

@Composable
fun PacmanAnimation(infiniteTransition: InfiniteTransition) {
    val circleColor = MaterialTheme.colors.onSurface

    val mouthUpAnimation by infiniteTransition.animateFloat(
        initialValue = 0F,//start with 0 degree
        targetValue = 40F,//go up up to 40 degree
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val mouthDownAnimation by infiniteTransition.animateFloat(
        initialValue = 360F,//start from 360 degree
        targetValue = 280F,//go down up to 280 degree
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier
            .size(100.dp)

    ) {
        drawArc(
            color = RainbowYellow,
            startAngle = mouthUpAnimation,
            sweepAngle = mouthDownAnimation,
            useCenter = true, //to make it circle
        )

        //eye of pacman
        drawCircle(
            color = circleColor,
            radius = 15f,
            center = Offset(x = this.center.x + 15f, y = this.center.y - 85f)
        )
    }
}

@Composable
fun ArcRotationAnimation(infiniteTransition: InfiniteTransition) {

    val arcAnimationSpec = infiniteRepeatable<Float>(
        animation = tween(1000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
    )
    val lightDarkGreyColor = LightDarkGrey

    val arc1 by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = arcAnimationSpec
    )

    val arc2 by infiniteTransition.animateFloat(
        initialValue = 180f,
        targetValue = 360f,
        animationSpec = arcAnimationSpec
    )

    val greenCircleAnimation by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, delayMillis = 100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.size(100.dp)) {
        //first arc
        drawArc(
            color = Green800,
            startAngle = arc1,
            sweepAngle = 90f,
            useCenter = false,//prevent from making full circle
            style = Stroke(width = 10f, cap = StrokeCap.Round)
        )

        //second arc
        drawArc(
            color = Green800,
            startAngle = arc2,
            sweepAngle = 90f,
            useCenter = false, //prevent from making full circle
            style = Stroke(width = 10f, cap = StrokeCap.Round)
        )

        drawCircle(
            color = lightDarkGreyColor,
            radius = 120f
        )

        drawCircle(
            color = Green800,
            radius = greenCircleAnimation
        )

    }

}