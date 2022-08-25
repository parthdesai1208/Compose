package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.*

@Composable
fun SwitchCompose() {
    var mCheckedState by remember { mutableStateOf(false) }
    var mCheckedState1 by remember { mutableStateOf(false) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Default Switch")
            Spacer(modifier = Modifier.height(8.dp))
            Switch(checked = mCheckedState, onCheckedChange = { mCheckedState = it })
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Switch State: $mCheckedState")

            DividerTextCompose()

            Text(text = "Disable Switch")
            Spacer(modifier = Modifier.height(8.dp))
            Switch(
                checked = false, onCheckedChange = {},
                enabled = false
            )

            DividerTextCompose()

            Text(text = "Switch with custom color")
            Spacer(modifier = Modifier.height(8.dp))
            Switch(
                checked = mCheckedState1, onCheckedChange = { mCheckedState1 = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = petrol_gmap,
                    checkedTrackColor = hotel_gmap,
                    uncheckedThumbColor = more_gmap,
                    uncheckedTrackColor = RainbowIndigo
                )
            )

            DividerTextCompose()
        }
    }
}

@Preview
@Composable
fun SwitchComposePrev() {
    ComposeTheme {
        SwitchCompose()
    }
}