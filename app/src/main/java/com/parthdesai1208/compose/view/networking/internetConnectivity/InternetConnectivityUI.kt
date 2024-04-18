package com.parthdesai1208.compose.view.networking.internetConnectivity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.view.theme.LightDarkContentColor
import com.parthdesai1208.compose.view.theme.googlePhotosRedColor
import com.parthdesai1208.compose.view.theme.spotifyGreenColor


@Phone
@Composable
private fun InternetConnectivitySample() {
    InternetConnectivityUI(
        visibility = true,
        text = stringResource(id = R.string.backOnline),
        icon = Icons.Default.CloudDone,
        bgColor = spotifyGreenColor,
    )
}

@Phone
@Composable
private fun NoInternetConnectivitySample() {
    InternetConnectivityUI(
        visibility = true,
        text = stringResource(id = R.string.noInternet),
        icon = Icons.Default.CloudOff,
        bgColor = googlePhotosRedColor,
    )
}

@Composable
fun InternetConnectivityUI(
    visibility: Boolean, text: String, icon: ImageVector, bgColor: Color
) {
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(),
        exit = slideOutVertically(),
    ) {
        Row(
            modifier = Modifier
                .background(color = bgColor)
                .fillMaxWidth()
                .padding(8.dp)
                .height(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon, contentDescription = null,
                tint = LightDarkContentColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = LightDarkContentColor,
                text = text,
                maxLines = 1
            )
        }
    }
}