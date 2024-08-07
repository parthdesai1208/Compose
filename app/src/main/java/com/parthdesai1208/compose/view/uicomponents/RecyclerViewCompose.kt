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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.DrawableStringPair
import com.parthdesai1208.compose.model.HorizontalGridListData
import com.parthdesai1208.compose.model.HorizontalListData
import com.parthdesai1208.compose.model.StaggeredGridListDataClass
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.navigation.HorizontalAdaptiveListScreen
import com.parthdesai1208.compose.view.navigation.VerticalListingScreenPath
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.LightDarkColor
import com.parthdesai1208.compose.viewmodel.HorizontalListViewModel
import com.parthdesai1208.compose.viewmodel.uicomponents.UpdateUsingMutableStateListOfViewModel
import kotlin.math.max

//region vertical recyclerview
enum class VerticalListListingEnumType(
    val buttonTitle: Int, val func: @Composable (NavHostController) -> Unit
) {
    CollapsableList(R.string.collapsable_expandable_recyclerview_vertical,
        { CollapsableRecyclerView(it) }),
    VerticalGridList(
        R.string.grid_list_fixed,
        { VerticalGridList(it) }),
    AdaptiveVerticalGridList(
        R.string.adaptive_grid_list,
        { VerticalGridList(navHostController = it, GridCells.Adaptive(150.dp)) }),
    CustomGridCell1(R.string.double_sized_first_row,
        { VerticalGridList(navHostController = it, gridCells = DoubleSizedLeftRowGridCell) }),
    CustomGridCell2(
        R.string.first_item_take_entire_space,
        { FirstItemTakeWholeSpace(it) }),
    CustomGridCell3(R.string.every_third_item_take_entire_space,
        { EveryThirdItemTakeWholeSpace(it) }),
    StaggeredGridList(
        R.string.staggered_grid_list,
        { VerticalStaggeredGridListFun(it) }),
    UpdateUsingMutableStateListOfSample(R.string.update_ui_using_mutablestatelistof,
        {
            UpdateUsingMutableStateListOfSample(
                vm = androidx.lifecycle.viewmodel.compose.viewModel(),
                navHostController = it
            )
        }),

}

@Phone
@Composable
fun ListSamplePreview() {
    UpdateUsingMutableStateListOfSample(
        androidx.lifecycle.viewmodel.compose.viewModel(),
        rememberNavController()
    )
}

@Composable
fun UpdateUsingMutableStateListOfSample(
    vm: UpdateUsingMutableStateListOfViewModel,
    navHostController: NavHostController
) {
    BuildTopBarWithScreen(screen = {
        LazyColumn {
            itemsIndexed(items = vm.updateUsingMutableStateListOfModelList,
                key = { _, item -> item.name },
                itemContent = { index, item ->
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(item.name, color = LightDarkColor)
                        IconButton(onClick = {
                            vm.onClick(index, !item.isFavourite)
                        }) {
                            Icon(
                                imageVector = if (item.isFavourite) Icons.Filled.Lightbulb else Icons.Filled.LightMode,
                                contentDescription = null,
                                tint = LightDarkColor,
                            )
                        }
                    }
                })
        }
    }) {
        navHostController.popBackStack()
    }
}

@Composable
fun ChildVerticalListScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<VerticalListListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

