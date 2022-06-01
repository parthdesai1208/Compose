package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.DrawableStringPair
import com.parthdesai1208.compose.model.HorizontalGridListData
import com.parthdesai1208.compose.model.HorizontalListData
import com.parthdesai1208.compose.model.StaggeredGridListDataClass
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.HorizontalListViewModel
import kotlin.math.ceil
import kotlin.math.max

//region vertical recyclerview

object VerticalListDestinations {
    const val VERTICAL_LIST_MAIN_SCREEN = "VERTICAL_LIST_MAIN_SCREEN"
    const val VERTICAL_LIST_SCREEN_ROUTE_PREFIX = "VERTICAL_LIST_SCREEN_ROUTE_PREFIX"
    const val VERTICAL_LIST_SCREEN_ROUTE_POSTFIX = "VERTICAL_LIST_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun VerticalListNavGraph(startDestination: String = VerticalListDestinations.VERTICAL_LIST_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = VerticalListDestinations.VERTICAL_LIST_MAIN_SCREEN) {
            VerticalListListing(navController = navController)
        }

        composable(
            route = "${VerticalListDestinations.VERTICAL_LIST_SCREEN_ROUTE_PREFIX}/{${VerticalListDestinations.VERTICAL_LIST_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(VerticalListDestinations.VERTICAL_LIST_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildVerticalListScreen(arguments.getString(VerticalListDestinations.VERTICAL_LIST_SCREEN_ROUTE_POSTFIX))
        }

    }
}

enum class VerticalListListingEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    CollapsableList("Collapsable Expandable Recyclerview(vertical)", { CollapsableRecyclerView() }),
    VerticalGridList("Grid List (fixed)", { VerticalGridList() }),
    AdaptiveVerticalGridList("Adaptive Grid List",
        { VerticalGridList(GridCells.Adaptive(150.dp)) }),
    CustomGridCell1("Double sized first row",
        { VerticalGridList(gridCells = DoubleSizedLeftRowGridCell) }),
    CustomGridCell2("First item take entire space", { FirstItemTakeWholeSpace() }),
    CustomGridCell3("Every third Item take entire Space", { EveryThirdItemTakeWholeSpace() }),
    StaggeredGridList("Staggered Grid List",{ VerticalStaggeredGridListFun() })
}

@Composable
fun ChildVerticalListScreen(onClickButtonTitle: String?) {
    enumValues<VerticalListListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}

@Composable
fun VerticalListListing(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: VerticalListListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${VerticalListDestinations.VERTICAL_LIST_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
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
            text = "Vertical List Samples",
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
            enumValues<VerticalListListingEnumType>().forEach {
                MyButton(it)
            }
        }
    }
}

//region Collapsable Recyclerview
@Composable
fun CollapsableRecyclerView(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)) {
        items(names) { name ->
            ItemCollapsableRecyclerView(name = name)
        }
    }
}

@Composable
private fun ItemCollapsableRecyclerView(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardItemCollapsableRecyclerView(name)
    }
}

@Composable
private fun CardItemCollapsableRecyclerView(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}

//endregion
//region vertical grid list
@Composable
fun VerticalGridList(gridCells: GridCells = GridCells.Fixed(2)) {
    androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
        columns = gridCells,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(items = HorizontalGridListData) { item ->
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = item.drawable),
                        contentDescription = stringResource(id = item.text),
                        modifier = Modifier
                            .sizeIn(maxHeight = 150.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = stringResource(id = item.text),
                        textAlign = TextAlign.Center, modifier = Modifier
                            .align(Alignment.Center)
                            .background(
                                color = Color.LightGray.copy(alpha = .5f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 3.dp)
                    )
                }
            }
        })
}

val DoubleSizedLeftRowGridCell = object : GridCells {
    override fun Density.calculateCrossAxisCellSizes(availableSize: Int, spacing: Int): List<Int> {
        val firstColumn = (availableSize - spacing) * 2 / 3
        val secondColumn = availableSize - spacing - firstColumn
        return listOf(firstColumn, secondColumn)
    }
}

@Composable
fun FirstItemTakeWholeSpace() {
    androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
        columns = DoubleSizedLeftRowGridCell,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            HorizontalGridListData.forEachIndexed { index, drawableStringPair ->
                if (index == 0) {  //for very first item
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ItemViewFirstItemTakeWholeSpace(drawableStringPair)
                    }
                } else { //for remaining items in list
                    item(span = { GridItemSpan(1) }) {
                        ItemViewFirstItemTakeWholeSpace(drawableStringPair)
                    }
                }
            }
        })
}

@Composable
fun ItemViewFirstItemTakeWholeSpace(item: DrawableStringPair) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = item.drawable),
            contentDescription = stringResource(id = item.text),
            modifier = Modifier
                .sizeIn(maxHeight = 150.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = item.text),
            textAlign = TextAlign.Center, modifier = Modifier
                .align(Alignment.Center)
                .background(
                    color = Color.LightGray.copy(alpha = .5f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 3.dp)
        )
    }
}

