/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.parthdesai1208.compose.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun AlertDialogTheme(content: @Composable () -> Unit, darkTheme: Boolean = isSystemInDarkTheme()) {

    val alertDialogColors = if (darkTheme) {
        darkColors(
            primary = Color.White,
            surface = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black),
            onSurface = Color.White
        )
    }else{
        lightColors(
            primary = Color.Black,
            surface = Color.Black.copy(alpha = 0.12f).compositeOver(Color.White),
            onSurface = Color.Black
        )
    }

    // Copy the current [Typography] and replace some text styles for this theme.
    val currentTypography = MaterialTheme.typography
    val dialogTypography = currentTypography.copy(
        body2 = currentTypography.body1.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 1.sp
        ),
        button = currentTypography.button.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.2.em
        )
    )
    MaterialTheme(colors = alertDialogColors, typography = dialogTypography, content = content)
}
