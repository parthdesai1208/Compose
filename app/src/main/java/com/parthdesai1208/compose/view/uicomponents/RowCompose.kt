package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

//region Column Listing Screen
enum class RowListingEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    WrapRowTopStart("WrapRowTopStart", { WrapRowTopStart() }),
    WrapRowTop("WrapRowTop", { WrapRowTop() }),
    WrapRowTopEnd("WrapRowTopEnd", { WrapRowTopEnd() }),
    WrapRowCenterStart("WrapRowCenterStart", { WrapRowCenterStart() }),
    WrapRowCenter("WrapRowCenter", { WrapRowCenter() }),
    WrapRowCenterEnd("WrapRowCenterEnd", { WrapRowCenterEnd() }),
    WrapRowBottomStart("WrapRowBottomStart", { WrapRowBottomStart() }),
    WrapRowBottom("WrapRowBottom", { WrapRowBottom() }),
    WrapRowBottomEnd("WrapRowBottomEnd", { WrapRowBottomEnd() }),

    FillMaxRowTopStart("FillMaxRowTopStart", { FillMaxRowTopStart() }),
    FillMaxRowTop("FillMaxRowTop", { FillMaxRowTop() }),
    FillMaxRowTopEnd("FillMaxRowTopEnd", { FillMaxRowTopEnd() }),
    FillMaxRowCenterStart("FillMaxRowCenterStart", { FillMaxRowCenterStart() }),
    FillMaxRowCenter("FillMaxRowCenter", { FillMaxRowCenter() }),
    FillMaxRowCenterEnd("FillMaxRowCenterEnd", { FillMaxRowCenterEnd() }),
    FillMaxRowBottomStart("FillMaxRowBottomStart", { FillMaxRowBottomStart() }),
    FillMaxRowBottom("FillMaxRowBottom", { FillMaxRowBottom() }),
    FillMaxRowBottomEnd("FillMaxRowBottomEnd", { FillMaxRowBottomEnd() }),
    FillMaxSizeRowChildSpaceEvenly("FillMaxSizeRowChildSpaceEvenly",{FillMaxSizeRowChildSpaceEvenly()}),
    FillMaxSizeRowChildSpaceAround("FillMaxSizeRowChildSpaceAround",{FillMaxSizeRowChildSpaceAround()}),
    FillMaxSizeRowChildSpaceBetween("FillMaxSizeRowChildSpaceBetween",{FillMaxSizeRowChildSpaceBetween()}),

    IndividualChildAlignmentRow("IndividualChildAlignment", { IndividualChildAlignmentRow() }),
    ChildWeightRow("ChildWeight (.25f for all)", { ChildWeightRow() }),
    ScrollableRow("ScrollableRow",{ScrollableRow()}),
    AlignAllChildRow("Apply same space between All Child",{AlignAllChildRow()}),
}

object RowDestinations {
    const val ROW_MAIN_SCREEN = "ROW_MAIN_SCREEN"
    const val ROW_SCREEN_ROUTE_PREFIX = "ROW_SCREEN_ROUTE_PREFIX"
    const val ROW_SCREEN_ROUTE_POSTFIX = "ROW_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun RowNavGraph(startDestination: String = RowDestinations.ROW_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = RowDestinations.ROW_MAIN_SCREEN) {
            RowListingScreen(navController = navController)
        }

        composable(
            route = "${RowDestinations.ROW_SCREEN_ROUTE_PREFIX}/{${RowDestinations.ROW_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(RowDestinations.ROW_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildRowScreen(arguments.getString(RowDestinations.ROW_SCREEN_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun ChildRowScreen(onClickButtonTitle: String?) {
    enumValues<RowListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Composable
fun RowListingScreen(navController: NavController) {
    @Composable
    fun MyButton(
        title: RowListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${RowDestinations.ROW_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(title.buttonTitle, textAlign = TextAlign.Center)
        }
    }

    Column {
        Text(
            text = "Row Samples",
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
            enumValues<RowListingEnumType>().forEach {
                MyButton(it)
            }
        }
    }
}
//endregion

@Composable
fun CommonBoxForRow(
    modifier: Modifier = Modifier,
    width: Dp = 80.dp,
    height: Dp = 40.dp,
    color: Color
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(color = color)
    )
}

