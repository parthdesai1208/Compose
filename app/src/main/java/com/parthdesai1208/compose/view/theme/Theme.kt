package com.parthdesai1208.compose.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPaletteBasic
    } else {
        LightColorPaletteBasic
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private val DarkColorPaletteBasic = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200
    /*primaryVariant = primaryVariantDColor,
    surface = Color.Blue,
    onSurface = Navy,
    primary = Navy,
    onPrimary = Chartreuse,
    background = Color.Black,
    onBackground = Color.Black*/
)

private val LightColorPaletteBasic = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200
    /*primaryVariant = primaryVariantLColor,
    surface = Color.Blue,
    onSurface = Color.White,
    primary = LightBlue,
    onPrimary = Navy*/
)


