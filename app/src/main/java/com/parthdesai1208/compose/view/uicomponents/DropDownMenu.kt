package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.parthdesai1208.compose.model.searchBarList

@Composable
fun DropdownMenu() {
    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Surface {
        Column(modifier = Modifier.padding(all = 20.dp)) {
            OutlinedTextField(
                value = mSelectedText,
                onValueChange = { mSelectedText = it },
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    //we have to set disable param here because we set enabled = false
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                    backgroundColor = MaterialTheme.colors.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mExpanded = !mExpanded }
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("DropDown Demo") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { mExpanded = !mExpanded })
                }
            )

            DropdownMenu(
                //Whether the menu is currently open and visible to the user
                expanded = mExpanded,
                //Called when the user requests to dismiss the menu, such as by tapping outside the menu's bounds
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) {
                        //we extract mTextFieldSize from above textField (width of the textField)
                        mTextFieldSize.width.toDp()
                    })
            ) {
                searchBarList.take(5).forEach { label ->
                    DropdownMenuItem(onClick = {
                        mSelectedText = label
                        mExpanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}
