package com.parthdesai1208.compose.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.conditionalSingleModifier(
    condition: Boolean, modifier: @Composable Modifier.() -> Modifier
): Modifier {

    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }

}

@Composable
fun Modifier.conditionalMultipleModifier(
    condition: Boolean, vararg modifier: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        var all: Modifier = Modifier
        modifier.forEach {
            all = all.then(it(Modifier))
        }
        then(all)
    } else {
        this
    }
}

//use it like
@Composable
fun MyBox(
    modifier: Modifier = Modifier,
    isFocused: Boolean
) {
    Box(
        modifier = modifier
            .conditionalMultipleModifier(
                condition = isFocused,
                { border(border = BorderStroke(1.dp, Color.Black)) },
                { background(Color.LightGray) }
            )
    ) {
        // ...
    }
}

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier = if (condition) {
    then(ifTrue(Modifier))
} else {
    then(ifFalse(Modifier))
}

inline fun <T> Modifier.nullConditional(
    argument: T?,
    ifNotNull: Modifier.(T) -> Modifier,
    ifNull: Modifier.() -> Modifier = { this },
): Modifier {
    return if (argument != null) {
        then(ifNotNull(Modifier, argument))
    } else {
        then(ifNull(Modifier))
    }
}

fun Modifier.rotateOnClickComposed() = composed {
    val color = remember { mutableStateOf(listOf(Color.Red, Color.Green).random()) }
    var isClicked by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (isClicked) 45f else 0f)

    then(background(color = color.value)
        .clickable { isClicked = !isClicked }
        .graphicsLayer { rotationZ = rotation })
}

@Composable
fun Modifier.rotateOnClickComposable(): Modifier {
    val color = remember { mutableStateOf(listOf(Color.Red, Color.Green).random()) }
    var isClicked by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (isClicked) 45f else 0f)

    return then(background(color = color.value)
        .clickable { isClicked = !isClicked }
        .graphicsLayer { rotationZ = rotation })
}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }