package com.parthdesai1208.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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