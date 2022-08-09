package com.parthdesai1208.compose.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

interface Analytics {
    fun logEvent(eventName: String, context: Context)
}

class LoggingAnalytics : Analytics {
    override fun logEvent(eventName: String, context: Context) {
        Toast.makeText(context, eventName, Toast.LENGTH_SHORT).show()
    }
}

val LocalAnalytics = staticCompositionLocalOf<Analytics> {
    LoggingAnalytics()
}

@Composable
fun CompositionLocalFun() {
    val context = LocalContext.current

    //CompositionLocal?????
    //to access data from any composable function without passing them via the function's parameters
    val analytics = LocalAnalytics.current //example of CompositionLocal
    //other example of CompositionLocal
    //MaterialTheme.colors.onSurface
    //MaterialTheme.typography.body2
    //MaterialTheme.shapes.medium
    Surface {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically),
            onClick = { analytics.logEvent("Basic Setup of CompositionLocal", context) }) {
            Text("Click me!")
        }
    }
}
