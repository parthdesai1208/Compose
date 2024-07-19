package com.parthdesai1208.compose.view.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.Phone

@Phone
@Composable
fun ListItemCompose(navHostController: NavHostController) {

    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material.Icon(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material.Text(
                    text = stringResource(R.string.listItemSample),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
    }
}