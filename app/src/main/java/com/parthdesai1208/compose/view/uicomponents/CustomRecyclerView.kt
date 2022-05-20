package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.ComposeTheme
import kotlin.math.max

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
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = MaterialTheme.colors.onSurface, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}


@Preview
@Composable
fun ChipPreview() {
    ComposeTheme {
        Chip(text = "Hi there")
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing",

    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun StaggeredGridFun(modifier: Modifier = Modifier) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        StaggeredGrid(modifier = modifier, totalRow = 4) {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(6.dp), text = topic)
            }
        }
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    ComposeTheme {
        StaggeredGridFun()
    }
}