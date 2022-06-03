package com.parthdesai1208.compose.view.uicomponents


import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
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
import com.parthdesai1208.compose.view.theme.Amber600
import com.parthdesai1208.compose.view.theme.Green800
import com.parthdesai1208.compose.view.theme.red1000

//region Column Listing Screen
enum class ColumnListingEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    SimpleColumn("WrapColumnTopStart", { WrapColumnTopStart() }),
    WrapColumnTop("WrapColumnTop", { WrapColumnTop() }),
    WrapColumnTopEnd("WrapColumnTopEnd", { WrapColumnTopEnd() }),
    WrapColumnCenterStart("WrapColumnCenterStart", { WrapColumnCenterStart() }),
    WrapColumnCenter("WrapColumnCenter", { WrapColumnCenter() }),
    WrapColumnCenterEnd("WrapColumnCenterEnd", { WrapColumnCenterEnd() }),
    WrapColumnBottomStart("WrapColumnBottomStart", { WrapColumnBottomStart() }),
    WrapColumnBottom("WrapColumnBottom", { WrapColumnBottom() }),
    WrapColumnBottomEnd("WrapColumnBottomEnd", { WrapColumnBottomEnd() }),

    FillMaxSizeColumn("Fill max size column", { FillMaxSizeColumn() }),
    FillMaxSizeChildTop("FillMaxSizeChildTop", { FillMaxSizeChildTop() }),
    FillMaxSizeChildTopEnd("FillMaxSizeChildTopEnd", { FillMaxSizeChildTopEnd() }),
    FillMaxSizeChildCenterStart("FillMaxSizeChildCenterStart", { FillMaxSizeChildCenterStart() }),
    FillMaxSizeChildCenter("FillMaxSizeChildCenter", { FillMaxSizeChildCenter() }),
    FillMaxSizeChildCenterEnd("FillMaxSizeChildCenterEnd", { FillMaxSizeChildCenterEnd() }),
    FillMaxSizeChildBottomStart("FillMaxSizeChildBottomStart", { FillMaxSizeChildBottomStart() }),
    FillMaxSizeChildBottomCenter("FillMaxSizeChildBottomCenter",
        { FillMaxSizeChildBottomCenter() }),
    FillMaxSizeChildBottomEnd("FillMaxSizeChildBottomEnd", { FillMaxSizeChildBottomEnd() }),
    FillMaxSizeChildSpaceEvenly("FillMaxSizeChildSpaceEvenly", { FillMaxSizeChildSpaceEvenly() }),
    FillMaxSizeChildSpaceAround("FillMaxSizeChildSpaceAround", { FillMaxSizeChildSpaceAround() }),
    FillMaxSizeChildSpaceBetween("FillMaxSizeChildSpaceBetween",
        { FillMaxSizeChildSpaceBetween() }),

    IndividualChildAlignment("Individual Child Alignment", { IndividualChildAlignment() }),
    ChildWeight("child Weight", { ChildWeight() }),

    ScrollableColumn("Scrollable Column", { ScrollableColumn() }),

    AlignAllChild("Apply same space between All Child", { AlignAllChild() }),
}

object ColumnDestinations {
    const val COLUMN_MAIN_SCREEN = "COLUMN_MAIN_SCREEN"
    const val COLUMN_SCREEN_ROUTE_PREFIX = "COLUMN_SCREEN_ROUTE_PREFIX"
    const val COLUMN_SCREEN_ROUTE_POSTFIX = "COLUMN_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun ColumnNavGraph(startDestination: String = ColumnDestinations.COLUMN_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = ColumnDestinations.COLUMN_MAIN_SCREEN) {
            ColumnListingScreen(navController = navController)
        }

        composable(
            route = "${ColumnDestinations.COLUMN_SCREEN_ROUTE_PREFIX}/{${ColumnDestinations.COLUMN_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(ColumnDestinations.COLUMN_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildColumnScreen(arguments.getString(ColumnDestinations.COLUMN_SCREEN_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun ChildColumnScreen(onClickButtonTitle: String?) {
    enumValues<ColumnListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Composable
fun ColumnListingScreen(navController: NavController) {
    @Composable
    fun MyButton(
        title: ColumnListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${ColumnDestinations.COLUMN_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
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
            text = "Column Samples",
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
            enumValues<ColumnListingEnumType>().forEach {
                MyButton(it)
            }
        }
    }
}
//endregion

fun Modifier.commonBorder(width: Dp = 10.dp, color: Color = Green800): Modifier {
    return this.border(width = width, color = color)
}

@Composable
fun WrapColumnTopStart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.commonBorder()
    ) {
        CommonBoxForColumn1()
        CommonBoxForColumn2()
    }
}

