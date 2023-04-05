package com.parthdesai1208.compose.view.draw

import android.graphics.CornerPathEffect
import android.graphics.DiscretePathEffect
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.*


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

@Composable
fun DrawDashLineFromTopRightToBottomLeft() {
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
                    strokeWidth = 5f,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(
                            30f,
                            10f,
                            10f,
                            10f
                        ), phase = 0f
                    )
                    //The above path effect will draw a line beginning with a 30px dash and 10px space,
                    //followed by 10px dash and a 10px space, repeating this sequence until the end of the line
                )
            })

        Text(
            text = "draw dash-Lines from topRight \nto bottomLeft using \ncoordinate system\ndraw a line beginning with a 30px dash and 10px space\nfollowed by 10px dash and a 10px space\nrepeating this sequence until the end of the line",
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

@Phone
@Composable
fun IconsUsingCanvasDraw() {
    val iconSize = 80.dp
    val horizontalArrangement =
        Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally)
    val rowModifier = Modifier.horizontalScroll(state = rememberScrollState())

    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = rowModifier,
            horizontalArrangement = horizontalArrangement
        ) {
            InstagramIconUsingCanvasDraw(iconSize)
            FacebookIconUsingCanvasDraw(iconSize)
            MessengerIconUsingCanvasDraw(iconSize)
            GooglePhotosIconUsingCanvasDrawing(iconSize)
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = horizontalArrangement
        ) {
            GoogleIconUsingCanvasDrawing(iconSize)
            YoutubeIconUsingCanvasDrawing(iconSize)
            GoogleAssistantIconUsingCanvasDrawing(iconSize)
            GoogleAdsIconUsingCanvasDrawing(iconSize)
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = horizontalArrangement
        ) {
            GoogleVoiceSearchIconUsingCanvasDrawing(iconSize)
            StackoverflowIconUsingCanvasDrawing(iconSize)
            SpotifyIconUsingCanvasDrawing(iconSize)
            TrelloIconUsingCanvasDrawing(iconSize)
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = horizontalArrangement
        ) {
            ZoomIconUsingCanvasDrawing(iconSize)
            AndroidIconUsingCanvasDrawing(iconSize)
            IosWeatherAppIconUsingCanvasDrawing(iconSize)
        }
    }
}

