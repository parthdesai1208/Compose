package com.parthdesai1208.compose.view.navigation.composeDestination

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination

@Composable
fun StartForComposeDestination() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}

@Destination(start = true)
@Composable
fun FirstScreen() {
    Surface {
        Text(text = "Home Screen")
    }
}