@Composable
fun WrapRowTopStart(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.commonBorder(width = 5.dp, color = MaterialTheme.colors.onSurface)
    ) {
        CommonBoxForRow(color = Color.Red)
        CommonBoxForRow(color = Color.Yellow)
        CommonBoxForRow(color = Color.Green)
    }
}

@Composable
fun WrapRowTop() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapRowTopEnd() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapRowCenterStart() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapRowCenter() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapRowCenterEnd() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapRowBottomStart() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapRowBottom() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapRowBottomEnd() {
    WrapRowTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun FillMaxRowTopStart(
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .commonBorder(color = MaterialTheme.colors.onSurface),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {
        CommonBoxForRow(color = Color.Red)
        CommonBoxForRow(color = Color.Yellow)
        CommonBoxForRow(color = Color.Green)
    }
}

@Composable
fun FillMaxRowTop() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    )
}

@Composable
fun FillMaxRowTopEnd() {
    FillMaxRowTopStart(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.End)
}

@Composable
fun FillMaxRowCenterStart() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    )
}

@Composable
fun FillMaxRowCenter() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
}

@Composable
fun FillMaxRowCenterEnd() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    )
}

@Composable
fun FillMaxRowBottomStart() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start
    )
}

@Composable
fun FillMaxRowBottom() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    )
}

@Composable
fun FillMaxRowBottomEnd() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    )
}

@Composable
fun FillMaxSizeRowChildSpaceEvenly() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    )
}

@Composable
fun FillMaxSizeRowChildSpaceAround() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    )
}


@Composable
fun FillMaxSizeRowChildSpaceBetween() {
    FillMaxRowTopStart(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
}

@Composable
fun IndividualChildAlignmentRow() {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
            .height(150.dp)
            .fillMaxWidth()
            .commonBorder(color = MaterialTheme.colors.onSurface, width = 2.dp)
    ) {
        CommonBoxForRow(color = Color.Red, modifier = Modifier.align(Alignment.Top))
        CommonBoxForRow(color = Color.Yellow, modifier = Modifier.align(Alignment.CenterVertically))
        CommonBoxForRow(color = Color.Green, modifier = Modifier.align(Alignment.Bottom))
        CommonBoxForRow(color = Color.Red, modifier = Modifier.align(Alignment.Top))
        CommonBoxForRow(color = Color.Yellow, modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun ChildWeightRow() {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
            .fillMaxWidth()
            .commonBorder(color = MaterialTheme.colors.onSurface, width = 2.dp)
    ) {
        CommonBoxForRow(color = Color.Red, modifier = Modifier.weight(weight = .25f))
        CommonBoxForRow(color = Color.Yellow, modifier = Modifier.weight(weight = .25f))
        CommonBoxForRow(color = Color.Green, modifier = Modifier.weight(weight = .25f))
        CommonBoxForRow(color = Color.Magenta, modifier = Modifier.weight(weight = .25f))
    }
}

@Composable
fun ScrollableRow() {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
            .fillMaxWidth()
            .commonBorder(color = MaterialTheme.colors.onSurface, width = 2.dp)
            .horizontalScroll(state = rememberScrollState())
    ) {
        CommonBoxForRow(color = Color.Red)
        CommonBoxForRow(color = Color.Yellow)
        CommonBoxForRow(color = Color.Green)
        CommonBoxForRow(color = Color.Magenta)
        CommonBoxForRow(color = Color.Cyan)
        CommonBoxForRow(color = Color.Gray)
        CommonBoxForRow(color = Color.Red)
        CommonBoxForRow(color = Color.Yellow)
        CommonBoxForRow(color = Color.Green)
        CommonBoxForRow(color = Color.Magenta)
        CommonBoxForRow(color = Color.Cyan)
        CommonBoxForRow(color = Color.Gray)
    }
}

@Composable
fun AlignAllChildRow() {
    Row(modifier = Modifier
        .fillMaxSize()
        .commonBorder(color = MaterialTheme.colors.onSurface),
    horizontalArrangement = Arrangement.spacedBy(20.dp)){
        CommonBoxForRow(color = Color.Red)
        CommonBoxForRow(color = Color.Yellow)
        CommonBoxForRow(color = Color.Green)
        CommonBoxForRow(color = Color.Magenta)
    }
}