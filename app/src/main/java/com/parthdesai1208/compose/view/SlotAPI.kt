package com.parthdesai1208.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SlotAPI() {
    BorderLayout(
        modifier = Modifier.fillMaxSize(),
        north = {
            Text(
                text = "North",
                modifier = it.background(Color.Blue),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        south = {
            Text(
                "South",
                modifier = it.background(Color.Blue),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
        east = {
            Text(
                "East",
                modifier = it
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .background(Color.Green)
            )
        },
        west = {
            Text(
                "West",
                modifier = it
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .background(Color.Green)
            )
        },
        center = {
            Text(
                "Center",
                modifier = it
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .background(Color.Red),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        },
    )
}

@Composable
fun BorderLayout(
    modifier: Modifier,
    north: @Composable ((Modifier) -> Unit)? = null,
    south: @Composable ((Modifier) -> Unit)? = null,
    east: @Composable ((Modifier) -> Unit)? = null,
    west: @Composable ((Modifier) -> Unit)? = null,
    center: @Composable ((Modifier) -> Unit)? = null,
) {
    Surface {
        Column(modifier = modifier) {
            north?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    it(Modifier.fillMaxWidth())
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                west?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                    ) {
                        it(Modifier.fillMaxHeight())
                    }
                }
                center?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        it(Modifier.fillMaxSize())
                    }
                }
                east?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth()
                    ) {
                        it(Modifier.fillMaxHeight())
                    }
                }
            }
            south?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    it(Modifier.fillMaxWidth())
                }
            }
        }
    }
}