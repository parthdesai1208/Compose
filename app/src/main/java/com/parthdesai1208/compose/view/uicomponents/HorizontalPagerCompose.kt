package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

data class TouristDestination(val imageResId: Int, val destination: String)

val imageList = listOf(
    TouristDestination(R.drawable.parallax1, "Puducherry"),
    TouristDestination(R.drawable.parallax2, "Jaisalmer"),
    TouristDestination(R.drawable.parallax3, "Shillong"),
    TouristDestination(R.drawable.parallax4, "Mumbai"),
    TouristDestination(R.drawable.parallax5, "Bengaluru"),
)

private val cardPadding = 25.dp

private val shadowElevation = 15.dp
private val borderRadius = 15.dp
private val shape = RoundedCornerShape(borderRadius)

private val xOffset = cardPadding.value * 2

@Composable
fun ParallaxEffectUsingHorizontalPager1(
    navHostController: NavHostController, modifier: Modifier = Modifier
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
                        pagerState.getOffsetDistanceInPages(page) * (screenWidth.value * 0.15f)

                    ParallaxCarouselItem(
                        imageList[page].imageResId,
                        parallaxOffset,
                        pagerHeight,
                        screenWidth,
                        density
                    )
                }
            }
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

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
            .padding(cardPadding)
            .background(Color.Red, shape)
            .shadow(elevation = shadowElevation, shape = shape)
    ) {
        // Canvas for drawing the image with parallax effect
        Canvas(
            modifier = Modifier
                .fillMaxSize()
//                .padding(imagePadding)
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
    density: Float, screenWidth: Dp, pagerHeight: Dp
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


@Preview
@Composable
private fun ParallaxEffectUsingHorizontalPager1Preview() {
    ParallaxEffectUsingHorizontalPager1(rememberNavController())
}

fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

@Composable
fun ParallaxEffectUsingHorizontalPager2(
    navHostController: NavHostController, modifier: Modifier = Modifier
) {
    val state = rememberPagerState { imageList.size }

    val scale by remember {
        derivedStateOf {
            1f - (state.currentPageOffsetFraction.absoluteValue) * .3f
        }
    }
    BuildTopBarWithScreen(screen = {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            val offsetFromStart = state.offsetForPage(0).absoluteValue
            Box(modifier = Modifier
                .aspectRatio(1f)
                .offset { IntOffset(0, 150.dp.roundToPx()) }
                .scale(scaleX = .6f, scaleY = .24f)
                .scale(scale)
                .rotate(offsetFromStart * 90f)
                .blur(
                    radius = 110.dp,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded,
                )
                .background(
                    androidx.compose.material.MaterialTheme.colors.onSurface.copy(
                        alpha = .5f
                    )
                ))

            HorizontalPager(
                state = state,
                modifier = Modifier
                    .scale(1f, scaleY = scale)
                    .aspectRatio(1f),
            ) { page ->
                Box(modifier = Modifier
                    .aspectRatio(1f)
                    .graphicsLayer {
                        val pageOffset = state.offsetForPage(page)
                        val offScreenRight = pageOffset < 0f
                        val deg = 105f
                        val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                        rotationY = min(interpolated * if (offScreenRight) deg else -deg, 90f)

                        transformOrigin = TransformOrigin(
                            pivotFractionX = if (offScreenRight) 0f else 1f, pivotFractionY = .5f
                        )
                    }
                    .drawWithContent {
                        val pageOffset = state.offsetForPage(page)

                        this.drawContent()
                        drawRect(
                            Color.Black.copy(
                                (pageOffset.absoluteValue * .7f)
                            )
                        )
                    }
                    .background(Color.LightGray), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = imageList[page].imageResId),
                        contentDescription = stringResource(
                            R.string.image_for_the_article, imageList[page].destination
                        ),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = imageList[page].destination,
                        style = MaterialTheme.typography.h3.copy(
                            color = Color.White, fontWeight = FontWeight.Bold, shadow = Shadow(
                                color = Color.Black.copy(alpha = .6f),
                                blurRadius = 30f,
                            )
                        )
                    )
                }
            }
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

@Preview
@Composable
private fun ParallaxEffectUsingHorizontalPager2Preview() {
    ParallaxEffectUsingHorizontalPager2(rememberNavController())
}