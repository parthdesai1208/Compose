package com.parthdesai1208.compose.view.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Gaps(
    val zero: Dp = Dp.Unspecified,
    val tiny: Dp = Dp.Unspecified,
    val small: Dp = Dp.Unspecified,
    val medium: Dp = Dp.Unspecified,
    val large: Dp = Dp.Unspecified,
)

val AppGaps = Gaps(
    zero = 0.dp,
    tiny = 2.dp,
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp,
)

val LocalAppGaps = compositionLocalOf { AppGaps }