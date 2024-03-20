package com.parthdesai1208.compose.view.custom

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.utils.rotateOnClickComposable
import com.parthdesai1208.compose.utils.rotateOnClickComposed
import com.parthdesai1208.compose.view.theme.ComposeTheme

//region Custom Modifier listing screen
enum class CustomModifierListingEnumType(
    val buttonTitle: String,
    val func: @Composable () -> Unit
) {
    CustomModifierScreen("baseLineToTop", { BaseLineToTopFun() }),
    RotateAnyComposeDemonstration("Rotate composable", { RotateAnyComposeDemonstration() }),
    ComposableVersusComposed("Composable Versus composed", { ComposableVersusComposed() }),
}

object CustomModifierDestinations {
    const val CUSTOM_MODIFIER_MAIN_SCREEN = "CUSTOM_MODIFIER_MAIN_SCREEN"
    const val CUSTOM_MODIFIER_ROUTE_PREFIX = "CUSTOM_MODIFIER_ROUTE_PREFIX"
    const val CUSTOM_MODIFIER_ROUTE_POSTFIX = "CUSTOM_MODIFIER_ROUTE_POSTFIX"
}

@Composable
fun CustomModifierNavGraph(startDestination: String = CustomModifierDestinations.CUSTOM_MODIFIER_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = CustomModifierDestinations.CUSTOM_MODIFIER_MAIN_SCREEN) {
            CustomModifierListingScreen(navController = navController)
        }

        composable(
            route = "${CustomModifierDestinations.CUSTOM_MODIFIER_ROUTE_PREFIX}/{${CustomModifierDestinations.CUSTOM_MODIFIER_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(CustomModifierDestinations.CUSTOM_MODIFIER_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildUIComponentsScreen(arguments.getString(CustomModifierDestinations.CUSTOM_MODIFIER_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun CustomModifierListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: CustomModifierListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${CustomModifierDestinations.CUSTOM_MODIFIER_ROUTE_PREFIX}/${title.buttonTitle}") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(title.buttonTitle, textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Text(
                text = "Custom Modifier Samples",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
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
fun ChildUIComponentsScreen(onClickButtonTitle: String?) {
    enumValues<CustomModifierListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}
//endregion

//region baseLineToTop
@Composable
fun BaseLineToTopFun() {
    Surface {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "with baseLineToTop\n32.dp",
                Modifier
                    .baseLineToTop(32.dp)
                    .wrapContentWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "without baseLineToTop\n32.dp paddingTop",
                modifier = Modifier
                    .padding(top = 32.dp)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

fun Modifier.baseLineToTop(firstBaselineToTop: Dp): Modifier {

    return this.then(
        layout { measurable, constraints ->
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
    )
}

@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.baseLineToTop(32.dp))
    }
}

@Composable
fun TextWithNormalPaddingPreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}
//endregion

//region rotate any compose using custom modifier
@Composable
fun RotateAnyComposeDemonstration() {
    Surface {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .rotating(duration = 3000) //Note: here order is every important
                    .redRectangleBox()
            )
        }
    }
}

fun Modifier.redRectangleBox(): Modifier = clip(RectangleShape).background(color = Color.Red)

fun Modifier.rotating(duration: Int): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val angelRatio by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = FastOutLinearInEasing)
        )
    )

    graphicsLayer(rotationZ = 360f * angelRatio)
}
//endregion

//region @Composable v/s composed { }
@Composable
fun ComposableVersusComposed() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = "@Composable resolves state only once at the call site. so if we click on any of the box, all of the box will rotated."
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
                        Text(text = "Composable")
                    }
                }
            }
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = "composed resolves state at the usage site for each Layout. so if we click on any of the box, other box won't get affected."
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
                        Text(text = "Composed")
                    }
                }
            }
        }
    }
}
//endregion

