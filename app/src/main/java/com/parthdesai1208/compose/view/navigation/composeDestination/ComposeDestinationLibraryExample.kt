package com.parthdesai1208.compose.view.navigation.composeDestination

import android.os.Parcelable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.BottomSheetUsingComposeDestinationDestination
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.DialogComposeDestination
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.SecondScreenDestination
import com.parthdesai1208.compose.view.navigation.composeDestination.destinations.ThirdScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun StartForComposeDestination() {
    //for without bottom sheet
//    DestinationsNavHost(navGraph = NavGraphs.root)

    //for bottom sheet you have to set things like below otherwise simply write 'DestinationsNavHost'
    val navController = rememberAnimatedNavController()
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
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "First Screen")
            Button(onClick = {
                navigator.navigate(
                    direction = SecondScreenDestination(
                        user = User(
                            name = "Parth",
                            id = "123",
                            created = "16-08-2020"
                        )
                    )
                )
            }) {
                Text(text = "Go to second screen")
            }
            Button(onClick = { navigator.navigate(direction = DialogComposeDestination) }) {
                Text(text = "Open Dialog")
            }
            Button(onClick = { navigator.navigate(direction = BottomSheetUsingComposeDestinationDestination) }) {
                Text(text = "Open BottomSheet")
            }
        }
    }
}

@Destination
@Composable
fun SecondScreen(navigator: DestinationsNavigator, user: User) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Second Screen : $user", textAlign = TextAlign.Center)
            Button(onClick = {
                navigator.navigate(direction = ThirdScreenDestination(show = true))
            }) {
                Text(text = "Go to third screen")
            }
        }
    }
}

@Destination
@Composable
fun ThirdScreen(show: Boolean = false) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Third Screen : $show")
        }
    }
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

@Destination(style = DestinationStyle.BottomSheet::class)
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