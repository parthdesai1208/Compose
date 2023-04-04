package com.parthdesai1208.compose.view.draw

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.ComposeTheme


enum class DrawListingEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    DrawLines("drawLine", { DrawLine() }), DrawRect("drawRect",
        { DrawRect() }),
    DrawLineFromTopRightToBottomLeft("DrawLine From TopRight To BottomLeft",
        { DrawLineFromTopRightToBottomLeft() }),
    DrawText("DrawText", { DrawText() }),
    MeasureText("Measure Text", { MeasureText() }),
    MeasureTextWithNarrowWidth("Measure Text With Narrow Size", { MeasureTextWithNarrowWidth() }),
    DrawImage("Draw Image", { DrawImage() }),
    DrawCircle("Draw Circle", { DrawCircle() }),
    DrawRoundedRect("Draw RoundedRect", { DrawRoundedRect() }),
    DrawOval("DrawOval", { DrawOval() }),
    DrawArc("DrawArc", { DrawArc() }),
    DrawPoint("DrawPoint", { DrawPoint() }),
    DrawPath("DrawPath", { DrawPath() }),
    AccessingCanvasObject("Accessing Canvas Object", { AccessingCanvasObject() }),
}

object DrawDestinations {
    const val DRAW_MAIN_SCREEN = "DRAW_MAIN_SCREEN"
    const val DRAW_SCREEN_ROUTE_PREFIX = "DRAW_SCREEN_ROUTE_PREFIX"
    const val DRAW_SCREEN_ROUTE_POSTFIX = "DRAW_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun DrawNavGraph(startDestination: String = DrawDestinations.DRAW_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = DrawDestinations.DRAW_MAIN_SCREEN) {
            DrawListingScreen(navController = navController)
        }

        composable(
            route = "${DrawDestinations.DRAW_SCREEN_ROUTE_PREFIX}/{${DrawDestinations.DRAW_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(DrawDestinations.DRAW_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildDrawScreen(arguments.getString(DrawDestinations.DRAW_SCREEN_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun DrawListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: DrawListingEnumType,
    ) {
        Button(
            onClick = { navController.navigate("${DrawDestinations.DRAW_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
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
                text = "Draw Samples",
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
                enumValues<DrawListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}

@Composable
fun ChildDrawScreen(onClickButtonTitle: String?) {
    enumValues<DrawListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Phone
@Composable
fun PreviewDrawNavGraph() {
    ComposeTheme {
        DrawNavGraph()
    }
}