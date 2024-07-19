package com.parthdesai1208.compose.view.uicomponents

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.view.theme.Purple100
import com.parthdesai1208.compose.view.theme.hotel_gmap
import com.parthdesai1208.compose.view.theme.petrol_gmap

@Composable
fun RadioButtonCompose(navHostController: NavHostController) {
    val radioOptions = listOf("A", "B (selected by default)", "C", "D")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }

    val radioOptions1 =
        listOf("radio button with custom color(item1)", "radio button with custom color(item2)")
    val (selectedOption1, onOptionSelected1) = remember { mutableStateOf(radioOptions1[0]) }

    val context = LocalContext.current

    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = context.getString(R.string.radioButtonSample),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                Text(text = "Simple Radio buttons")
                radioOptions.forEach { text ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .selectable( //for making whole row clickable & perform same operation as user click on radio button
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                Toast
                                    .makeText(
                                        context,
                                        "$text option is clicked",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        )
                        .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
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
                DividerTextCompose()
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = true,
                        onClick = { },
                        enabled = false
                    )
                    Text(
                        text = "Disable RadioButton",
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                }
                DividerTextCompose()
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = true,
                        onClick = { },
                        enabled = false,
                        colors = RadioButtonDefaults.colors(disabledColor = petrol_gmap)
                    )
                    Text(
                        text = "Disable RadioButton with custom color",
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                }
                DividerTextCompose()
                radioOptions1.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption1),
                                onClick = { onOptionSelected1(text) }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption1),
                            onClick = { onOptionSelected1(text) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = hotel_gmap,
                                unselectedColor = Purple100,
                            )
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
}

@Preview
@Composable
fun RadioButtonComposePrev() {
    ComposeTheme {
        RadioButtonCompose(rememberNavController())
    }
}