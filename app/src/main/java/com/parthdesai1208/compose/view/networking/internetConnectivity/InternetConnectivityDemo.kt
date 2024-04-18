package com.parthdesai1208.compose.view.networking.internetConnectivity

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.networking.ConnectionState
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.utils.connectivityState
import com.parthdesai1208.compose.view.theme.LightDarkContentColor
import com.parthdesai1208.compose.view.theme.googlePhotosRedColor
import com.parthdesai1208.compose.view.theme.spotifyGreenColor
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Phone
@Composable
fun InternetConnectivityDemo() {
    var isVisibleBanner by rememberSaveable { mutableStateOf(false) }

    val connection by connectivityState()

    LaunchedEffect(key1 = connection == ConnectionState.Available) {
        isVisibleBanner = true
    }

    DisposableEffect(key1 = isVisibleBanner) {

        val counter = object : CountDownTimer(5000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isVisibleBanner = false
            }
        }
        counter.start()
        onDispose {
            counter.cancel()
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            if (connection == ConnectionState.Available) {
                InternetConnectivityUI(
                    visibility = isVisibleBanner,
                    text = stringResource(id = R.string.backOnline),
                    icon = Icons.Default.CloudDone,
                    bgColor = spotifyGreenColor,
                )
            } else {
                InternetConnectivityUI(
                    visibility = true,
                    text = stringResource(id = R.string.noInternet),
                    icon = Icons.Default.CloudOff,
                    bgColor = googlePhotosRedColor,
                )
            }
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.observeInternetConnectivityDemo),
            color = LightDarkContentColor
        )
    }


}