@Composable
fun InstagramIconUsingCanvasDraw(iconSize: Dp) {
    val iconColor = listOf(Color.Yellow, Color.Red, Color.Magenta)
    val brush = Brush.linearGradient(colors = iconColor)

    Canvas(modifier = Modifier.size(iconSize), onDraw = {
        drawRoundRect(
            brush = brush,
            cornerRadius = CornerRadius(60f, 60f),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(brush = brush, radius = 45f, style = Stroke(width = 15f, cap = StrokeCap.Round))
        drawCircle(
            brush = brush,
            radius = 15f,
            center = Offset(size.width * .80f, size.height * .20f)
        )
    })
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun FacebookIconUsingCanvasDraw(iconSize: Dp) {
    val assetManager = LocalContext.current.assets
    val textMeasurer = rememberTextMeasurer()
    val typeface = Typeface.createFromAsset(assetManager, "FACEBOLF.OTF")

    Canvas(modifier = Modifier
        .size(iconSize)
        .drawWithCache {
            val measuredText = textMeasurer.measure(
                text = AnnotatedString(text = "f"),
                style = TextStyle(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 75.sp,
                    fontFamily = FontFamily(typeface)
                )
            )
            onDrawBehind {
                drawRoundRect(color = facebookIconColor, cornerRadius = CornerRadius(20f, 20f))
                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(x = 80f, y = 20f)
                )
            }
        }, onDraw = {})
}

@Composable
fun MessengerIconUsingCanvasDraw(iconSize: Dp) {
    val colors = listOf(messengerColor1, messengerColor2)
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {

        //bottom left triangle
        val trianglePath = Path().let {
            it.moveTo(
                size.width * .20f,
                size.height * .77f
            ) //move to left point where triangle & icon meet
            it.lineTo(size.width * .20f, size.height * 0.95f) //draw line to triangle top
            it.lineTo(
                size.width * .37f,
                size.height * 0.86f
            ) //draw line to right point where triangle & icon meet
            it.close()
            it
        }

        //path which display in middle of the icon in white color
        val electricPath = Path().let {
            it.moveTo(
                size.width * .20f,
                size.height * 0.60f
            )   //move to first/starting point of the electric Path
            it.lineTo(size.width * .45f, size.height * 0.35f)   //first up-right direction line
            it.lineTo(size.width * 0.56f, size.height * 0.46f)  //then down-right side line
            it.lineTo(size.width * 0.78f, size.height * 0.35f)  //again up-right side line
            it.lineTo(size.width * 0.54f, size.height * 0.60f)  //down-left side line
            it.lineTo(size.width * 0.43f, size.height * 0.45f)  //again up-left side line
            it.close()                                                //now close it to make electric Path
            it
        }

        drawOval(
            brush = Brush.verticalGradient(colors = colors),
            size = Size(this.size.width, this.size.height * 0.95f)
        )

        drawPath(
            path = trianglePath,
            brush = Brush.verticalGradient(colors = colors),
            style = Stroke(width = 15f)
        )

        drawPath(path = electricPath, color = Color.White)

    }
}

@Composable
fun GooglePhotosIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(modifier = Modifier.size(iconSize), onDraw = {
        //red half circle
        drawArc(
            color = googlePhotosRedColor,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, 0f)
        )
        //blue half circle
        drawArc(
            color = googlePhotosBlueColor,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .50f, size.height * .25f)
        )
        //green half circle
        drawArc(
            color = googlePhotosGreenColor,
            startAngle = 0f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(0f, size.height * .25f)
        )
        //yellow half circle
        drawArc(
            color = googlePhotosYellowColor,
            startAngle = 270f,
            sweepAngle = -180f,
            useCenter = true,
            size = Size(size.width * .50f, size.height * .50f),
            topLeft = Offset(size.width * .25f, size.height * .50f)
        )
    })
}

@Composable
fun GoogleIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(modifier = Modifier
        .size(iconSize)
        .padding(8.dp), onDraw = {
        val canvasWidth = size.width
        val canvasHeight = size.height

        //first/upper horizontal line in logo
        drawRect(
            color = googlePhotosBlueColor,
            size = Size(canvasWidth * .56f, 20f),
            topLeft = Offset(canvasWidth * .55f, canvasHeight * .45f)
        )
        //second/lower horizontal line in logo
        drawRect(
            color = googlePhotosBlueColor,
            size = Size(canvasWidth * .45f, 20f),
            topLeft = Offset(canvasWidth * .55f, canvasHeight * .54f)
        )
        //blue arc
        drawArc(
            color = googlePhotosBlueColor,
            startAngle = 0f,
            sweepAngle = 45f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        //green arc
        drawArc(
            color = googlePhotosGreenColor,
            startAngle = 45f,
            sweepAngle = 135f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        //yellow arc
        drawArc(
            color = googlePhotosYellowColor,
            startAngle = 145f,
            sweepAngle = 80f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
        //red arc
        drawArc(
            color = googlePhotosRedColor,
            startAngle = 205f,
            sweepAngle = 120f,
            useCenter = false,
            style = Stroke(width = 40f)
        )
    })
}

@Composable
fun YoutubeIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {
        //white triangle in middle of the icon
        val path = Path().apply {
            moveTo(size.width * .43f, size.height * .38f) //move to top point
            lineTo(size.width * .72f, size.height * .55f) //draw line to right end point
            lineTo(size.width * .43f, size.height * .73f) //draw line to left bottom point
            close()                                             //close the path to make triangle
        }
        drawRoundRect(
            color = Color.Red,
            cornerRadius = CornerRadius(40f, 40f),
            size = Size(size.width, size.height * .70f),
            topLeft = Offset(size.width.times(.0f), size.height.times(.20f))
        )
        drawPath(color = Color.White, path = path)
    }
}

@Composable
fun GoogleAssistantIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {
        //base white color circle
        drawCircle(color = Color.White)
        //blue circle
        drawCircle(
            color = googlePhotosBlueColor,
            radius = size.width * .20f,
            center = Offset(size.width * .33f, size.height * .35f)
        )
        //red circle
        drawCircle(
            color = googlePhotosRedColor,
            radius = size.width * .12f,
            center = Offset(size.width * .66f, size.height * .48f)
        )
        //yellow circle
        drawCircle(
            color = googlePhotosYellowColor,
            radius = size.width * .14f,
            center = Offset(size.width * .66f, size.height * .78f)
        )
        //green circle
        drawCircle(
            color = googlePhotosGreenColor,
            radius = size.width * .08f,
            center = Offset(size.width * .84f, size.height * .32f)
        )
    }

}

