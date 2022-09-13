package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adwi.neumorph.android.MorphSwitch
import com.parthdesai1208.compose.view.theme.*

@Composable
fun SwitchCompose() {
    var mCheckedState by remember { mutableStateOf(false) }
    var mCheckedState1 by remember { mutableStateOf(false) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
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
            MorphSwitchCompose()
            DividerTextCompose()
        }
    }
}

@Composable
fun MorphSwitchCompose() {
    var value by remember { mutableStateOf(false) }

    MorphSwitch(
        elevation = 10.dp,
        cornerRadius = 10.dp,
        switchColor = MaterialTheme.colors.secondary,
        value = value,
        onValueChange = { value = !value },
        modifier = Modifier
            .width(80.dp)
            .height(40.dp)
    )
}

@Preview
@Composable
fun SwitchComposePrev() {
    ComposeTheme {
        SwitchCompose()
    }
}