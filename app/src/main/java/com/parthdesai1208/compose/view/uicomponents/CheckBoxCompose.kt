package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.Purple100
import com.parthdesai1208.compose.view.theme.coffee_gmap
import com.parthdesai1208.compose.view.theme.hotel_gmap
import com.parthdesai1208.compose.view.theme.petrol_gmap

@Composable
fun CheckBoxCompose() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var checkedState by remember { mutableStateOf(true) }
            var checkedState1 by remember { mutableStateOf(true) }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable(selected = checkedState, onClick = {
                    checkedState = !checkedState
                })
            ) {
                Checkbox(
                    checked = checkedState,
                    onCheckedChange = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Please check me!")
            }

            DividerTextCompose()

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = null,
                    enabled = false
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Disabled checkBox")
            }

            DividerTextCompose()

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = null,
                    enabled = false,
                    colors = CheckboxDefaults.colors(disabledColor = petrol_gmap)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Disabled checkBox with custom color")
            }

            DividerTextCompose()

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable(selected = checkedState1, onClick = {
                    checkedState1 = !checkedState1
                })
            ) {
                Checkbox(
                    checked = checkedState1,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        checkedColor = hotel_gmap,
                        uncheckedColor = Purple100,
                        checkmarkColor = coffee_gmap
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "checkBox with custom color")
            }

        }
    }
}