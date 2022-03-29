package com.parthdesai1208.compose.view

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_POSTFIX
import com.parthdesai1208.compose.view.MainDestinations.MAIN_SCREEN_ROUTE_PREFIX
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
    LearnStateScreen("Learn state (VM)", {
        androidx.compose.material.Surface {
            TodoActivityScreen(androidx.lifecycle.viewmodel.compose.viewModel())
        }
    }),
    CustomModifierScreen("custom modifier", {
        Text(
            "Hi there!",
            Modifier
                .baseLineToTop(32.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            color = androidx.compose.material.MaterialTheme.colors.onSurface
        )
    }),
    CustomRecyclerviewScreen("Custom recyclerview", {
        Column(verticalArrangement = Arrangement.Top) {
            StaggeredGridFun(
                modifier = androidx.compose.ui.Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }
    }),
    ConstraintLayoutContent("Constraint Layout Content", { ConstraintLayoutContent() }),
    ConstraintLayoutScreen("runtime Constraint Layout", { DecoupledConstraintLayout() }),
    AnimatedVisibilityWithoutParams("AnimatedVisibility - without params", { com.parthdesai1208.compose.view.animation.AnimatedVisibilityWithoutParams() }),
    AnimatedVisibilityWithParams("AnimatedVisibility - with params", { com.parthdesai1208.compose.view.animation.AnimatedVisibilityWithParams() }),
    AnimateVisibilityState("AnimatedVisibility - with state", { com.parthdesai1208.compose.view.animation.AnimateVisibilityState() }),
    AnimateEnterExitChild("enter exit visibility animation", { com.parthdesai1208.compose.view.animation.AnimateEnterExitChild() }),
    CrossFade("CrossFade", { com.parthdesai1208.compose.view.animation.CrossFade() }),
    AnimatableOnly("AnimatableOnly", { com.parthdesai1208.compose.view.animation.AnimatableOnly() }),
    AnimatedContentSimple("AnimatedContentSimple", { com.parthdesai1208.compose.view.animation.AnimatedContentSimple() }),
    AnimatedContentWithTransitionSpec1("AnimatedContent - with targetState, transitionSpec ex-1",{ com.parthdesai1208.compose.view.animation.AnimatedContentWithTransitionSpec1() }),
    AnimatedContentWithTransitionSpec2("AnimatedContent - with targetState, transitionSpec ex-2", { com.parthdesai1208.compose.view.animation.AnimatedContentWithTransitionSpec2() }),
    AnimatedContentWithTransitionSpec3("AnimatedContent - with targetState, transitionSpec ex-3", { com.parthdesai1208.compose.view.animation.AnimatedContentWithTransitionSpec3() }),
    AnimatedContentSize("AnimatedContentSize", { com.parthdesai1208.compose.view.animation.AnimatedContentSize() }),
    AnimatedContentSizeTransform("AnimatedContentSizeTransform", { com.parthdesai1208.compose.view.animation.AnimatedContentSizeTransform() }),
    AnimateFloatAsState("AnimateFloatAsState", { com.parthdesai1208.compose.view.animation.AnimateFloatAsState() }),
    AnimateColorAsState("AnimateColorAsState", { com.parthdesai1208.compose.view.animation.AnimateColorAsState() }),
    AnimateDpAsState("animateDpAsState", { com.parthdesai1208.compose.view.animation.AnimateDpAsState() }),
    AnimateSizeAsState("AnimateSizeAsState", { com.parthdesai1208.compose.view.animation.AnimateSizeAsState() }),
    UpdateTransition1("updateTransition-1", { com.parthdesai1208.compose.view.animation.UpdateTransitionBasic1() }),
    UpdateTransition2("updateTransition-2", { com.parthdesai1208.compose.view.animation.UpdateTransitionBasic2() }),
    UpdateTransitionChild("UpdateTransitionChild", { com.parthdesai1208.compose.view.animation.UpdateTransitionChild() }),
    UpdateTransitionExtension("multiple anim updateTransition",{ com.parthdesai1208.compose.view.animation.UpdateTransitionExtension() }),
    InfiniteAnimation("InfiniteAnimation", { com.parthdesai1208.compose.view.animation.InfiniteAnimation() }),
    TargetBasedAnimation("TargetBasedAnimation", { com.parthdesai1208.compose.view.animation.TargetBasedAnimationFun() }),
    Spring("spring",{ com.parthdesai1208.compose.view.animation.SpringFun() }),
    Tween("tween", { com.parthdesai1208.compose.view.animation.TweenFun() }),
    Keyframes("keyframes", { com.parthdesai1208.compose.view.animation.KeyFramesFun() }),
    Repeatable("repeatable", { com.parthdesai1208.compose.view.animation.RepeatableFun() }),
    InfiniteRepeatable("InfiniteRepeatable", { com.parthdesai1208.compose.view.animation.InfiniteRepeatableFun() }),
    Snap("snap", { com.parthdesai1208.compose.view.animation.SnapFun() }),
    AnimationVector("AnimationVector - TypeConverter,Coroutine", { com.parthdesai1208.compose.view.animation.AnimationVectorFun() })
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
                .padding(8.dp)
        ) {
            Text(title.buttonTitle, textAlign = TextAlign.Center)
        }
    }

    Column {
        Text(text = "Compose Samples", modifier = Modifier.padding(16.dp), fontSize = 18.sp, fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.colors.onSurface)
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