@Composable
fun WrapColumnTop() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapColumnTopEnd() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Top)
    )
}

@Composable
fun WrapColumnCenterStart() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapColumnCenter() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapColumnCenterEnd() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun WrapColumnBottomStart() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.Start)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapColumnBottom() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
fun WrapColumnBottomEnd() {
    WrapColumnTopStart(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)
            .fillMaxHeight()
            .wrapContentHeight(align = Alignment.Bottom)
    )
}

@Composable
private fun CommonBoxForColumn2(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(width = 20.dp, color = red1000)
            .size(width = 300.dp, height = 200.dp)
    )
}

@Composable
private fun CommonBoxForColumn1(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(width = 20.dp, color = Amber600)
            .size(width = 300.dp, height = 200.dp)
    )
}


@Composable
fun FillMaxSizeColumn(
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .commonBorder(),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        CommonBoxForColumn1()
        CommonBoxForColumn2()
    }
}

@Composable
fun FillMaxSizeChildTop() {
    FillMaxSizeColumn(horizontalAlignment = Alignment.CenterHorizontally)
}

@Composable
fun FillMaxSizeChildTopEnd() {
    FillMaxSizeColumn(horizontalAlignment = Alignment.End)
}

@Composable
fun FillMaxSizeChildCenterStart() {
    FillMaxSizeColumn(verticalArrangement = Arrangement.Center)
}

@Composable
fun FillMaxSizeChildCenter() {
    FillMaxSizeColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildCenterEnd() {
    FillMaxSizeColumn(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End)
}

@Composable
fun FillMaxSizeChildBottomStart() {
    FillMaxSizeColumn(verticalArrangement = Arrangement.Bottom)
}

@Composable
fun FillMaxSizeChildBottomCenter() {
    FillMaxSizeColumn(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildBottomEnd() {
    FillMaxSizeColumn(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End)
}

@Composable
fun FillMaxSizeChildSpaceEvenly() {
    FillMaxSizeColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildSpaceAround() {
    FillMaxSizeColumn(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun FillMaxSizeChildSpaceBetween() {
    FillMaxSizeColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
}

@Composable
fun IndividualChildAlignment() {
    Column(
        modifier = Modifier
            .commonBorder()
            .fillMaxSize()
    ) {
        CommonBoxForColumn1(modifier = Modifier.align(alignment = Alignment.Start))
        CommonBoxForColumn2(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        CommonBoxForColumn1(modifier = Modifier.align(alignment = Alignment.End))
    }
}

@Composable
fun ChildWeight() {
    Column(
        modifier = Modifier
            .commonBorder()
            .fillMaxSize()
    ) {
        CommonBoxForColumn1(modifier = Modifier.weight(weight = .5f))
        CommonBoxForColumn2(modifier = Modifier.weight(weight = .5f))
    }
}

@Composable
fun ScrollableColumn() {
    Column(
        modifier = Modifier
            .commonBorder()
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
        CommonBoxForColumn1()
        CommonBoxForColumn2()
    }
}

@Composable
fun AlignAllChild() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
            .fillMaxHeight()
            .wrapContentHeight(), verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        AlignAllChildText("1st child")
        AlignAllChildText("2nd child")
        AlignAllChildText("3rd child")
        AlignAllChildText("4th child")
        AlignAllChildText("16dp space applied to all child vertically")
    }
}

@Composable
fun AlignAllChildText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(),
        textAlign = TextAlign.Center
    )
}