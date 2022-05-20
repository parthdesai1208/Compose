package com.parthdesai1208.compose.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.parthdesai1208.compose.R

val HorizontalListData = listOf(
    R.drawable.hl1 to R.string.hl1,
    R.drawable.hl2 to R.string.hl2,
    R.drawable.hl3 to R.string.hl3,
    R.drawable.hl4 to R.string.hl4,
    R.drawable.hl5 to R.string.hl5,
    R.drawable.hl6 to R.string.hl6,
    R.drawable.hl1 to R.string.hl1,
    R.drawable.hl2 to R.string.hl2,
    R.drawable.hl3 to R.string.hl3,
    R.drawable.hl4 to R.string.hl4,
    R.drawable.hl5 to R.string.hl5,
    R.drawable.hl6 to R.string.hl6
).map { DrawableStringPair(it.first, it.second) }

data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

val HorizontalGridListData = listOf(
    R.drawable.hgl1 to R.string.hgl1,
    R.drawable.hgl2 to R.string.hgl2,
    R.drawable.hgl3 to R.string.hgl3,
    R.drawable.hgl4 to R.string.hgl4,
    R.drawable.hgl5 to R.string.hgl5,
    R.drawable.hgl6 to R.string.hgl6
).map { DrawableStringPair(it.first, it.second) }
