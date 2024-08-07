package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.TruckArtCanvasBackground
import com.parthdesai1208.compose.view.theme.TruckArtPrimaryRectangleBackground
import com.parthdesai1208.compose.view.theme.TruckArtSecondaryRectangleBackground
import com.parthdesai1208.compose.view.theme.purple700
import kotlin.math.cos
import kotlin.math.sin

@Phone
@Composable
fun TruckArtCompose(navHostController: NavHostController) {
    BuildTopBarWithScreen(
        title = stringResource(id = R.string.truckart_pattern),
        screen = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                TruckCanvas {
                    drawArtBannerAt(ArtPosition.TOP)
                    drawArtBannerAt(ArtPosition.CENTER)
                    drawArtBannerAt(ArtPosition.BOTTOM)
                }
            }
        },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}

@Composable
private fun TruckCanvas(onDraw: DrawScope.() -> Unit) {
    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .background(color = TruckArtCanvasBackground)
            .fillMaxWidth()
            .height(320.dp)
    ) {
        onDraw.invoke(this)
    }
}

enum class ArtPosition {
    TOP,
    CENTER,
    BOTTOM
}

fun DrawScope.drawArtBannerAt(artPosition: ArtPosition) {
    when (artPosition) {
        ArtPosition.TOP -> {
            drawTopBannerArt()
        }

        ArtPosition.CENTER -> {
            drawCenterBannerArt()
        }

        else -> {
            drawBottomBannerArt()
        }
    }
}

fun DrawScope.drawTopBannerArt() {
    drawArtBannerCore(
        color = TruckArtSecondaryRectangleBackground,
        height = 60.dp,
        topMargin = 5.dp
    ) {
        drawDashedLine(        //first dashed line
            marginTop = 15.dp,
            marginStart = 5.dp
        )
        drawDiamondsPattern(marginTop = 35.dp) //top diamond line
        drawDashedLine(     //second dashed line
            marginTop = 60.dp - 5.dp,
            marginStart = 5.dp
        )
    }
}

fun DrawScope.drawArtBannerCore(
    color: Color,
    topMargin: Dp = 0.dp,
    height: Dp,
    onDraw: (DrawScope) -> Unit,
) {
    drawRect(
        color = color,
        topLeft = Offset(0f, topMargin.toPx()),
        size = Size(size.width, height.toPx())
    )
    onDraw.invoke(this)
}

fun DrawScope.drawCenterBannerArt() {
    drawArtBannerCore(
        color = TruckArtPrimaryRectangleBackground,
        topMargin = 70.dp,
        height = 180.dp
    ) {
        //I want to draw this much flowers on main banner of the canvas
        val weightSum = 5

        //Finds the width for each flower
        val eachFlowerWidth = size.width / weightSum

        //Finds the X Offset at the center of individual item width.
        var positionX = eachFlowerWidth / 2

        repeat(weightSum) {
            drawPrimaryFlowerArt(positionX, center.y)  //single flower

            //Moves the drawing control to the next item X Offset center position
            positionX += eachFlowerWidth
        }
    }
}

fun DrawScope.drawPrimaryFlowerArt(positionX: Float = center.x, positionY: Float = center.y) {
    val circleRadius = 50f
    val leafCount = 7
    val angle = (360 / leafCount).toFloat()

    for (i in 1..leafCount) {
        val angleInDegrees = angle * i
        val theta = (Math.PI * angleInDegrees) / 180

        val x = circleRadius * cos((theta).toFloat())
        val y = circleRadius * sin((theta).toFloat())

        drawPrimaryFlowerLeaf(
            positionX + x,
            positionY - y
        ) //outer circle around center circle in flower (7 leaf around flower)
    }
    drawPrimaryFlower(positionX, positionY)     //center circle in flower
}

private fun DrawScope.drawPrimaryFlowerLeaf(x: Float, y: Float) {
    val leafRadius = 20f
    drawCircle(
        //outer white circle
        color = Color.White,
        center = Offset(x, y),
        radius = leafRadius,
        style = Stroke(width = 20f),
    )
    drawCircle(
        //inner circle
        brush = Brush.radialGradient(listOf(TruckArtSecondaryRectangleBackground, purple700)),
        center = Offset(x, y),
        radius = leafRadius,
    )
}

private fun DrawScope.drawPrimaryFlower(positionX: Float = center.x, positionY: Float = center.y) {
    val circleRadius = 35f

    drawCircle(
        //2nd circle from top in flower
        color = TruckArtSecondaryRectangleBackground,
        center = Offset(positionX, positionY),
        radius = circleRadius,
    )

    drawCircle(
        //3rd circle from top in flower
        color = Color.White,
        center = Offset(positionX, positionY),
        style = Stroke(width = 10f),
        radius = circleRadius,
    )

    drawCircle(
        //1st circle from top in flower
        color = Color.Yellow,
        center = Offset(positionX, positionY),
        radius = circleRadius / 2,
    )
}

fun DrawScope.drawDashedLine(
    marginTop: Dp,
    marginStart: Dp,
    strokeWidth: Float = 10f,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
) {
    drawLine(
        color = Color.White,
        start = Offset(marginStart.toPx(), marginTop.toPx()),
        end = Offset(size.width - marginStart.toPx(), marginTop.toPx()),
        strokeWidth = strokeWidth,
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(0f, 30f),
            phase = 20f
        ),
        cap = StrokeCap.Round,
        blendMode = blendMode
    )
}

fun DrawScope.drawDiamondsPattern(marginTop: Dp) {
    val diamondsCount = 10
    val itemWidth = (size.width) / diamondsCount
    val diamondRadius = itemWidth / 3
    var marginLeft = itemWidth / 2
    val diamondSides = 4

    repeat(diamondsCount) {
        //draw diamond
        drawPath(
            path = createPathForDiamond(
                marginLeft,
                marginTop.toPx(),
                diamondSides,
                diamondRadius
            ),
            color = Color.Yellow
        )
        if (it > 0) {
            //draw dot beside diamond
            drawCircle(
                color = Color.Black,
                radius = 8f,
                center = Offset(itemWidth.times(it), marginTop.toPx())
            )
        }
        marginLeft = marginLeft.plus(itemWidth)
    }
}

private fun createPathForDiamond(
    marginLeft: Float,
    marginTop: Float,
    sides: Int = 4,
    radius: Float,
): Path {
    val path = Path()
    val angle = 2.0 * Math.PI / sides
    path.moveTo(
        marginLeft + (radius * cos(0.0)).toFloat(),
        marginTop + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        path.lineTo(
            marginLeft + (radius * cos(angle * i)).toFloat(),
            marginTop + (radius * sin(angle * i)).toFloat()
        )
    }
    path.close()
    return path
}

fun DrawScope.drawBottomBannerArt() {
    drawArtBannerCore(
        color = TruckArtSecondaryRectangleBackground,
        topMargin = 255.dp,
        height = 60.dp
    ) {
        drawDashedLine(     //3rd dashed line
            marginTop = 255.dp + 10.dp,
            marginStart = 5.dp
        )
        drawDiamondsPattern(marginTop = 320.dp - 35.dp)     //bottom diamond line
        drawDashedLine(         //4th dashed line
            marginTop = 320.dp - 15.dp,
            marginStart = 5.dp
        )
    }
}