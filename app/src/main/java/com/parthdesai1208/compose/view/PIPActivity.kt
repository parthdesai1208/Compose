package com.parthdesai1208.compose.view

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.MutableLiveData
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.ComposeTheme

class PIPActivity : ComponentActivity() {

    private val isPIPSupported by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
        } else {
            false
        }
    }

    private val aspectRatio = Rational(16, 9) //for rectangle window

    //    private val aspectRatio = Rational(1, 1) //for square window
    private var videoViewBound = Rect()
    private var isInPIPMode = MutableLiveData<Boolean>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                PIPScreen()
            }
        }
        Log.e("lifecycle", "onCreate()")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun PIPScreen() {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                AndroidView(factory = {
                    VideoView(it, null).apply {
                        setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.sample}"))
                        start() //to play video
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        videoViewBound = it
                            .boundsInWindow()
                            .toAndroidRect()
                    })

                OtherUnImportantView()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun OtherUnImportantView() {
        val isInPIPModeValue by isInPIPMode.observeAsState(initial = false)
        AnimatedVisibility(visible = !isInPIPModeValue) {
            Column {
                Text(
                    text = "App for demonstrating the Picture in Picture mode in Android",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .padding(all = 16.dp),
                    style = MaterialTheme.typography.h6
                )
                Button(
                    onClick = { enterPIPMode() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .padding(all = 16.dp)
                ) {
                    Text(text = "Enter PIP", style = MaterialTheme.typography.button)
                }
            }
        }
    }

    //region PIP lifecycle methods
    //this method is triggered when
    //Home or recent button is pressed
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
        enterPIPMode()
    }

    //trigger when pip mode change
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean, newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        isInPIPMode.value = isInPictureInPictureMode
        /*if (isInPictureInPictureMode) {
            //hide all unimportant views
        } else {
            //show all unimportant views
        }*/
    }

    //endregion

    @RequiresApi(Build.VERSION_CODES.O)
    fun enterPIPMode() {
        if (isPIPSupported) {
            //region icon displayed in PIP mode layer
            val actions: ArrayList<RemoteAction> = ArrayList()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            val openBrowserAction = RemoteAction(
                Icon.createWithResource(this, android.R.drawable.ic_menu_info_details),
                "title here",
                "description here",
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            )
            val openBrowserAction1 = RemoteAction(
                Icon.createWithResource(this, android.R.drawable.ic_delete),
                "title here",
                "description here",
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            )
            actions.add(openBrowserAction)
            actions.add(openBrowserAction1)
            //you can add multiple actions here
//        🗒️ Note: If an app has a video playing, then play, pause, next, and previous controls will appear by default.
            //endregion

            val pipBuilder = PictureInPictureParams.Builder()
            pipBuilder.setAspectRatio(aspectRatio)
                .setSourceRectHint(videoViewBound) //for smother transition of window
                .setActions(actions).build()
            enterPictureInPictureMode(pipBuilder.build())
        }
    }


    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy()")
    }
}
