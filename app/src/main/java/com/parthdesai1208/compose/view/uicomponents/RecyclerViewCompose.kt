package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.HorizontalGridListData
import com.parthdesai1208.compose.model.HorizontalListData
import com.parthdesai1208.compose.model.StaggeredGridListDataClass
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.HorizontalListViewModel
import kotlin.math.max

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
    var expanded by remember { mutableStateOf(false) }

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

//region Horizontal Recyclerview
@Composable
fun HorizontalList(viewModel : HorizontalListViewModel,modifier: Modifier = Modifier) {
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
            text = "Grid List (Grid:2)",
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
        Text(
            text = "Staggered Grid List (Grid:4)",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(all = 8.dp)
        )

        StaggeredGridFun(viewModelState, onChipClick = { index, b ->
            viewModel.onChipClicked(index,b)
        })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val s = StringBuilder()
            viewModelState.forEach { if(it.isAdded) s.append(it.genre).append(", ") }
            if(s.isNotEmpty()) Toast.makeText(context, "${s.dropLast(2)}", Toast.LENGTH_SHORT).show()
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
//endregion

//region Staggered Grid List
@Composable
fun StaggeredGridFun(list : List<StaggeredGridListDataClass>, modifier: Modifier = Modifier,
   onChipClick: (Int,Boolean) -> Unit) {
    val context = LocalContext.current

    Row(modifier = modifier
        .horizontalScroll(rememberScrollState())) {
        StaggeredGrid(modifier = modifier, totalRow = 4) {
            list.forEachIndexed { index, s ->
                Chip(modifier = Modifier.padding(6.dp)
                    , text = s.genre
                    ,onChipClick = {
                            onChipClick(index,it)
                })
            }
        }
    }
}

class StaggeredGridItemTransition(
    selectedAlpha: State<Float>
){
    val selectedAlpha by selectedAlpha
}
private enum class SelectionState { Unselected, Selected }

@Composable
private fun staggeredGridItemTransitionFun(rowSelected : Boolean) : StaggeredGridItemTransition {
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

    return remember(transition){
        StaggeredGridItemTransition(selectedAlpha = selectedAlpha)
    }
}

@Composable
fun StaggeredGrid(
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
fun Chip(modifier: Modifier = Modifier, text: String ,onChipClick : (Boolean) -> Unit) {
    val (selected, onSelected) = rememberSaveable { mutableStateOf(false) }
    val staggeredGridItemTransitionState = staggeredGridItemTransitionFun(rowSelected = selected)
    Card(modifier = modifier.padding(1.dp),
        border = BorderStroke(color = MaterialTheme.colors.onSurface, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = if(staggeredGridItemTransitionState.selectedAlpha > 0f) colorResource(id = R.color.pink_400) else MaterialTheme.colors.surface
    ) {
        if(staggeredGridItemTransitionState.selectedAlpha > 0f){
            onChipClick(true)
        }else{
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
            Icon(imageVector = if(staggeredGridItemTransitionState.selectedAlpha > 0f) Icons.Default.Check else Icons.Default.Add, contentDescription = null
                , tint = if(staggeredGridItemTransitionState.selectedAlpha > 0f) Color.White else LocalContentColor.current.copy(alpha = LocalContentAlpha.current))
            /*Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )*/
            Spacer(Modifier.width(4.dp))
            Text(text = text, color = if(staggeredGridItemTransitionState.selectedAlpha > 0f) Color.White else MaterialTheme.colors.onSurface)
        }
    }
}

@Preview(name = "light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChipPreview() {
    ComposeTheme {
        Chip(text = "Hi there", onChipClick = {})
    }
}
//endregion
//endregion