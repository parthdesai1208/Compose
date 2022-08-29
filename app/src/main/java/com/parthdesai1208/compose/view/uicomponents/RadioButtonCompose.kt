package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.ComposeTheme

@Composable
fun RadioButtonCompose() {
    val radioOptions = listOf("A", "B", "C")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
    Surface {
        Column {
            radioOptions.forEach { text ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .selectable( //for making whole row clickable & perform same operation as user click on radio button
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RadioButtonComposePrev() {
    ComposeTheme {
        RadioButtonCompose()
    }
}