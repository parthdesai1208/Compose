package com.parthdesai1208.compose.view.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SweepGradientShader
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parthdesai1208.compose.view.theme.red1000

@Composable
fun RotatingBorders() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var size by remember { mutableStateOf(Size.Zero) } //initially 0-zero size
            var round by remember { mutableStateOf(false) }
            val cornerPercent by animateIntAsState(if (round) 50 else 8) //change between rectangle & circle
            val shader = SweepGradientShader(
                center = Offset(size.width / 2, size.height / 2),
                colors = listOf(Color.Transparent, red1000),
                // Color.Transparent = background border stroke color
                // red1000 = animating border stroke color
                colorStops = listOf(0.3f, 1.0f),
                //0.3f = length of the Color.Transparent line
                //1.0f = length of the red1000
            )

            val brush = animateBrushRotation(shader = shader, size = size)

            OutlinedButton(
                modifier = Modifier
                    .size(250.dp)
                    .onSizeChanged { //size will change on button click
                        size = Size(it.width.toFloat(), it.height.toFloat())
                    },
                border = BorderStroke(width = 2.dp, brush = brush),
                onClick = { round = !round },
                shape = RoundedCornerShape(cornerPercent)
            ) {
                val displayedShape = if (round) "rectangle" else "circle"
                Text(
                    text = "Rotating Border\nClick for $displayedShape",
                    color = MaterialTheme.colors.onSurface,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
fun animateBrushRotation(shader: Shader, size: Size): ShaderBrush {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val (matrix, brush) = remember(shader, size) {
        android.graphics.Matrix().also {
            shader.getLocalMatrix(it)
        } to ShaderBrush(shader)
    }

    matrix.postRotate(angle, size.width / 2, size.height / 2)
    shader.setLocalMatrix(matrix)

    return brush
}