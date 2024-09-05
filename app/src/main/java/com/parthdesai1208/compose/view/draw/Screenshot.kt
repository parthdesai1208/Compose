package com.parthdesai1208.compose.view.draw

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Constant.INFINITE_ANIMATION_COLOR_SCREENSHOT
import com.parthdesai1208.compose.utils.Constant.REMEMBER_INFINITE_TRANSITION_ANIMATION_COLOR_SCREENSHOT
import com.parthdesai1208.compose.view.theme.ComposeTheme
import kotlinx.coroutines.launch

@Composable
fun Screenshot(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val infiniteTransition =
        rememberInfiniteTransition(label = REMEMBER_INFINITE_TRANSITION_ANIMATION_COLOR_SCREENSHOT)
    val color by infiniteTransition.animateColor(
        initialValue = Red, targetValue = Color.Green, animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
        ), label = INFINITE_ANIMATION_COLOR_SCREENSHOT
    )

    BuildTopBarWithScreen(
        screen = {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    imageBitmap?.let { imageBitmap ->
                        Image(
                            bitmap = imageBitmap,
                            modifier = Modifier.weight(0.5f),
                            contentDescription = stringResource(R.string.this_is_screenshot_that_you_just_took),
                        )
                    } ?: run {
                        Text(stringResource(R.string.screenshot_will_be_captured_here))
                    }
                }
                Box(modifier = Modifier.weight(0.5f)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                graphicsLayer.record {
                                    this@drawWithContent.drawContent()
                                }
                                drawLayer(graphicsLayer)
                            }
                            .clickable {
                                coroutineScope.launch {
                                    val bitmap = graphicsLayer.toImageBitmap()
                                    imageBitmap = bitmap
                                }
                            }
                            .background(color)
                            .align(Alignment.Center)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(R.string.click_here_to_capture_screenshot),
                        color = Color.Black,
                    )
                }
            }
        },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}

@Preview
@Composable
private fun ScreenshotPreview() {
    ComposeTheme {
        Screenshot(rememberNavController())
    }
}