package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.*

@Composable
fun SliderCompose() {
    var sliderPosition by remember { mutableStateOf(0f) }
    var sliderPosition1 by remember { mutableStateOf(0f) }
    var sliderPosition2 by remember { mutableStateOf(0f) }
    var sliderPosition3 by remember { mutableStateOf(0f) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = sliderPosition.toString())
            Slider(value = sliderPosition, onValueChange = { sliderPosition = it })
            DividerTextCompose()
            Text(text = "Disabled slider")
            Slider(value = 0f, onValueChange = { }, enabled = false)
            DividerTextCompose()
            Text(text = "custom Value range(0-15): $sliderPosition1")
            Slider(
                value = sliderPosition1, onValueChange = { sliderPosition1 = it },
                valueRange = 0f..15f
            )
            DividerTextCompose()
            Text(text = "Value range(0-15) with step 2(2+1=3)(0to15 every parts divided equally)\npart1:0to5\npart2:5to10\npart3:10to15")
            Text(text = "$sliderPosition2")
            Slider(
                value = sliderPosition2, onValueChange = { sliderPosition2 = it },
                valueRange = 0f..15f, steps = 2
            )
            DividerTextCompose()
            Text(text = "custom color")
            Slider(
                value = sliderPosition3, onValueChange = { sliderPosition3 = it },
                valueRange = 0f..20f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = red1000,
                    activeTrackColor = hotel_gmap,
                    inactiveTrackColor = more_gmap,
                    activeTickColor = coffee_gmap,
                    inactiveTickColor = attractions_gmap
                )
            )
        }
    }
}