@Composable
fun GoogleAdsIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {

        //yellow rectangle with rotation
        rotate(30f) {
            drawRoundRect(
                color = googlePhotosYellowColor,
                cornerRadius = CornerRadius(40f, 40f),
                topLeft = Offset(26f, 5f),
                size = Size(size.width * .35f, size.height)
            )
        }
        //blue rectangle with rotation
        rotate(-30f) {
            drawRoundRect(
                color = googlePhotosBlueColor,
                cornerRadius = CornerRadius(40f, 40f),
                topLeft = Offset(size.width.div(2), 0f),
                size = Size(size.width * .35f, size.height)
            )
        }
        //green circle in left rectangle
        drawCircle(color = googlePhotosGreenColor, radius = 35f, center = Offset(30f, 145f))
    }
}

@Composable
fun GoogleVoiceSearchIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {

        /*val paint = android.graphics.Paint().apply {
            color = Color.White.toArgb()
            setShadowLayer(20f, 0f, 0f, Color.DarkGray.copy(alpha = .20f).toArgb())
        }
        this.drawIntoCanvas {
            it.nativeCanvas.drawOval(this.size.width, this.size.height, 0f, 0f, paint)
        }*/
        //base white circle
        drawCircle(color = Color.White)
        //blue rectangle at top of the mic
        drawRoundRect(
            color = googlePhotosBlueColor,
            cornerRadius = CornerRadius(40f, 40f),
            topLeft = Offset(size.width.times(.40f), size.height.times(.20f)),
            size = Size(size.width * .20f, size.height.times(.40f))
        )
        //green rectangle at bottom of the mic
        drawRect(
            color = googlePhotosGreenColor,
            topLeft = Offset(size.width.times(.47f), size.height.times(.72f)),
            size = Size(size.width.times(.08f), size.height.times(.17f))
        )
        //yellow arc at left of mic
        drawArc(
            color = googlePhotosYellowColor,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = size.width.times(.08f)),
            size = Size(size.width.times(.40f), size.height.times(.40f)),
            topLeft = Offset(size.width.times(.30f), size.height.times(.30f))
        )
        //red arc at right of mic
        drawArc(
            color = googlePhotosRedColor,
            startAngle = 0f,
            sweepAngle = 130f,
            useCenter = false,
            style = Stroke(width = size.width.times(.08f)),
            size = Size(size.width.times(.40f), size.height.times(.40f)),
            topLeft = Offset(size.width.times(.30f), size.height.times(.30f))
        )

    }
}

