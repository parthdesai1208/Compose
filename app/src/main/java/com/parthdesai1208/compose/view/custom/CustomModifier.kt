package com.parthdesai1208.compose.view.custom

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Constant.CUSTOM_MODIFIER_ROTATING_ANIMATE_FLOAT
import com.parthdesai1208.compose.utils.Constant.CUSTOM_MODIFIER_ROTATING_ANIMATION
import com.parthdesai1208.compose.utils.rotateOnClickComposable
import com.parthdesai1208.compose.utils.rotateOnClickComposed
import com.parthdesai1208.compose.utils.setSizeByScreenPercentage
import com.parthdesai1208.compose.view.navigation.CustomModifierScreen
import com.parthdesai1208.compose.view.theme.ComposeTheme

//region Custom Modifier listing screen
enum class CustomModifierListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    CustomModifierScreen(
        R.string.baseLineToTop, { BaseLineToTopFun(it) }),
    RotateAnyComposeDemonstration(R.string.rotateComposable, { RotateAnyComposeDemonstration(it) }),
    ComposableVersusComposed(R.string.composableVersusComposed, { ComposableVersusComposed(it) }),
}

@Composable
fun CustomModifierListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: CustomModifierListingEnumType
    ) {
        val context = LocalContext.current
        Button(
            onClick = { navController.navigate(CustomModifierScreen(pathPostFix = title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.custom_modifier_samples),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<CustomModifierListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}

@Composable
fun CustomModifierListingScreen1(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<CustomModifierListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}
//endregion

//region baseLineToTop
@Composable
fun BaseLineToTopFun(navHostController: NavHostController) {
    BuildTopBarWithScreen(title = stringResource(id = R.string.baseLineToTop), screen = {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.with_baselinetotop_32_dp),
                Modifier
                    .baseLineToTop(32.dp)
                    .wrapContentWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.without_baselinetotop_32_dp_paddingtop),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                textAlign = TextAlign.Center
            )
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

fun Modifier.baseLineToTop(firstBaselineToTop: Dp): Modifier {

    return this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints = constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // now we minus total padding from firstbaseline, so we get it from baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        // now we have to draw box with height of the layout + value that we get from minus operation
        val height = placeable.height + placeableY
        //apply height to layout
        layout(placeable.width, height) {
            // we place layout in box
            placeable.placeRelative(0, placeableY)
        }
    }
}

@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeTheme {
        Text(stringResource(R.string.hi_there), Modifier.baseLineToTop(32.dp))
    }
}

@Composable
fun TextWithNormalPaddingPreview() {
    ComposeTheme {
        Text(
            text = stringResource(id = R.string.hi_there),
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}
//endregion

//region rotate any compose using custom modifier
@Composable
fun RotateAnyComposeDemonstration(navHostController: NavHostController) {
    BuildTopBarWithScreen(title = stringResource(id = R.string.rotateComposable), screen = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .setSizeByScreenPercentage(40f, 15f)
                    .rotating(duration = 3000) //Note: here order is every important
                    .redRectangleBox()
            )
        }
    }, onBackIconClick = {
        navHostController.popBackStack()
    })
}

fun Modifier.redRectangleBox(): Modifier = clip(RectangleShape).background(color = Color.Red)

fun Modifier.rotating(duration: Int): Modifier = composed {
    val transition = rememberInfiniteTransition(label = CUSTOM_MODIFIER_ROTATING_ANIMATION)
    val angelRatio by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = FastOutLinearInEasing)
        ), label = CUSTOM_MODIFIER_ROTATING_ANIMATE_FLOAT
    )

    graphicsLayer(rotationZ = 360f * angelRatio)
}
//endregion

//region @Composable v/s composed { }
@Composable
fun ComposableVersusComposed(navHostController: NavHostController) {
    BuildTopBarWithScreen(title = stringResource(id = R.string.composableVersusComposed), screen = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = stringResource(R.string.composable_resolves_state_only_once_at_the_call_site)
            )
            val modifier1 = Modifier
                .rotateOnClickComposable()
                .size(100.dp)
            LazyRow {
                items(10) {
                    Box(
                        modifier = modifier1,
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = stringResource(R.string.composable))
                    }
                }
            }
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = stringResource(R.string.composed_resolves_state_at_the_usage_site_for_each_layout_so_if_we_click_on_any_of_the_box_other_box_won_t_get_affected)
            )
            val modifier2 = Modifier
                .rotateOnClickComposed()
                .size(100.dp)
            LazyRow {
                items(10) {
                    Box(
                        modifier = modifier2,
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = stringResource(R.string.composed))
                    }
                }
            }
        }
    }) {
        navHostController.popBackStack()
    }
}
//endregion