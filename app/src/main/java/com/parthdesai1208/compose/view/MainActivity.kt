package com.parthdesai1208.compose.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_POSTFIX
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_PREFIX
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewUI()
        }
    }

    @Preview(name = "light", showSystemUi = true)
    @Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Preview(
        name = "landscape",
        showSystemUi = true,
        device = Devices.AUTOMOTIVE_1024p,
        widthDp = 720,
        heightDp = 360
    )
    @Composable
    fun PreviewUI() {
        ComposeTheme {
            MainActivityNavGraph()
            //region Learn state
            /*Surface {
                TodoActivityScreen(todoViewModel)
            }*/
            //endregion
        }
    }
}

//region for navigation
object MainDestinations {
    const val MAIN_SCREEN = "mainScreen"
    const val MAIN_SCREEN_ROUTE_PREFIX = "MAIN_SCREEN_ROUTE_PREFIX"
    const val MAIN_SCREEN_ROUTE_POSTFIX = "MAIN_SCREEN_ROUTE_POSTFIX"
}

enum class MainScreenEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    TextInCenter("Text in center", { TextInCenter("Parth") }),
    CollapsableRecyclerviewScreen("recyclerview", { CollapsableRecyclerView() }),
    //LearnStateScreen("Learn state", {  } )
    CustomModifierScreen("custom modifier", { Text("Hi there!", Modifier.baseLineToTop(32.dp).wrapContentWidth().wrapContentHeight(), color = androidx.compose.material.MaterialTheme.colors.onSurface) }),
    CustomRecyclerviewScreen("Custom recyclerview", {
        androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top) {
            com.parthdesai1208.compose.view.StaggeredGridFun(
                modifier = androidx.compose.ui.Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }
    }),
    ConstraintLayoutContent("Constraint Layout Content",{ com.parthdesai1208.compose.view.ConstraintLayoutContent() }),
    ConstraintLayoutScreen("runtime Constraint Layout", { DecoupledConstraintLayout() })
}

@Composable
fun MainActivityNavGraph(startDestination: String = MainDestinations.MAIN_SCREEN) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = MainDestinations.MAIN_SCREEN) {
            MainScreen(actions)
        }
        composable(
            route = "$MAIN_SCREEN_ROUTE_PREFIX/{$MAIN_SCREEN_ROUTE_POSTFIX}",
            arguments = listOf(navArgument(MAIN_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildScreen(arguments.getString(MAIN_SCREEN_ROUTE_POSTFIX))
        }
    }
}

class MainActions(navController: NavHostController) {
    val mainScreen: () -> Unit = {
        navController.navigate(MainDestinations.MAIN_SCREEN)
    }
    val mainScreenToNextScreenOnClick: (String) -> Unit = { routePostFixString ->
        navController.navigate("$MAIN_SCREEN_ROUTE_PREFIX/$routePostFixString")
    }
}

@Composable
fun ChildScreen(onClickButtonTitle: String?) {
    enumValues<MainScreenEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Composable
fun MainScreen(actions: MainActions) {

    @Composable
    fun MyButton(
        title: MainScreenEnumType
    ) {
        Button(
            onClick = { actions.mainScreenToNextScreenOnClick(title.buttonTitle) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            Text(title.buttonTitle)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        enumValues<MainScreenEnumType>().forEach {
            MyButton(it)
        }
    }
}
//endregion