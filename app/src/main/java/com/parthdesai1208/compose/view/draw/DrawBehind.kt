package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.ComposeTheme


@Composable
fun DrawLine(navHostController: NavHostController) {
    BuildTopBarWithScreen(
        title = stringResource(id = R.string.drawline), screen = {
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
                    Text(text = stringResource(R.string.drawlines))
                    Text(text = stringResource(R.string.using_drawbehind))
                }
            }
        },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}

@Phone
@Composable
fun DrawLinePreview() {
    ComposeTheme {
        DrawLine(rememberNavController())
    }
}