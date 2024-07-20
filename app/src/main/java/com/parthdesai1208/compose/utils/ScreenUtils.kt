package com.parthdesai1208.compose.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun BuildTopBarWithScreen(
    title: String = "",
    screen: @Composable () -> Unit,
    onBackIconClick: () -> Unit,
) {
    Surface {
        if (title.isEmpty()) {
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
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        } else {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            onBackIconClick.invoke()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                screen()
            }
        }
    }
}

fun Context.getActivityOrNull(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }

    return null
}