@Composable
fun VerticalListListing(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: VerticalListListingEnumType
    ) {
        val context = LocalContext.current
        Button(
            onClick = { navController.navigate(VerticalListingScreenPath(title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Vertical List Samples",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
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
}

//region Collapsable Recyclerview
@Composable
fun CollapsableRecyclerView(
    navHostController: NavHostController,
    names: List<String> = List(1000) { "$it" }
) {
    BuildTopBarWithScreen(screen = {
        LazyColumn(modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)) {
            items(names) { name ->
                ItemCollapsableRecyclerView(name = name)
            }
        }
    }) {
        navHostController.popBackStack()
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
                    dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
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
                text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " + "padding theme elit, sed do bouncy. ").repeat(
                        4
                    ),
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
fun VerticalGridList(
    navHostController: NavHostController,
    gridCells: GridCells = GridCells.Fixed(2)
) {
    BuildTopBarWithScreen(screen = {
        LazyVerticalGrid(columns = gridCells,
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
                            textAlign = TextAlign.Center,
                            modifier = Modifier
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
    }) {
        navHostController.popBackStack()
    }
}

val DoubleSizedLeftRowGridCell = object : GridCells {
    override fun Density.calculateCrossAxisCellSizes(availableSize: Int, spacing: Int): List<Int> {
        val firstColumn = (availableSize - spacing) * 2 / 3
        val secondColumn = availableSize - spacing - firstColumn
        return listOf(firstColumn, secondColumn)
    }
}

@Composable
fun FirstItemTakeWholeSpace(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        LazyVerticalGrid(columns = DoubleSizedLeftRowGridCell,
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
    }) {
        navHostController.popBackStack()
    }
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
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .background(
                    color = Color.LightGray.copy(alpha = .5f), shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 3.dp)
        )
    }
}

@Composable
fun EveryThirdItemTakeWholeSpace(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        LazyVerticalGrid(columns = DoubleSizedLeftRowGridCell,
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
    }) {
        navHostController.popBackStack()
    }
}


@Composable
fun VerticalStaggeredGridListFun(navHostController: NavHostController) {
    BuildTopBarWithScreen(screen = {
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            item {
                VerticalStaggeredGridList(totalColumn = 3) {
                    HorizontalGridListData.forEachIndexed { _, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = item.drawable),
                                contentDescription = stringResource(id = item.text),
                                modifier = Modifier.align(Alignment.Center),
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                text = stringResource(id = item.text),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
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
    }) {
        navHostController.popBackStack()
    }
}

@Composable
fun VerticalStaggeredGridList(
    modifier: Modifier = Modifier, totalColumn: Int = 0, content: @Composable () -> Unit
) {
    Layout(
        content = content, modifier = modifier
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

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth, height = height
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
@Composable
fun HorizontalList(
    modifier: Modifier = Modifier,
    viewModel: HorizontalListViewModel = HorizontalListViewModel(),
    navController: NavHostController
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
    BuildTopBarWithScreen(screen = {
        Surface {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = modifier.padding(top = 16.dp)
                ) {
                    itemsIndexed(HorizontalListData) { position, item ->
                        HorizontalListItem(item.drawable, item.text, onItemClick = {
                            Toast.makeText(
                                context, "$it is clicked at $position position", Toast.LENGTH_SHORT
                            ).show()
                        })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Reverse Layout",
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
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                    )
                }

                AnimatedVisibility(visible = firstDetect) {
                    Text(
                        text = "First item detected",
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Grid List (Grid:2 (row-fixed))",
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
                        HorizontalGridListItem(
                            item.drawable,
                            item.text,
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
                    navController.navigate(HorizontalAdaptiveListScreen)
                }, modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(text = "Adaptive Grid List ->")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Staggered Grid List (Grid:4)",
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
                    if (s.isNotEmpty()) Toast.makeText(
                        context,
                        "${s.dropLast(2)}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }, modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(text = "Show selection")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }) {
        navController.popBackStack()
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
        }, horizontalAlignment = Alignment.CenterHorizontally
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
            )
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
    @DrawableRes drawable: Int, @StringRes text: Int, modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small, modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(192.dp)
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
fun HorizontalAdaptiveGridListFun(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    BuildTopBarWithScreen(screen = {
        Column(modifier = modifier.fillMaxSize()) {
            LazyHorizontalGrid(
                rows = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(8.dp),
                content = {
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
    }) {
        navHostController.popBackStack()
    }

}
//endregion

//region Staggered Grid List
@Composable
fun StaggeredGridFun(
    list: List<StaggeredGridListDataClass>,
    modifier: Modifier = Modifier,
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
    modifier: Modifier = Modifier, totalRow: Int = 3, content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier, content = content
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
        val totalWidthOfRecyclerView =
            eachRowWidth.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth

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
                    x = eachRowXPosition[row], y = eachRowYPosition[row]
                )
                //here we add width after each item place in horizontal direction
                eachRowXPosition[row] += placeable.width
            }
        }
    }
}

@Suppress("unused")
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
                .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (staggeredGridItemTransitionState.selectedAlpha > 0f) Icons.Default.Check else Icons.Default.Add,
                contentDescription = null,
                tint = if (staggeredGridItemTransitionState.selectedAlpha > 0f) Color.White else LocalContentColor.current.copy(
                    alpha = LocalContentAlpha.current
                )
            )/*Box(
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