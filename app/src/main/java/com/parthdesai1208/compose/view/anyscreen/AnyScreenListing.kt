package com.parthdesai1208.compose.view.anyscreen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.anyscreen.sample1.AnyScreenSample1Activity
import com.parthdesai1208.compose.view.navigation.AnyScreenListingScreenPath

enum class AnyScreenListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    DemonstrateUIForTabletFoldableDesktop(
        R.string.tablet_foldable_desktop_compatible_ui_sample_from_codelabs,
        { }),
    InsetsLearning(R.string.insets, { StatusNavigationBarInsets(it) }),
}

@Composable
fun AnyScreenListingScreen(navController: NavHostController) {
    val context = LocalContext.current

    @Composable
    fun MyButton(
        title: AnyScreenListingEnumType
    ) {
        Button(
            onClick = {
                if (title.buttonTitle == R.string.tablet_foldable_desktop_compatible_ui_sample_from_codelabs) {
                    context.startActivity(Intent(context, AnyScreenSample1Activity::class.java))
                    return@Button
                }
                navController.navigate(AnyScreenListingScreenPath(title.buttonTitle))
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(stringResource(id = title.buttonTitle), textAlign = TextAlign.Center)
        }
    }

    BuildTopBarWithScreen(
        title = stringResource(id = R.string.any_screen_samples),
        screen = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<AnyScreenListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        },
        onBackIconClick = {
            navController.popBackStack()
        })
}

@Composable
fun ChildAnyScreenListingScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<AnyScreenListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

/*
@Composable
fun NavigateToAnyScreenSample1Activity() {
    val context = LocalContext.current
    context.startActivity(Intent(context, AnyScreenSample1Activity::class.java))
}*/
