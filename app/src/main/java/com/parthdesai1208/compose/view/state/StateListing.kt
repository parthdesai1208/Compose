package com.parthdesai1208.compose.view.state

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
import com.parthdesai1208.compose.R

enum class StateListingEnumType(val buttonTitle: Int, val func: @Composable () -> Unit) {
    LearnState(R.string.learnstate, {
        TodoActivityScreen(androidx.lifecycle.viewmodel.compose.viewModel())
    }),
    DependentVariableState(
        R.string.dependentVariableState,
        { DependentVariableState(androidx.lifecycle.viewmodel.compose.viewModel()) })
}

object StateDestinations {
    const val STATE_LISTING_MAIN_SCREEN = "STATE_LISTING_MAIN_SCREEN"
    const val STATE_LISTING_MAIN_SCREEN_ROUTE_PREFIX = "STATE_LISTING_MAIN_SCREEN_ROUTE_PREFIX"
    const val STATE_LISTING_MAIN_SCREEN_ROUTE_POSTFIX = "STATE_LISTING_MAIN_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun StateListingNavGraph(
    startDestination: String = StateDestinations.STATE_LISTING_MAIN_SCREEN
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = StateDestinations.STATE_LISTING_MAIN_SCREEN) {
            MainScreen(navController = navController)
        }
        composable(
            route = "${StateDestinations.STATE_LISTING_MAIN_SCREEN_ROUTE_PREFIX}/{${StateDestinations.STATE_LISTING_MAIN_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(StateDestinations.STATE_LISTING_MAIN_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
                /*type: NavType<Any?>, <-- type of the arguments
                isNullable: Boolean,   <-- type of the arguments is nullable or not
                defaultValue: Any?,    <-- default value if you want to provide
                defaultValuePresent: Boolean*/ //<-- default value is present or not
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildScreen(arguments.getString(StateDestinations.STATE_LISTING_MAIN_SCREEN_ROUTE_POSTFIX))
        }

    }
}

@Composable
fun ChildScreen(onClickButtonTitle: String?) {
    enumValues<StateListingEnumType>().first { it.buttonTitle.toString() == onClickButtonTitle }.func.invoke()
}

@Composable
fun MainScreen(navController: NavHostController) {

    @Composable
    fun MyButton(
        title: StateListingEnumType,
    ) {
        val context = LocalContext.current

        Button(
            onClick = {
                navController.navigate("${StateDestinations.STATE_LISTING_MAIN_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(
                context.getString(title.buttonTitle),
                textAlign = TextAlign.Center
            )
        }
    }
    Surface {
        Column {
            Text(
                text = "State Samples",
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
                enumValues<StateListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}