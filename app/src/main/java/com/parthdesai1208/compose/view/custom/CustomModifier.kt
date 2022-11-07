package com.parthdesai1208.compose.view.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.ComposeTheme

fun Modifier.baseLineToTop(firstBaselineToTop: Dp): Modifier {

    return this.then(
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints = constraints)

            // Check the composable has a first baseline
            check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
            val firstBaseline = placeable[FirstBaseline]

            // now we minus total padding from firstbaseline, so we get it from baseline
            val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
            // now we have to draw box with height of the layout + value that we get from minus operation
            val height = placeable.height + placeableY
            //apply height to layout
            layout(placeable.width, height) {
                // we place layout in box
                placeable.placeRelative(0, placeableY)
            }
        }
    )
}


@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.baseLineToTop(32.dp))
    }
}


@Composable
fun TextWithNormalPaddingPreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

@Preview
@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}