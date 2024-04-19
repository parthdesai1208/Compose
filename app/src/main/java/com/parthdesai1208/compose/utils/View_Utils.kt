package com.parthdesai1208.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

fun Modifier.delayedClick(delay: Long = 300L, onClick : () -> Unit) = composed {
    var enableClick by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = enableClick, block = {
        if(enableClick) return@LaunchedEffect
        delay(delay)
        enableClick = true
    })
    this.clickable {
        if(enableClick){
            enableClick = false
            onClick()
        }
    }
}

@Composable
fun ToolBarWithIconAndTitle(
    screenTitle: String? = null,
    onBackArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickable(onClick = { onBackArrowClick() }),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        screenTitle?.let {
            Text(
                text = screenTitle,
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}