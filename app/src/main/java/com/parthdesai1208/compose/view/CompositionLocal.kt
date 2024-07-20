package com.parthdesai1208.compose.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
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
fun CompositionLocalFun(navHostController: NavHostController) {
    val context = LocalContext.current

    val analytics = LocalAnalytics.current //example of CompositionLocal
    //other example of CompositionLocal
    //MaterialTheme.colors.onSurface
    //MaterialTheme.typography.body2
    //MaterialTheme.shapes.medium
    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = context.getString(R.string.compositionLocalSample),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        analytics.logEvent(
                            "Basic Setup of CompositionLocal",
                            context
                        )
                    }) {
                    Text("Click me! (using staticCompositionLocalOf)")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    textAlign = TextAlign.Center,
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
    }
}
