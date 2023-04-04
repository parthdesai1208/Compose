package com.parthdesai1208.compose.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val teal200 = Color(0xFF03DAC5)

val Navy = Color(0xFF073042)
val Chartreuse = Color(0xFFEFF7CF)
val LightBlue = Color(0xFFD7EFFE)
val primaryVariantLColor = Color(0xFFFFFDE7)
val primaryVariantDColor = Color(0xFF090808)

val Purple100 = Color(0xFFE1BEE7)
val Green300 = Color(0xFF81C784)
val Green800 = Color(0xFF2E7D32)
val Amber600 = Color(0xFFFFB300)
val red1000 = Color(0xFFF52A26)
val red500 = Color(0x80F52A26)

val blueDirectionColor = Color(0xFF1A73E8)

val restaurant_gmap = Color(0xFF4285F4)
val petrol_gmap = Color(0xFFEA4335)
val hotel_gmap = Color(0xFFFBBC04)
val coffee_gmap = Color(0xFF34A853)
val groceries_gmap = Color(0xFFFA7B17)
val attractions_gmap = Color(0xFFF439A0)
val shopping_gmap = Color(0xFFA142F4)
val more_gmap = Color(0xFF24C1E0)

val RainbowRed = Color(0xFFDA034E)
val RainbowOrange = Color(0xFFFF9800)
val RainbowYellow = Color(0xFFFFEB3B)
val RainbowGreen = Color(0xFF4CAF50)
val RainbowBlue = Color(0xFF2196F3)
val RainbowIndigo = Color(0xFF3F51B5)
val RainbowViolet = Color(0xFF9C27B0)

val red = Color(0xFFEF5350)
val purple = Color(0xFFAB47BC)
val blue = Color(0xFF42A5F5)
val green = Color(0xFF66BB6A)

val GreyLight = Color(0xFFB5B6BA)
val GreyDark = Color(0xFF60626C)
val CircleOffsetInitialColor = Color(0xff712B75)
val CircleOffsetTargetColor = Color(0xFFE4AEC5)
val facebookIconColor = Color(0xFF1776d1)
val messengerColor1 = Color(0xFF02b8f9)
val messengerColor2 = Color(0xFF0277fe)
val googlePhotosRedColor = Color(0xFFf04231)
val googlePhotosBlueColor = Color(0xFF4385f7)
val googlePhotosGreenColor = Color(0xFF30a952)
val googlePhotosYellowColor = Color(0xFFffbf00)
val stackoverflowBottomRectangleColor = Color(0xFFbcbbbb)
val stackoverflowStackColor = Color(0xFFf48023)
val spotifyGreenColor = Color(0xFF1ed760)
val trelloBlueColor = Color(0xFF0269a9)

val LightDarkGrey: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

val LightDarkRed: Color
    @Composable
    get() = if (isSystemInDarkTheme()) red500 else red1000