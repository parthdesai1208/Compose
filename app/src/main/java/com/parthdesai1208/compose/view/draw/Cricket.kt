package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.darkStadiumGreen
import com.parthdesai1208.compose.view.theme.lightStadiumGreen
import com.parthdesai1208.compose.view.theme.pitch

@Composable
fun DrawCricketGround(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    BuildTopBarWithScreen(screen = {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CricketField()
            CricketPitch()
        }
    },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}

@Composable
fun CricketField() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.size(320.dp),
            shape = CircleShape
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val height = size.height
                val width = size.width
                val n = 25
                val rectangleHeight = height / n

                for (i in 0..n) {
                    val color = if (i % 2 == 0) lightStadiumGreen else darkStadiumGreen

                    //green lines on field
                    drawRect(
                        color = color,
                        topLeft = Offset(0f, i * rectangleHeight),
                        size = Size(width, rectangleHeight),
                    )
                }

                //outer circle
                drawCircle(
                    color = Color.White,
                    style = Stroke(2.dp.toPx()),
                    center = Offset(width / 2, height / 2),
                    radius = width / 2 - 20f
                )

                //inner circle
                drawCircle(
                    color = Color.White,
                    style = Stroke(2.dp.toPx()),
                    center = Offset(width / 2, height / 2),
                    radius = width / 2 - 200f
                )
            }
        }
    }
}

@Composable
fun CricketPitch(modifier: Modifier = Modifier) {
    val s: Dp = 40.dp
    val x = 0f
    val y = 0f
    val h = s * 3
    val w = s
    val cliche = w / 4
    val wide = cliche / 3
    Canvas(modifier = modifier.size(width = w, height = h)) {
        //pitch rectangle
        drawRect(
            color = pitch,
            topLeft = Offset(x, y),
            size = Size(width = w.toPx(), height = h.toPx())
        )
        //top longer horizontal line
        drawLine(
            start = Offset(x, cliche.toPx()),
            end = Offset(w.toPx(), cliche.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )
        //bottom longer horizontal line
        drawLine(
            start = Offset(x, h.toPx() - cliche.toPx()),
            end = Offset(w.toPx(), h.toPx() - cliche.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )
        //top left vertical line
        drawLine(
            start = Offset(wide.toPx(), y),
            end = Offset(wide.toPx(), cliche.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )
        //top right vertical line
        drawLine(
            start = Offset(w.toPx() - wide.toPx(), y),
            end = Offset(w.toPx() - wide.toPx(), cliche.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )

        //bottom left vertical line
        drawLine(
            start = Offset(wide.toPx(), h.toPx() - cliche.toPx()),
            end = Offset(wide.toPx(), h.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )
        //bottom right vertical line
        drawLine(
            start = Offset(w.toPx() - wide.toPx(), h.toPx() - cliche.toPx()),
            end = Offset(w.toPx() - wide.toPx(), h.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )

        //top shorter horizontal line - top Wicket
        drawLine(
            start = Offset(5 * wide.toPx(), wide.toPx()),
            end = Offset(w.toPx() - 5 * wide.toPx(), wide.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )
        //bottom shorter horizontal line - bottom wicket
        drawLine(
            start = Offset(5 * wide.toPx(), h.toPx() - wide.toPx()),
            end = Offset(w.toPx() - 5 * wide.toPx(), h.toPx() - wide.toPx()),
            color = Color.White,
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Preview
@Composable
private fun DrawCricketGroundPreview() {
    ComposeTheme {
        DrawCricketGround(rememberNavController())
    }
}