@Composable
fun StackoverflowIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {
        //bottom rectangle at left
        drawRect(
            color = stackoverflowBottomRectangleColor,
            topLeft = Offset(size.width * .10f, size.height * .63f),
            Size(size.width * .10f, size.height * .35f)
        )
        //bottom rectangle at bottom
        drawRect(
            color = stackoverflowBottomRectangleColor,
            topLeft = Offset(size.width * .10f, size.height * .89f),
            Size(size.width * .89f, size.height * .10f)
        )
        //bottom rectangle at right
        drawRect(
            color = stackoverflowBottomRectangleColor,
            topLeft = Offset(size.width * .89f, size.height * .63f),
            Size(size.width * .10f, size.height * .35f)
        )
        //there are 5 stacks
        //bottom stack (5th stack from top)
        rotate(0f) {
            drawRect(
                color = stackoverflowStackColor,
                topLeft = Offset(size.width * .28f, size.height * .72f),
                Size(size.width * .54f, size.height * .10f)
            )
        }
        //4th stack from top
        rotate(6f) {
            drawRect(
                color = stackoverflowStackColor,
                topLeft = Offset(size.width * .30f, size.height * .56f),
                Size(size.width * .54f, size.height * .10f)
            )
        }
        //3rd stack from top
        rotate(15f) {
            drawRect(
                color = stackoverflowStackColor,
                topLeft = Offset(size.width * .30f, size.height * .40f),
                Size(size.width * .54f, size.height * .10f)
            )
        }
        //2nd stack from top
        rotate(24f) {
            drawRect(
                color = stackoverflowStackColor,
                topLeft = Offset(size.width * .26f, size.height * .22f),
                Size(size.width * .54f, size.height * .10f)
            )
        }
        //1st stack from top
        rotate(34f) {
            drawRect(
                color = stackoverflowStackColor,
                topLeft = Offset(size.width * .22f, size.height * .06f),
                Size(size.width * .54f, size.height * .10f)
            )
        }
    }
}

@Composable
fun SpotifyIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            //draw top/first line
            moveTo(width.times(.18f), height.times(.34f))
            cubicTo(
                width.times(.18f),
                height.times(.35f),
                width.times(.45f),
                height.times(.20f),
                width.times(.83f),
                height.times(.40f)
            )
            //draw 2nd line
            moveTo(
                width.times(.28f),
                height.times(.50f),
            )
            cubicTo(
                width.times(.28f),
                height.times(.50f),
                width.times(.45f),
                height.times(.43f),
                width.times(.73f),
                height.times(.55f)
            )
            //draw 3rd/bottom line
            moveTo(
                width.times(.33f),
                height.times(.67f),
            )
            cubicTo(
                width.times(.33f),
                height.times(.67f),
                width.times(.45f),
                height.times(.62f),
                width.times(.67f),
                height.times(.72f)
            )
        }
        //base green circle
        drawCircle(color = spotifyGreenColor)
        //now draw path
        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(width = width.times(.09f), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun TrelloIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {
        //base blue rectangle
        drawRoundRect(color = trelloBlueColor, cornerRadius = CornerRadius(20f, 20f))
        //left rectangle
        drawRoundRect(
            color = Color.White,
            cornerRadius = CornerRadius(10f, 10f),
            size = Size(width = size.width.times(.30f), size.height.times(.60f)),
            topLeft = Offset(size.width.times(.13f), size.height.times(.20f))
        )
        //right rectangle
        drawRoundRect(
            color = Color.White,
            cornerRadius = CornerRadius(10f, 10f),
            size = Size(width = size.width.times(.30f), size.height.times(.45f)),
            topLeft = Offset(size.width.times(.58f), size.height.times(.20f))
        )
    }
}

@Composable
fun ZoomIconUsingCanvasDrawing(iconSize: Dp) {
    val zoomColors = listOf(zoomIconColor1, zoomIconColor2)

    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {
        val width = size.width
        val height = size.height

        val pathHeart = Path().apply {
            moveTo(width.times(.22f), height.times(.58f))
            lineTo(width.times(.22f), height.times(.37f))
            quadraticBezierTo(
                width.times(.22f),
                height.times(.34f),
                width.times(.25f),
                height.times(.34f)
            )
            lineTo(width.times(.54f), height.times(.34f))
            quadraticBezierTo(
                width.times(.62f),
                height.times(.34f),
                width.times(.62f),
                height.times(.41f)
            )
            lineTo(width.times(.62f), height.times(.62f))
            quadraticBezierTo(
                width.times(.62f),
                height.times(.65f),
                width.times(.58f),
                height.times(.65f)
            )
            lineTo(width.times(.30f), height.times(.65f))
            quadraticBezierTo(
                width.times(.22f),
                height.times(.65f),
                width.times(.22f),
                height.times(.58f)
            )
            moveTo(width.times(.65f), height.times(.44f))
            lineTo(width.times(.65f), height.times(.56f))
            lineTo(width.times(.75f), height.times(.65f))
            lineTo(width.times(.75f), height.times(.35f))
            lineTo(width.times(.65f), height.times(.44f))
            close()
        }
        //base blue rectangle
        drawRoundRect(
            brush = Brush.verticalGradient(zoomColors),
            cornerRadius = CornerRadius(60f, 60f)
        )
        //draw middle video camera icon
        drawPath(pathHeart, color = Color.White)
    }
}

