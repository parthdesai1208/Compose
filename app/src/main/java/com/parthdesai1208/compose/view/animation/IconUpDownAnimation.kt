package com.parthdesai1208.compose.view.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R

@Preview(showSystemUi = true)
@Composable
fun BoxWithIconUpDownAnimation() {

    val googleAnimation by rememberInfiniteTransition().animateValue(
        initialValue = 0.dp,
        targetValue = 15.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3500
                0.dp at 200
                15.dp at 3000 with LinearOutSlowInEasing
            }, //tween(3500), //easing = LinearOutSlowInEasing
            repeatMode = RepeatMode.Reverse
        )
    )

    val appleAnimation by rememberInfiniteTransition().animateValue(
        initialValue = 0.dp,
        targetValue = 18.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3800
                0.dp at 200
                18.dp at 3300 with LinearOutSlowInEasing
            }, //tween(3500), //easing = LinearOutSlowInEasing
            repeatMode = RepeatMode.Reverse
        )
    )

    val microsoftAnimation by rememberInfiniteTransition().animateValue(
        initialValue = 0.dp,
        targetValue = (-15).dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0.dp at 200
                (-15).dp at 2500 with LinearOutSlowInEasing
            }, //tween(3500), //easing = LinearOutSlowInEasing
            repeatMode = RepeatMode.Reverse
        )
    )

    val facebookAnimation by rememberInfiniteTransition().animateValue(
        initialValue = 0.dp,
        targetValue = (-18).dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3800
                0.dp at 200
                (-18).dp at 3300 with LinearOutSlowInEasing
            }, //tween(3500), //easing = LinearOutSlowInEasing
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                modifier = Modifier.absoluteOffset(y = googleAnimation)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_apple),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                modifier = Modifier.absoluteOffset(y = appleAnimation)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_microsoft),
                contentDescription = null,
                modifier = Modifier.absoluteOffset(y = microsoftAnimation)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_facebook), contentDescription = null,
                modifier = Modifier.absoluteOffset(y = facebookAnimation)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_airbnb),
                contentDescription = null,
                modifier = Modifier.absoluteOffset(y = googleAnimation)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_uber), contentDescription = null,
                modifier = Modifier.absoluteOffset(y = appleAnimation)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_tesla),
                contentDescription = null,
                modifier = Modifier.absoluteOffset(y = microsoftAnimation)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_twitter), contentDescription = null,
                modifier = Modifier.absoluteOffset(y = facebookAnimation)
            )
        }
    }
}