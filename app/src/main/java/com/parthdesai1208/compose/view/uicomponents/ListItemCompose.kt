package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.utils.Phone

@Phone
@Composable
fun ListItemCompose() {

    LazyColumn {
        items(count = 10) { index ->
            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "favorite icon",
                    )
                },
                headlineContent = { Text("Headline text") },
                supportingContent = { Text("supporting text") },
                trailingContent = {
                    Icon(
                        Icons.Filled.Info, contentDescription = "Info icon"
                    )
                },
                overlineContent = {
                    Text("${index + 1}. this is overline content")
                },
                modifier = Modifier
                    .padding(all = 10.dp)
                    .clip(RoundedCornerShape(8.dp)),
                tonalElevation = 16.dp,
                shadowElevation = 8.dp,
            )
            HorizontalDivider()
        }
    }
}