package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.Purple100
import com.parthdesai1208.compose.view.theme.coffee_gmap
import com.parthdesai1208.compose.view.theme.hotel_gmap
import com.parthdesai1208.compose.view.theme.petrol_gmap

@Composable
fun CheckBoxCompose(navHostController: NavHostController) {
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
                    text = context.getString(R.string.checkboxSample),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
}