@Composable
fun EveryThirdItemTakeWholeSpace() {
    androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
        columns = DoubleSizedLeftRowGridCell,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            HorizontalGridListData.forEachIndexed { index, drawableStringPair ->
                if (index % 3 == 0) {  //for every third items
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ItemViewFirstItemTakeWholeSpace(drawableStringPair)
                    }
                } else { //for remaining items in list
                    item(span = { GridItemSpan(1) }) {
                        ItemViewFirstItemTakeWholeSpace(drawableStringPair)
                    }
                }
            }
        })
}


@Composable
fun VerticalStaggeredGridListFun() {
    LazyColumn(contentPadding = PaddingValues(8.dp)){
        item {
            VerticalStaggeredGridList(totalColumn = 3){
                HorizontalGridListData.forEachIndexed { index, item ->
                    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                        Image(
                            painter = painterResource(id = item.drawable),
                            contentDescription = stringResource(id = item.text),
                            modifier = Modifier
                                .align(Alignment.Center),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = stringResource(id = item.text),
                            textAlign = TextAlign.Center, modifier = Modifier
                                .align(Alignment.Center)
                                .background(
                                    color = Color.LightGray.copy(alpha = .5f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VerticalStaggeredGridList(modifier: Modifier = Modifier, totalColumn : Int = 0,content: @Composable () -> Unit) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeableXY: MutableMap<Placeable, Pair<Int, Int>> = mutableMapOf()

        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
//        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / totalColumn
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(totalColumn) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            placeableXY[placeable] = Pair(columnWidth * column, colHeights[column])
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            placeables.forEach { placeable ->
                placeable.place(
                    x = placeableXY.getValue(placeable).first,
                    y = placeableXY.getValue(placeable).second
                )
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}
//endregion
//endregion

//region Horizontal Recyclerview
object HorizontalListDestinations {
    const val HAGL_MAIN_SCREEN = "HAGL_MAIN_SCREEN"
    const val HAGL_SCREEN_ROUTE_PREFIX = "HAGL_SCREEN_ROUTE_PREFIX"
}

@Composable
fun HorizontalListNavGraph(startDestination: String = HorizontalListDestinations.HAGL_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = HorizontalListDestinations.HAGL_MAIN_SCREEN) {
            HorizontalList(
                androidx.lifecycle.viewmodel.compose.viewModel(),
                navController = navController
            )
        }

        composable(route = HorizontalListDestinations.HAGL_SCREEN_ROUTE_PREFIX) {
            HorizontalAdaptiveGridListFun()
        }

    }
}

@Composable
fun HorizontalList(
    viewModel: HorizontalListViewModel = HorizontalListViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val stateForLazyListStateDemo = rememberLazyListState()
    val lastDetect by remember {
        derivedStateOf {
            stateForLazyListStateDemo.isScrolledToTheEnd()
        }
    }
    val firstDetect by remember {
        derivedStateOf {
            stateForLazyListStateDemo.firstVisibleItemIndex == 0
        }
    }
    val context = LocalContext.current
    val viewModelState by viewModel.staggeredGridItems.collectAsState()
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier.padding(top = 16.dp)
        ) {
            itemsIndexed(HorizontalListData) { position, item ->
                HorizontalListItem(item.drawable, item.text, onItemClick = {
                    Toast.makeText(
                        context,
                        "$it is clicked at $position position",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Reverse Layout",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(all = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier,
            reverseLayout = true
        ) {
            items(HorizontalListData) { item ->
                HorizontalListItem(item.drawable, item.text)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Lazy list state",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(all = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier.wrapContentHeight(),
            state = stateForLazyListStateDemo
        ) {
            items(HorizontalListData) { item ->
                HorizontalListItem(item.drawable, item.text)
            }
        }

        AnimatedVisibility(visible = lastDetect) {
            Text(
                text = "Last item detected",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)
            )
        }

        AnimatedVisibility(visible = firstDetect) {
            Text(
                text = "First item detected",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Grid List (Grid:2 (row-fixed))",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(all = 8.dp)
        )

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.height(120.dp)
        ) {
            itemsIndexed(HorizontalGridListData) { position, item ->
                HorizontalGridListItem(item.drawable, item.text,
                    Modifier
                        .height(56.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "${context.getString(item.text)} is clicked at $position position",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = {
            navController.navigate(HorizontalListDestinations.HAGL_SCREEN_ROUTE_PREFIX)
        }, modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = "Adaptive Grid List ->")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Staggered Grid List (Grid:4)",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(all = 8.dp)
        )

        StaggeredGridFun(viewModelState, onChipClick = { index, b ->
            viewModel.onChipClicked(index, b)
        })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val s = StringBuilder()
            viewModelState.forEach { if (it.isAdded) s.append(it.genre).append(", ") }
            if (s.isNotEmpty()) Toast.makeText(context, "${s.dropLast(2)}", Toast.LENGTH_SHORT)
                .show()
        }, modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = "Show selection")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HorizontalListItem(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.clickable {
            onItemClick(context.getString(text))
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.paddingFromBaseline(
                top = 24.dp, bottom = 8.dp
            ),
            color = MaterialTheme.colors.onSurface
        )
    }
}

//region Lazy list state
fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
//endregion

//region grid list
@Composable
fun HorizontalGridListItem(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun HorizontalAdaptiveGridListFun(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyHorizontalGrid(rows = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(8.dp), content = {
                //150.dp is the height of one cell
                items(items = HorizontalGridListData) {
                    Image(
                        painter = painterResource(it.drawable),
                        contentDescription = stringResource(id = it.text),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                    )
                }
            })
    }
}
//endregion

//region Staggered Grid List
@Composable
fun StaggeredGridFun(
    list: List<StaggeredGridListDataClass>, modifier: Modifier = Modifier,
    onChipClick: (Int, Boolean) -> Unit
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        HorizontalStaggeredGrid(modifier = modifier, totalRow = 4) {
            list.forEachIndexed { index, s ->
                Chip(modifier = Modifier.padding(6.dp), text = s.genre, onChipClick = {
                    onChipClick(index, it)
                })
            }
        }
    }
}

class StaggeredGridItemTransition(
    selectedAlpha: State<Float>
) {
    val selectedAlpha by selectedAlpha
}

private enum class SelectionState { Unselected, Selected }

@Composable
private fun staggeredGridItemTransitionFun(rowSelected: Boolean): StaggeredGridItemTransition {
    val transition = updateTransition(
        targetState = if (rowSelected) SelectionState.Selected else SelectionState.Unselected,
        label = ""
    )

    val selectedAlpha = transition.animateFloat(label = "") { state ->
        when (state) {
            SelectionState.Unselected -> 0f
            SelectionState.Selected -> 0.8f
        }
    }

    return remember(transition) {
        StaggeredGridItemTransition(selectedAlpha = selectedAlpha)
    }
}

@Composable
fun HorizontalStaggeredGrid(
    modifier: Modifier = Modifier,
    totalRow: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Keep track of the width of each row
        val eachRowWidth = IntArray(totalRow) { 0 }

        // Keep track of the max height of each row
        val eachRowHeight = IntArray(totalRow) { 0 }

        // here we add array for calculating total width & height of the horizontal recyclerview
        val placeables = measurables.mapIndexed { index, measurable ->

            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % totalRow
            eachRowWidth[row] += placeable.width
            eachRowHeight[row] = max(eachRowHeight[row], placeable.height)

            placeable
        }

        // here we calculate total width of the horizontal recyclerview
        val totalWidthOfRecyclerView = eachRowWidth.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // here we calculate total height of the horizontal recyclerview
        val totalHeightOfRecyclerView = eachRowHeight.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // here we prepare array for each item's height in increment order
        val eachRowYPosition = IntArray(totalRow) { 0 }
        for (i in 1 until totalRow) {
            eachRowYPosition[i] = eachRowYPosition[i - 1] + eachRowHeight[i - 1]
        }

        // Set the size of the recycler view
        layout(totalWidthOfRecyclerView, totalHeightOfRecyclerView) {
            // x cord we have placed up to, per row
            val eachRowXPosition = IntArray(totalRow) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % totalRow
                placeable.placeRelative(
                    x = eachRowXPosition[row],
                    y = eachRowYPosition[row]
                )
                //here we add width after each item place in horizontal direction
                eachRowXPosition[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String, onChipClick: (Boolean) -> Unit) {
    val (selected, onSelected) = rememberSaveable { mutableStateOf(false) }
    val staggeredGridItemTransitionState = staggeredGridItemTransitionFun(rowSelected = selected)
    Card(
        modifier = modifier.padding(1.dp),
        border = BorderStroke(color = MaterialTheme.colors.onSurface, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = if (staggeredGridItemTransitionState.selectedAlpha > 0f) colorResource(id = R.color.pink_400) else MaterialTheme.colors.surface
    ) {
        if (staggeredGridItemTransitionState.selectedAlpha > 0f) {
            onChipClick(true)
        } else {
            onChipClick(false)
        }
        Row(
            modifier = Modifier
                .toggleable(value = selected, onValueChange = onSelected)
                .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (staggeredGridItemTransitionState.selectedAlpha > 0f) Icons.Default.Check else Icons.Default.Add,
                contentDescription = null,
                tint = if (staggeredGridItemTransitionState.selectedAlpha > 0f) Color.White else LocalContentColor.current.copy(
                    alpha = LocalContentAlpha.current
                )
            )
            /*Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )*/
            Spacer(Modifier.width(4.dp))
            Text(
                text = text,
                color = if (staggeredGridItemTransitionState.selectedAlpha > 0f) Color.White else MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun ChipPreview() {
    ComposeTheme {
        Chip(text = "Hi there", onChipClick = {})
    }
}
//endregion
//endregion