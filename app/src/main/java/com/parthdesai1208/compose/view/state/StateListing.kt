package com.parthdesai1208.compose.view.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R

enum class StateListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    LearnState(R.string.learnstate, {
        TodoActivityScreen(it, androidx.lifecycle.viewmodel.compose.viewModel())
    }),
    DependentVariableState(
        R.string.dependentVariableState,
        { DependentVariableState(it, androidx.lifecycle.viewmodel.compose.viewModel()) })
}

object StateDestinations {
    const val STATE_LISTING_MAIN_SCREEN = "STATE_LISTING_MAIN_SCREEN"
    const val STATE_LISTING_MAIN_SCREEN_ROUTE_PREFIX = "STATE_LISTING_MAIN_SCREEN_ROUTE_PREFIX"
    const val STATE_LISTING_MAIN_SCREEN_ROUTE_POSTFIX = "STATE_LISTING_MAIN_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun ChildScreenState(onClickButtonTitle: String?, navController: NavHostController) {
    enumValues<StateListingEnumType>().first { it.buttonTitle.toString() == onClickButtonTitle }.func.invoke(
        navController
    )
}

@Composable
fun MainScreenState(navController: NavHostController) {

    @Composable
    fun MyButton(
        title: StateListingEnumType,
    ) {
        val context = LocalContext.current

        Button(
            onClick = {
                navController.navigate("${StateDestinations.STATE_LISTING_MAIN_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(
                context.getString(title.buttonTitle),
                textAlign = TextAlign.Center
            )
        }
    }
    Surface {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "State Samples",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<StateListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}