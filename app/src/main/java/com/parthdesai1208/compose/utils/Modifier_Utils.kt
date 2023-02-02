package com.parthdesai1208.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.ConditionalModifier(
    condition: Boolean,
    modifier: @Composable Modifier.() -> Modifier
): Modifier {

    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }

}