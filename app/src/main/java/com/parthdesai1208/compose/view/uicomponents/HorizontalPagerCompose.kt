package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import kotlin.math.roundToInt

val imageList = listOf(
    R.drawable.parallax1,
    R.drawable.parallax2,
    R.drawable.parallax3,
    R.drawable.parallax4,
    R.drawable.parallax5,
    /*R.drawable.gi1,
    R.drawable.gi2,
    R.drawable.gi3,
    R.drawable.gi4,
    R.drawable.gi5,
    R.drawable.gi6,
    R.drawable.gi7,
    R.drawable.gi8,
    R.drawable.gi10,
    R.drawable.gi11,
    R.drawable.gi12,
    R.drawable.gi13,
    R.drawable.gi14,
    R.drawable.gi15,*/
)

@Composable
fun ParallaxEffectUsingHorizontalPager1(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current.density

    val pagerState = rememberPagerState { imageList.size }

    val pagerHeight = screenHeight / 1.5f

    BuildTopBarWithScreen(screen = {
        Surface {
            Box(Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.75f)
                        .align(Alignment.Center)
                ) { page ->

                    val parallaxOffset =
                        pagerState.getOffsetDistanceInPages(page) * screenWidth.value

                    ParallaxCarouselItem(
                        imageList[page],
                        parallaxOffset,
                        pagerHeight,
                        screenWidth,
                        density
                    )
                }
            }
        }
    },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}

val shape = RoundedCornerShape(15.dp)
private val xOffset = 25.dp.value * 2
private fun Float.toIntPx(density: Float) = (this * density).roundToInt()

@Composable
fun ParallaxCarouselItem(
    imageResId: Int,
    parallaxOffset: Float,
    pagerHeight: Dp,
    screenWidth: Dp,
    density: Float,
) {
    // Load the image bitmap
    val imageBitmap = ImageBitmap.imageResource(id = imageResId)

    // Calculate the draw size for the image
    val drawSize = imageBitmap.calculateDrawSize(density, screenWidth, pagerHeight)

    // Card composable for the item
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(Color.Cyan, shape)
            .shadow(elevation = 15.dp, shape = shape)
    ) {
        // Canvas for drawing the image with parallax effect
        Canvas(
            modifier = Modifier
                .fillMaxSize()
//                .padding(10.dp)
                .clip(shape)
        ) {
            // Translate the canvas for parallax effect
            translate(left = parallaxOffset * density) {
                // Draw the image
                drawImage(
                    image = imageBitmap,
                    srcSize = IntSize(imageBitmap.width, imageBitmap.height),
                    dstOffset = IntOffset(-xOffset.toIntPx(density), 0),
                    dstSize = drawSize,
                )
            }
        }
    }
}

private fun ImageBitmap.calculateDrawSize(
    density: Float,
    screenWidth: Dp,
    pagerHeight: Dp
): IntSize {
    val originalImageWidth = width / density
    val originalImageHeight = height / density

    val frameAspectRatio = screenWidth / pagerHeight
    val imageAspectRatio = originalImageWidth / originalImageHeight

    val drawWidth = xOffset + if (frameAspectRatio > imageAspectRatio) {
        screenWidth.value
    } else {
        pagerHeight.value * imageAspectRatio
    }

    val drawHeight = if (frameAspectRatio > imageAspectRatio) {
        screenWidth.value / imageAspectRatio
    } else {
        pagerHeight.value
    }

    return IntSize(drawWidth.toIntPx(density), drawHeight.toIntPx(density))
}