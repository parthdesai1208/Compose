package com.parthdesai1208.compose.view.uicomponents

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
import com.parthdesai1208.compose.view.uicomponents.bottomsheet.BottomSheetNavGraph


enum class UIComponentsListingEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    TextComponents("Text", { TextComponents("World") }),
    EditTextCompose(
        "EditText",
        { EditTextCompose(androidx.lifecycle.viewmodel.compose.viewModel()) }),
    ButtonComponents("Button", { ButtonCompose() }),
    ImageCompose("Image", { ImageComposeScreen() }),
    IconCompose("Icon", { IconComposeScreen() }),
    SearchBarComponents(
        "SearchBar",
        { SearchBar(androidx.lifecycle.viewmodel.compose.viewModel()) }),
    SnackBarComponents("SnackBar", { SnackBarCompose() }),
    ColumnCompose("Column", { ColumnNavGraph() }),
    RowCompose("Row", { RowNavGraph() }),
    BoxCompose("Box", { BoxNavGraph() }),
    CardCompose("Card", { CardCompose() }),
    CollapsableRecyclerviewScreen("Vertical List", { VerticalListNavGraph() }),
    HorizontalListScreen("Horizontal List", { HorizontalListNavGraph() }),
    ConstraintLayoutContent("Constraint Layout Content", { ConstraintLayoutContent() }),
    ConstraintLayoutScreen("runtime Constraint Layout", { DecoupledConstraintLayout() }),
    BottomSheetScreen("Bottom Sheet", { BottomSheetNavGraph() }),
    SwitchCompose("Switch", { SwitchCompose() }),
    RadioCompose("Radio Button", { RadioButtonCompose() }),
    DropdownMenuCompose("Drop-down Menu", { DropdownMenu() }),
    SliderCompose("Slider", { SliderCompose() }),
    BadgeCompose("Badge", { BadgeCompose() }),
    DialogCompose("Dialog", { DialogCompose() }),
}

object UIComponentsDestinations {
    const val DRAW_MAIN_SCREEN = "DRAW_MAIN_SCREEN"
    const val DRAW_SCREEN_ROUTE_PREFIX = "DRAW_SCREEN_ROUTE_PREFIX"
    const val DRAW_SCREEN_ROUTE_POSTFIX = "DRAW_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun UIComponentsNavGraph(startDestination: String = UIComponentsDestinations.DRAW_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = UIComponentsDestinations.DRAW_MAIN_SCREEN) {
            UIComponentsListingScreen(navController = navController)
        }

        composable(
            route = "${UIComponentsDestinations.DRAW_SCREEN_ROUTE_PREFIX}/{${UIComponentsDestinations.DRAW_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(UIComponentsDestinations.DRAW_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildUIComponentsScreen(arguments.getString(UIComponentsDestinations.DRAW_SCREEN_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun UIComponentsListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: UIComponentsListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${UIComponentsDestinations.DRAW_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
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
                text = "UI Components Samples",
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
                enumValues<UIComponentsListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}

@Composable
fun ChildUIComponentsScreen(onClickButtonTitle: String?) {
    enumValues<UIComponentsListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}
