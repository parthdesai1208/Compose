package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parthdesai1208.compose.view.theme.attractions_gmap


@Composable
fun DrawRect() {
    Box {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface),
            //Note: here we specify background color because canvas lets you draw content on screen, it's not taking color from theme that we
            //use in app
            onDraw = {
                val canvasQuadrantSize = size / 2F
                drawRect(color = attractions_gmap, size = canvasQuadrantSize)
            })
        Text(
            text = "by default, drawRect start with (0,0) Coordinate at top-left side of the phone screen",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(all = 8.dp)
        )
    }
}

@Composable
fun DrawLineFromTopRightToBottomLeft() {
    Box {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface),
            onDraw = {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawLine(
                    color = attractions_gmap,
                    start = Offset(x = canvasWidth, y = 0f),
                    end = Offset(x = 0f, canvasHeight),
                    strokeWidth = 5f
                )
            })

        Text(
            text = "drawLines from topRight \nto bottomLeft using \ncoordinate system",
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(all = 8.dp)
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DrawText() {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface),
        onDraw = {
            drawText(
                textMeasurer = textMeasurer,
                text = "This text is drawing using `drawText` with canvas onDraw{} lambda\nstarting from 100f in x-axis & y-axis",
                style = TextStyle(color = attractions_gmap, fontSize = 16.sp),
                topLeft = Offset(x = 100f, y = 100f)
            )
        })
}