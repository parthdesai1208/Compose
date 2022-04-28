package com.parthdesai1208.compose.view.draw

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.ComposeTheme


@Composable
fun DrawLine() {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .drawBehind {
                val strokeWidth = 2f
                val x = size.width - strokeWidth
                val y = size.height - strokeWidth

                //top line
                drawLine(
                    color = Color.Green,
                    start = Offset(0f, 0f), //(0,0) at top-left point of the box
                    end = Offset(x, 0f), //top-right point of the box
                    strokeWidth = strokeWidth
                )

                //left line
                drawLine(
                    color = Color.Magenta,
                    start = Offset(0f, 0f), //(0,0) at top-left point of the box
                    end = Offset(0f, y),//bottom-left point of the box
                    strokeWidth = strokeWidth
                )

                //right line
                drawLine(
                    color = Color.Red,
                    start = Offset(x, 0f),// top-right point of the box
                    end = Offset(x, y),// bottom-right point of the box
                    strokeWidth = strokeWidth
                )

                //bottom line
                drawLine(
                    color = Color.Cyan,
                    start = Offset(0f, y),// bottom-left point of the box
                    end = Offset(x, y),// bottom-right point of the box
                    strokeWidth = strokeWidth
                )
            }) {
        Column(modifier = Modifier.padding(2.dp)) {
            Text(text = "DrawLines", color = MaterialTheme.colors.onSurface)
            Text(text = "Using DrawBehind", color = MaterialTheme.colors.onSurface)
        }
    }
}

@Preview(name = "Light", showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DrawLinePreview() {
    ComposeTheme {
        DrawLine()
    }
}