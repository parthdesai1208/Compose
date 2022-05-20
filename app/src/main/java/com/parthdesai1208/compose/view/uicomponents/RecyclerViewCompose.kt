package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.HorizontalGridListData
import com.parthdesai1208.compose.model.HorizontalListData

//region Collapsable Recyclerview
@Preview
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
@Preview
@Composable
fun HorizontalList(modifier: Modifier = Modifier) {
    val stateForLazyListStateDemo = rememberLazyListState()
    val context = LocalContext.current
    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier.padding(top = 16.dp)
        ) {
            itemsIndexed(HorizontalListData) { position, item ->
                HorizontalListItem(item.drawable, item.text, onItemClick = {
                    Toast.makeText(context, "$it is clicked at $position position", Toast.LENGTH_SHORT).show()
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

        val lastDetect by remember {
            derivedStateOf {
                stateForLazyListStateDemo.isScrolledToTheEnd()
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

        val firstDetect by remember {
            derivedStateOf {
                stateForLazyListStateDemo.firstVisibleItemIndex == 0
            }
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
            text = "Horizontal Grid List (Grid:2)",
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
            itemsIndexed(HorizontalGridListData) {position, item ->
                HorizontalGridListItem(item.drawable, item.text,
                    Modifier
                        .height(56.dp)
                        .clickable {
                            Toast.makeText(context, "${context.getString(item.text)} is clicked at $position position", Toast.LENGTH_SHORT).show()
                        })
            }
        }
    }
}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

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