package com.parthdesai1208.compose.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.LightDarkContentColor
import com.parthdesai1208.compose.view.theme.LocalAppGaps

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

    val analytics = LocalAnalytics.current //example of CompositionLocal
    //other example of CompositionLocal
    //MaterialTheme.colors.onSurface
    //MaterialTheme.typography.body2
    //MaterialTheme.shapes.medium
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { analytics.logEvent("Basic Setup of CompositionLocal", context) }) {
            Text("Click me! (using staticCompositionLocalOf)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            textAlign = TextAlign.Center,
            color = LightDarkContentColor,
            text = "Gap between icon & text is implemented using compositionLocalOf{}"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { }) {
            Icon(imageVector = Icons.Filled.CloudCircle, contentDescription = null)
            Spacer(modifier = Modifier.width(LocalAppGaps.current.medium))
            Text("using compositionLocalOf")
        }
    }
}
