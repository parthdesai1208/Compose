package com.parthdesai1208.compose.model.animation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Immutable
data class StarMeta(
    val color: Color, val initialOffset: Offset
)