@Composable
fun AndroidIconUsingCanvasDrawing(iconSize: Dp) {
    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {

        val height = this.size.height
        val width = this.size.width

        //android head
        drawArc(
            color = androidIconColor,
            startAngle = 0f,
            useCenter = false,
            sweepAngle = -180f,
            size = Size(width = width.times(.60f), height = height.times(.55f)),
            topLeft = Offset(width.times(.25f), height.times(.30f))
        )
        //left hand
        drawLine(
            color = androidIconColor,
            start = Offset(width.times(.40f), height.times(.42f)),
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            end = Offset(width.times(.29f), height.times(.29f))
        )
        //right hand
        drawLine(
            color = androidIconColor,
            start = Offset(width.times(.68f), height.times(.40f)),
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            end = Offset(width.times(.76f), height.times(.27f))
        )
        //left eye
        drawCircle(
            color = Color.White,
            radius = width.times(.04f),
            center = Offset(width.times(.45f), height.times(.45f))
        )
        //right eye
        drawCircle(
            color = Color.White, radius = width.times(.04f),
            center = Offset(width.times(.64f), height.times(.45f)),
        )
    }
}

@Composable
fun IosWeatherAppIconUsingCanvasDrawing(iconSize: Dp) {
    val backgroundColor = listOf(weatherIconBgUpperColor, weatherIconBgBottomColor)
    val sunColor = listOf(weatherIconSunColor, weatherIconSunOverlappingColor)

    Canvas(
        modifier = Modifier
            .size(iconSize)
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(width.times(.76f), height.times(.72f)) //starting point of cloud at left
            //first curv from left
            cubicTo(
                width.times(.93f),
                height.times(.72f),
                width.times(.98f),
                height.times(.41f),
                width.times(.76f),
                height.times(.40f)
            )
            //second curv from left
            cubicTo(
                width.times(.75f),
                height.times(.21f),
                width.times(.35f),
                height.times(.21f),
                width.times(.38f),
                height.times(.50f)
            )
            //third curv from left & first curb from right
            cubicTo(
                width.times(.25f),
                height.times(.50f),
                width.times(.20f),
                height.times(.69f),
                width.times(.41f),
                height.times(.72f)
            )
            close() //close the path to form cloud
        }
        //base blue rectangle
        drawRoundRect(
            brush = Brush.verticalGradient(backgroundColor),
            cornerRadius = CornerRadius(50f, 50f),

            )
        //sun
        drawCircle(
            brush = Brush.verticalGradient(sunColor),
            radius = width.times(.17f),
            center = Offset(width.times(.35f), height.times(.35f))
        )
        //cloud
        drawPath(path = path, color = Color.White.copy(alpha = .90f))
    }
}

@Composable
fun DrawTriangleWaterDropletWithCornerPathEffects() {
    val dropColor = MaterialTheme.colors.onSurface

    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        val rect = Rect(Offset.Zero, size)

        val trianglePath = Path().apply {
            moveTo(rect.topCenter.x, rect.topCenter.y)
            lineTo(rect.bottomRight.x, rect.bottomRight.y)
            lineTo(rect.bottomLeft.x, rect.bottomLeft.y)
            close()
        }

        drawIntoCanvas {
            it.drawOutline(outline = Outline.Generic(trianglePath), paint = Paint().apply {
                color = dropColor
                pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 2)
            })
        }
    })
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ChainPathEffectSimpleExample() {

    val dashPathEffect = remember {
        PathEffect.dashPathEffect(intervals = floatArrayOf(20f, 10f), 0f)
    }

    val cornerPathEffect = remember {
        PathEffect.cornerPathEffect(80f)
    }

    Text(
        text = "This is chainPathEffect apply to text compose",
        style = TextStyle(
            color = stackoverflowStackColor,
            fontSize = 80.sp,
            drawStyle = Stroke(
                width = 6f, join = StrokeJoin.Round,
                pathEffect = PathEffect.chainPathEffect(
                    outer = dashPathEffect,
                    inner = cornerPathEffect
                )
            )
        )

    )
}

