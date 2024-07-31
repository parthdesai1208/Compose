package com.parthdesai1208.compose.view.navigation.composeDestination

import android.os.Parcelable
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.BottomSheetUsingComposeDestinationDestination
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.DialogComposeDestination
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.SecondScreenDestination
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.ThirdScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun StartForComposeDestination() {
    //for without bottom sheet
//    DestinationsNavHost(navGraph = NavGraphs.root)

    //for bottom sheet you have to set things like below otherwise simply write 'DestinationsNavHost'
    val navController = rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator
    com.google.accompanist.navigation.material.ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetBackgroundColor = Color.Transparent
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            engine = rememberAnimatedNavHostEngine()
        )
    }

}

@RootNavGraph(start = true)
@Destination
@Composable
fun FirstScreen(navigator: DestinationsNavigator) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val context = LocalContext.current

    BuildTopBarWithScreen(
        title = stringResource(id = R.string.composedestination),
        screen = {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.first_screen))
                Button(onClick = {
                    navigator.navigate(
                        direction = SecondScreenDestination(
                            user = User(
                                name = context.getString(R.string.parth),
                                id = context.getString(R.string._123),
                                created = context.getString(R.string._16_08_2020)
                            )
                        )
                    )
                }) {
                    Text(text = stringResource(R.string.go_to_second_screen))
                }
                Button(onClick = { navigator.navigate(direction = DialogComposeDestination) }) {
                    Text(text = stringResource(R.string.open_dialog))
                }
                Button(onClick = { navigator.navigate(direction = BottomSheetUsingComposeDestinationDestination) }) {
                    Text(text = stringResource(R.string.open_bottomsheet))
                }
            }
        },
        onBackIconClick = {
            onBackPressedDispatcher?.onBackPressed()
        })
}

@Destination
@Composable
fun SecondScreen(navigator: DestinationsNavigator, user: User) {
    BuildTopBarWithScreen(
        title = stringResource(id = R.string.secondScreen),
        screen = {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.second_screen, user),
                    textAlign = TextAlign.Center
                )
                Button(onClick = {
                    navigator.navigate(direction = ThirdScreenDestination(show = true))
                }) {
                    Text(text = stringResource(R.string.go_to_third_screen))
                }
            }
        },
        onBackIconClick = {
            navigator.popBackStack()
        })
}

@Destination
@Composable
fun ThirdScreen(navigator: DestinationsNavigator, show: Boolean = false) {

    BuildTopBarWithScreen(
        title = stringResource(id = R.string.thirdScreen),
        screen = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.third_screen, show))
            }
        }, onBackIconClick = {
            navigator.popBackStack()
        })
}

@Parcelize
data class User(
    val name: String,
    val id: String,
    val created: String
) : Parcelable


@Destination(style = MyDialogStyle::class)
@Composable
fun DialogCompose(navigator: DestinationsNavigator) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "This is an error prompt",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            TextButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                Text(text = "OK")
            }
        }
    }
}

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun BottomSheetUsingComposeDestination(navigator: DestinationsNavigator) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "You are just open 'bottom sheet' using compose destination library",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            TextButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                Text(text = "Fine")
            }
        }
    }
}