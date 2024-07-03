package com.parthdesai1208.compose.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.DarkLightColor
import com.parthdesai1208.compose.view.theme.LightDarkColor

@Composable
fun Modifier.setSizeByScreenPercentage(
    widthPercentage: Float,
    heightPercentage: Float,
): Modifier {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp * (widthPercentage / 100)
    val height = configuration.screenHeightDp.dp * (heightPercentage / 100)
    return then(this.size(width, height))
}

@Composable
fun AddBackIconToScreen(
    screen: @Composable () -> Unit,
    onBackIconClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        screen()
        FloatingActionButton(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.TopStart)
                .size(36.dp),
            onClick = onBackIconClick,
            backgroundColor = LightDarkColor,
            contentColor = DarkLightColor,
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }
}