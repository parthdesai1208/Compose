package com.parthdesai1208.compose.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
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

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.baseLineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    ComposeTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}