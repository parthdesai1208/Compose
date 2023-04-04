package com.parthdesai1208.compose.view.draw

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parthdesai1208.compose.R
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

@OptIn(ExperimentalTextApi::class)
@Composable
fun MeasureText() {
    val textMeasurer = rememberTextMeasurer()
    val longText = stringResource(id = R.string.lorem_ipsum)

    Canvas(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .drawWithCache {
            val measuredText = textMeasurer.measure(
                text = AnnotatedString(text = longText),
                constraints = Constraints.fixedWidth(width = (size.width * 2f / 3f).toInt()),
                style = TextStyle(fontSize = 18.sp)
            )
            onDrawBehind {
                val sizeWithExtraPadding = Size(
                    16.dp.toPx() + measuredText.size.width,
                    16.dp.toPx() + measuredText.size.height
                ) //16.dp just for padding
                drawRect(color = attractions_gmap, size = sizeWithExtraPadding)
                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(x = 8.dp.toPx(), y = 8.dp.toPx())
                ) //topLeft just for padding
                //Note: here order of execution is important
                //so what happen inside onDrawBehind{} block?
                //first it draw rect of text size which we measure in `measuredText`
                //second it will draw text on that `rect`
            }
        }, onDraw = {})
}

//Note: The above example uses Modifier.drawWithCache, since drawing text is an expensive operation.
//Using drawWithCache helps cache the created objects until the size of the drawing area changes.
// For more information, see the Modifier.drawWithCache documentation.
// https://developer.android.com/jetpack/compose/graphics/draw/modifiers#drawwithcache

@OptIn(ExperimentalTextApi::class)
@Composable
fun MeasureTextWithNarrowWidth() {
    val textMeasurer = rememberTextMeasurer()
    val longText = stringResource(id = R.string.lorem_ipsum)

    Canvas(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .drawWithCache {
            val measuredText = textMeasurer.measure(
                text = AnnotatedString(text = longText),
                constraints = Constraints.fixed(
                    width = (size.width / 3f).toInt(),
                    height = (size.height / 3f).toInt()
                ),
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(fontSize = 18.sp)
            )
            onDrawBehind {
                val sizeWithExtraPadding = Size(
                    16.dp.toPx() + measuredText.size.width,
                    16.dp.toPx() + measuredText.size.height
                ) //16.dp just for padding
                drawRect(color = attractions_gmap, size = sizeWithExtraPadding)
                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(x = 8.dp.toPx(), y = 8.dp.toPx())
                ) //topLeft just for padding
                //Note: here order of execution is important
                //so what happen inside onDrawBehind{} block?
                //first it draw rect of text size which we measure in `measuredText`
                //second it will draw text on that `rect`
            }
        }, onDraw = {})
}

@Composable
fun DrawImage() {
    val image = ImageBitmap.imageResource(id = R.drawable.actual_bitmap_image)

    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawImage(image = image)
    })
}

@Composable
fun DrawCircle() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onDraw = {
            drawCircle(color = attractions_gmap)
        }
    )

}

@Composable
fun DrawRoundedRect() {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), onDraw = {
        drawRoundRect(
            color = attractions_gmap,
            cornerRadius = CornerRadius(
                x = 16.dp.toPx(),
                y = 16.dp.toPx()
            ) //give radius to both x & y axis
        )
    })
}

@Composable
fun DrawOval() {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawOval(color = attractions_gmap)
    })
}

@Composable
fun DrawArc() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(weight = 1f)) {
            Canvas(modifier = Modifier
                .fillMaxSize(), onDraw = {
                drawArc(
                    color = attractions_gmap,
                    startAngle = 0f,
                    sweepAngle = 250f,
                    useCenter = false
                )
            })
            Text(
                text = "startAngle = 0f\nsweepAngle = 250f\nuseCenter = false",
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
        Box(modifier = Modifier.weight(weight = 1f)) {
            Canvas(modifier = Modifier
                .fillMaxSize(), onDraw = {
                drawArc(
                    color = attractions_gmap,
                    startAngle = 0f,
                    sweepAngle = 250f,
                    useCenter = true
                )
            })
            Text(
                text = "startAngle = 0f\nsweepAngle = 250f\nuseCenter = true",
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
fun DrawPoint() {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {

        val list = mutableListOf<Offset>()
        repeat(5) {
            list.add(
                Offset(
                    (0f.toInt()..size.width.toInt()).random().toFloat(),
                    (0f.toInt()..size.height.toInt()).random().toFloat()
                )
            )
        }

        drawPoints(
            points = list.toList(),
            pointMode = PointMode.Points,
            color = attractions_gmap,
            strokeWidth = 5.dp.toPx()
        )
    })
}

@Composable
fun DrawPath() {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .drawWithCache {
            val path = Path()
            path.moveTo(0f, 0f)
            path.lineTo(size.width / 2f, size.height / 2f)
            path.lineTo(size.width, 0f)
            path.close()
            path.moveTo(size.width / 2f, size.height / 2f)
            path.lineTo(0f, size.height)
            path.lineTo(size.width, size.height)
            path.close()
            onDrawBehind {
                drawPath(
                    path = path,
                    color = attractions_gmap,
                    style = Stroke(width = 5.dp.toPx())
                )
            }
        }, onDraw = {})
}

@Composable
fun AccessingCanvasObject() {
    val drawable = ShapeDrawable(OvalShape())
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .drawWithContent {
            drawIntoCanvas { canvas ->
                drawable.setBounds(0, 0, size.width.toInt(), size.height.toInt())
                drawable.draw(canvas.nativeCanvas)
            }
        }, onDraw = {})
}