@Composable
fun GooeyEffectUsingChainPathEffect() {
    val pathDynamic = remember { Path() }
    val pathStatic = remember { Path() }

    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    val segmentCount = 20
    val pathMeasure = remember { PathMeasure() }

    val paint = remember { Paint() }

    var isPaintSetUp by remember { mutableStateOf(false) }

    Canvas(modifier = Modifier
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                currentPosition = change.position
            }
        }
        .fillMaxSize(), onDraw = {
        val center = size.center

        val position = if (currentPosition == Offset.Unspecified) {
            center
        } else currentPosition

        pathDynamic.reset() //clear path
        pathDynamic.addOval( //add movable circle
            Rect(
                center = position,
                radius = 150f
            )
        )

        pathStatic.reset()  //clear path
        pathStatic.addOval( //add fix circle in center of the screen
            Rect(
                center = Offset(center.x, center.y),
                radius = 100f
            )
        )

        pathMeasure.setPath(pathDynamic, true) //assign new path

        //apply effect to big movable circle
        //0f as deviation means pattern is regular and evenly spaced
        val discretePathEffect = DiscretePathEffect(pathMeasure.length / segmentCount, 0f)
        //add full circle effect to two circles
        val cornerPathEffect = CornerPathEffect(50f)

        //chainPathEffect will provide effect to inner path effect & then outer effect to all inner path effect
        val chainPathEffect = PathEffect.chainPathEffect(
            outer = cornerPathEffect.toComposePathEffect(), //toComposePathEffect() convert CornerPathEffect -> PathEffect
            inner = discretePathEffect.toComposePathEffect() //toComposePathEffect() convert DiscretePathEffect -> PathEffect
        )
        if (!isPaintSetUp) { //this condition make sure only one time paint will apply

            paint.shader = LinearGradientShader(
                from = Offset.Zero,                     //start range when first color applied
                to = Offset(size.width, size.height),   //end range when second color applied
                colors = listOf(
                    Color(0xffFFEB3B),   //color applied when you drag big circle to top
                    Color(0xffE91E63)    //color applied when you drag big circle to bottom
                ),
                tileMode = TileMode.Clamp
            )
            isPaintSetUp = true
            paint.pathEffect = chainPathEffect
        }

        //combine two paths since we want to draw path into canvas & want it only one
        val newPath = Path.combine(PathOperation.Union, pathDynamic, pathStatic)

        drawIntoCanvas {
            it.drawPath(path = newPath, paint = paint)
        }
    })
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun StampedPathEffectExample() {

    val heartPath = remember {
        Path().apply {
            val width = 20f
            val height = 20f
            moveTo(width / 2, height / 4)
            cubicTo(width / 4, 0f, 0f, height / 3, width / 4, height / 2)
            lineTo(width / 2, height * 3 / 4)
            lineTo(width * 3 / 4, height / 2)
            cubicTo(width, height / 3, width * 3 / 4, 0f, width / 2, height / 4)
        }
    }

    val stampEffect = remember {
        PathEffect.stampedPathEffect(
            shape = heartPath, advance = 20f, phase = 10f, style = StampedPathEffectStyle.Translate
            //advance = Spacing between each heart shape
            //phase = Amount to offset before the first shape is stamped
        )
    }
    Text(
        text = "This is stampedPathEffect apply to text compose",
        style = LocalTextStyle.current.merge(
            TextStyle(
                color = stackoverflowStackColor,
                fontSize = 80.sp,
                drawStyle = Stroke(
                    width = 6f, join = StrokeJoin.Round,
                    pathEffect = stampEffect
                )
            )
        )
    )
}