package com.parthdesai1208.compose.view.anyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//@Preview(showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
fun StatusNavigationBarInsets() {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
                .statusBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Red)
                    .safeDrawingPadding()
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp)
                        .background(Color.White)
                )
            }
        }
    }
}
