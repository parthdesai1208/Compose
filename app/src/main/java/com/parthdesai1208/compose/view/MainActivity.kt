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
import androidx.compose.material.Surface
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
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.UserData
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_POSTFIX
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_PREFIX
import com.parthdesai1208.compose.view.accessibility.AccessibilityScreen
import com.parthdesai1208.compose.view.animation.AnimationNavGraph
import com.parthdesai1208.compose.view.anyscreen.AnyScreenListingNavGraph
import com.parthdesai1208.compose.view.custom.CustomLayoutNavGraph
import com.parthdesai1208.compose.view.custom.CustomModifierNavGraph
import com.parthdesai1208.compose.view.migration.MigrationActivity
import com.parthdesai1208.compose.view.navigation.NavigationEx1
import com.parthdesai1208.compose.view.navigation.RallyScreen
import com.parthdesai1208.compose.view.navigation.composeDestination.StartForComposeDestination
import com.parthdesai1208.compose.view.networking.NetworkingListNavGraph
import com.parthdesai1208.compose.view.theme.ComposeTheme

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
    val buttonTitle: Int,
    val func: @Composable () -> Unit,
    val buttonTitleForAccessibility: Int = buttonTitle,
) {
    TextComponents(
        R.string.uicomponents,
        { com.parthdesai1208.compose.view.uicomponents.UIComponentsNavGraph() }),
    LearnStateScreen(R.string.learnstate, {
        Surface {
            TodoActivityScreen(androidx.lifecycle.viewmodel.compose.viewModel())
        }
    }, buttonTitleForAccessibility = R.string.learnstatewithviewmodel),
    CompositionLocal(R.string.learncompositionlocal, { CompositionLocalFun() }),
    CustomModifierScreen(R.string.custommodifier, { CustomModifierNavGraph() }),
    CustomLayout(R.string.customlayout, { CustomLayoutNavGraph() }),
    AnimationScreen(R.string.animationsamples, { AnimationNavGraph() }),
    NavigationEx1(
        R.string.navigationex1, { NavigationEx1() },
        buttonTitleForAccessibility = R.string.navigationexample
    ),
    ComposeDestination(R.string.composedestination, { StartForComposeDestination() }),
    Accessibility(R.string.accessibility, {}),
    Migration(R.string.migrationToCompose, {}),
    DrawScreen(R.string.drawsamples, { com.parthdesai1208.compose.view.draw.DrawNavGraph() }),
    AnyScreen(R.string.anyscreen, { AnyScreenListingNavGraph() }),
    SlotAPI(R.string.slotapi, { SlotAPI() }),
    Networking(R.string.networking, { NetworkingListNavGraph() }),
    GestureScreen(R.string.gesture, { GestureScreen() }),
    PermissionScreen(R.string.permission, { PermissionListNavGraph() })
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
                /*type: NavType<Any?>, <-- type of the arguments
                isNullable: Boolean,   <-- type of the arguments is nullable or not
                defaultValue: Any?,    <-- default value if you want to provide
                defaultValuePresent: Boolean*/ //<-- default value is present or not
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            if (arguments.getString(MAIN_SCREEN_ROUTE_POSTFIX) == R.string.accessibility.toString()) {
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
    enumValues<MainScreenEnumType>().first { it.buttonTitle.toString() == onClickButtonTitle }.func.invoke()
}

@Composable
fun MainScreen(navController: NavHostController) {

    @Composable
    fun MyButton(
        title: MainScreenEnumType,
    ) {
        val context = LocalContext.current

        Button(
            onClick = {
                if (title.buttonTitle == R.string.migrationToCompose) {
                    context.startActivity(Intent(context, MigrationActivity::class.java))
                    return@Button
                }
                navController.navigate("$MAIN_SCREEN_ROUTE_PREFIX/${title.buttonTitle}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle),
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics {
                    contentDescription = context.getString(title.buttonTitleForAccessibility)
                })
        }
    }
    Surface {
        Column {
            Text(
                text = "Compose Samples",
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
                enumValues<MainScreenEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
//endregion