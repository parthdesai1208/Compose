package com.parthdesai1208.compose.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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