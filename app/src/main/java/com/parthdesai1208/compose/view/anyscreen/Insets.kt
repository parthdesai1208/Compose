package com.parthdesai1208.compose.view.anyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen

@Composable
fun StatusNavigationBarInsets(navHostController: NavHostController) {
    BuildTopBarWithScreen(
        title = stringResource(id = R.string.insets),
        screen = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
                    .statusBarsPadding()
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.Red)
                        .safeDrawingPadding()
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp)
                            .background(Color.White)
                    )
                }
            }
        },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}
