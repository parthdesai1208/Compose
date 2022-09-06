package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BadgeCompose() {
    Surface {
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)) {
            BadgedBox(badge = { Badge { Text("8") } }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}
