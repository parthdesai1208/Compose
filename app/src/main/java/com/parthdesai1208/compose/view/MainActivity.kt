package com.parthdesai1208.compose.view

import android.app.Application
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.parthdesai1208.compose.ComposeApp
import com.parthdesai1208.compose.model.UserData
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_POSTFIX
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_PREFIX
import com.parthdesai1208.compose.view.animation.AnimationNavGraph
import com.parthdesai1208.compose.view.navigation.NavigationEx1
import com.parthdesai1208.compose.view.navigation.RallyScreen
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.accessibility.AccessibilityScreen
import com.parthdesai1208.compose.view.migration.MigrationActivity

class MainActivity : AppCompatActivity() {

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
            MainActivityNavGraph(application = application)
        }
    }
}

//region for navigation
object MainDestinations {
    const val MAIN_SCREEN = "mainScreen"
    const val MAIN_SCREEN_ROUTE_PREFIX = "MAIN_SCREEN_ROUTE_PREFIX"
    const val MAIN_SCREEN_ROUTE_POSTFIX = "MAIN_SCREEN_ROUTE_POSTFIX"
}

enum class MainScreenEnumType(
    val buttonTitle: String,
    val func: @Composable () -> Unit,
    val buttonTitleForAccessibility: String = buttonTitle,
) {
    TextComponents("UI Components", { com.parthdesai1208.compose.view.uicomponents.UIComponentsNavGraph() }),
    CollapsableRecyclerviewScreen("recyclerview", { CollapsableRecyclerView() }),
    LearnStateScreen("Learn state (VM)", {
        androidx.compose.material.Surface {
            TodoActivityScreen(androidx.lifecycle.viewmodel.compose.viewModel())
        }
    }, buttonTitleForAccessibility = "Learn state with view model"),
    CustomModifierScreen("custom modifier", {
        Text(
            "Hi there!",
            Modifier
                .baseLineToTop(32.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            color = MaterialTheme.colors.onSurface
        )
    }),
    CustomRecyclerviewScreen("Custom recyclerview", {
        Column(verticalArrangement = Arrangement.Top) {
            StaggeredGridFun(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }
    }),
    ConstraintLayoutContent("Constraint Layout Content", { ConstraintLayoutContent() }),
    ConstraintLayoutScreen("runtime Constraint Layout", { DecoupledConstraintLayout() }),
    AnimationScreen("Animation Samples", { AnimationNavGraph() }),
    NavigationEx1(
        "NavigationEx1 with arg,DeepLink",
        { NavigationEx1() },
        buttonTitleForAccessibility = "Navigation Example 1 with arg,DeepLink"
    ),
    Accessibility("Accessibility", {}),
    Migration("Migration to compose", {}),
    DrawScreen("Draw Samples",{ com.parthdesai1208.compose.view.draw.DrawNavGraph() })
}

@Composable
fun MainActivityNavGraph(
    startDestination: String = MainDestinations.MAIN_SCREEN,
    application: Application,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = MainDestinations.MAIN_SCREEN) {
            MainScreen(navController = navController)
        }
        composable(
            route = "$MAIN_SCREEN_ROUTE_PREFIX/{$MAIN_SCREEN_ROUTE_POSTFIX}",
            arguments = listOf(navArgument(MAIN_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            if (arguments.getString(MAIN_SCREEN_ROUTE_POSTFIX) == "Accessibility") {
                val container = (application as ComposeApp).container
                AccessibilityScreen(appContainer = container)
            } else {
                ChildScreen(arguments.getString(MAIN_SCREEN_ROUTE_POSTFIX))
            }
        }

        //region for deep link = https://example.com/task_id=Checking
        composable(
            route = "${RallyScreen.Accounts.name}/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "https://example.com/task_id={name}" })
        ) { entry ->
            val accountName = entry.arguments?.getString("name")
            val account = UserData.getAccount(accountName)
            if (account.name.isNotEmpty()) {
                MainScreenEnumType.NavigationEx1.func.invoke()
            }
        }
        //endregion
    }
}

@Composable
fun ChildScreen(onClickButtonTitle: String?) {
    enumValues<MainScreenEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Composable
fun MainScreen(navController: NavHostController) {

    @Composable
    fun MyButton(
        title: MainScreenEnumType,
    ) {
        val context  = LocalContext.current

        Button(
            onClick = {
                if(title.buttonTitle == "Migration to compose"){
                    context.startActivity(Intent(context,MigrationActivity::class.java))
                    return@Button
                }
                navController.navigate("$MAIN_SCREEN_ROUTE_PREFIX/${title.buttonTitle}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(title.buttonTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics {
                    contentDescription = title.buttonTitleForAccessibility
                })
        }
    }

    Column {
        Text(
            text = "Compose Samples",
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
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
}
//endregion