package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.darkStadiumGreen
import com.parthdesai1208.compose.view.theme.lightStadiumGreen

@Composable
fun DrawFootballGround(navHostController: NavHostController, modifier: Modifier = Modifier) {
    BuildTopBarWithScreen(
        screen = {
            Canvas(modifier = modifier.fillMaxSize()) {
                val screenSizes = size.height
                val rectangleHeight = screenSizes / 10

                for (i in 0..10) {
                    val color = if (i % 2 == 0) lightStadiumGreen else darkStadiumGreen
                    //green lines on field
                    drawRect(
                        color = color,
                        topLeft = Offset(0f, i * rectangleHeight),
                        size = Size(size.width, rectangleHeight)
                    )
                }

                val pitchOutLine = AndroidPath().apply {
                    moveTo(50f, 50f)
                    lineTo(size.width - 50f, 50f)
                    lineTo(size.width - 50f, size.height - 50f)
                    lineTo(50f, size.height - 50f)
                    lineTo(50f, 50f)
                    close()
                    moveTo(50f, size.height / 2)
                    lineTo(size.width - 50f, size.height / 2)
                    close()
                }
                //outer rectangle border & middle horizontal line
                drawPath(path = pitchOutLine, color = Color.White, style = Stroke(3.dp.toPx()))
                //middle dot only
                drawCircle(
                    color = Color.White, radius = 10f, center = Offset(
                        size.width / 2,
                        size.height / 2
                    )
                )
                //middle circle
                drawCircle(
                    color = Color.White,
                    radius = 100f,
                    center = Offset(size.width / 2, size.height / 2),
                    style = Stroke(3.dp.toPx())
                )
                //top left arc
                drawArc(
                    color = Color.White,
                    startAngle = 0f,
                    sweepAngle = 90f,
                    size = Size(100f, 100f),
                    topLeft = Offset(0f, 0f),
                    style = Stroke(3.dp.toPx()),
                    useCenter = false
                )
                //top right arc
                drawArc(
                    color = Color.White,
                    startAngle = 90f,
                    sweepAngle = 90f,
                    size = Size(100f, 100f),
                    topLeft = Offset(size.width - 100f, 0f),
                    style = Stroke(3.dp.toPx()),
                    useCenter = false
                )
                //bottom right arc
                drawArc(
                    color = Color.White,
                    startAngle = 180f,
                    sweepAngle = 90f,
                    size = Size(100f, 100f),
                    topLeft = Offset(size.width - 100f, size.height - 100f),
                    style = Stroke(3.dp.toPx()),
                    useCenter = false
                )
                //bottom left arc
                drawArc(
                    color = Color.White,
                    startAngle = 270f,
                    sweepAngle = 90f,
                    size = Size(100f, 100f),
                    topLeft = Offset(0f, size.height - 100f),
                    style = Stroke(3.dp.toPx()),
                    useCenter = false
                )

                //Penalty
                //top first box
                drawRect(
                    color = Color.White,
                    topLeft = Offset(size.width.div(2).minus(100f), 50f),
                    size = Size(200f, 100f),
                    style = Stroke(3.dp.toPx())
                )
                //bottom second box
                drawRect(
                    color = Color.White,
                    topLeft = Offset(size.width.div(2).minus(100f), size.height - 150f),
                    size = Size(200f, 100f),
                    style = Stroke(3.dp.toPx())
                )
                //top second box
                drawRect(
                    color = Color.White,
                    topLeft = Offset((size.width / 2) - 300f, 50f),
                    size = Size(600f, 300f),
                    style = Stroke(3.dp.toPx())
                )
                //bottom first box
                drawRect(
                    color = Color.White,
                    topLeft = Offset((size.width / 2) - 300f, size.height - 350f),
                    size = Size(600f, 300f),
                    style = Stroke(3.dp.toPx())
                )
                //top penalty area dot
                drawCircle(
                    color = Color.White,
                    radius = 10f,
                    center = Offset(size.width / 2, 200f)
                )
                //bottom penalty area dot
                drawCircle(
                    color = Color.White,
                    radius = 10f,
                    center = Offset(size.width / 2, size.height - 200f)
                )
            }
        }, onBackIconClick = {
            navHostController.popBackStack()
        })
}

@Phone
@Composable
private fun DrawFootballGroundPreview() {
    ComposeTheme {
        DrawFootballGround(rememberNavController())
    }
}