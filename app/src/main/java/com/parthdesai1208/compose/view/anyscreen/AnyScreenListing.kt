package com.parthdesai1208.compose.view.anyscreen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.parthdesai1208.compose.view.anyscreen.sample1.AnyScreenSample1Activity

enum class AnyScreenListingEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    DemonstrateUIForTabletFoldableDesktop(
        "Tablet,Foldable & Desktop compatible UI\n (sample from codelabs)",
        { NavigateToAnyScreenSample1Activity() }),
    InsetsLearning("Insets", { StatusNavigationBarInsets() }),
}

object AnyScreenDestinations {
    const val DRAW_MAIN_SCREEN = "DRAW_MAIN_SCREEN"
    const val DRAW_SCREEN_ROUTE_PREFIX = "DRAW_SCREEN_ROUTE_PREFIX"
    const val DRAW_SCREEN_ROUTE_POSTFIX = "DRAW_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun AnyScreenListingNavGraph(startDestination: String = AnyScreenDestinations.DRAW_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = AnyScreenDestinations.DRAW_MAIN_SCREEN) {
            AnyScreenListingScreen(navController = navController)
        }

        composable(
            route = "${AnyScreenDestinations.DRAW_SCREEN_ROUTE_PREFIX}/{${AnyScreenDestinations.DRAW_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(AnyScreenDestinations.DRAW_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildUIComponentsScreen(arguments.getString(AnyScreenDestinations.DRAW_SCREEN_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun AnyScreenListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: AnyScreenListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${AnyScreenDestinations.DRAW_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
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
                text = "Any Screen Samples",
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
                enumValues<AnyScreenListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}

@Composable
fun ChildUIComponentsScreen(onClickButtonTitle: String?) {
    enumValues<AnyScreenListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Composable
fun NavigateToAnyScreenSample1Activity() {
    val context = LocalContext.current
    context.startActivity(Intent(context, AnyScreenSample1Activity::class.java))
}