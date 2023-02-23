package com.parthdesai1208.compose.view.networking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.R

enum class NetworkingListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    MoviesList(R.string.moviesList, { MoviesListScreen(viewModel(), navHostController = it) }),
    NewsList(R.string.newListUsingPaging3, { NewsListUsingPaging3(viewModel = viewModel()) })
}

object NetworkingListingDestinations {
    const val NETWORKING_LISTING_MAIN_SCREEN = "NETWORKING_LISTING_MAIN_SCREEN"
    const val NETWORKING_LISTING_SCREEN_ROUTE_PREFIX = "NETWORKING_LISTING_SCREEN_ROUTE_PREFIX"
    const val NETWORKING_LISTING_SCREEN_ROUTE_POSTFIX = "NETWORKING_LISTING_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun NetworkingListNavGraph(startDestination: String = NetworkingListingDestinations.NETWORKING_LISTING_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = NetworkingListingDestinations.NETWORKING_LISTING_MAIN_SCREEN) {
            NetworkingListingScreen(navController = navController)
        }

        composable(
            route = "${NetworkingListingDestinations.NETWORKING_LISTING_SCREEN_ROUTE_PREFIX}/{${NetworkingListingDestinations.NETWORKING_LISTING_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(NetworkingListingDestinations.NETWORKING_LISTING_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildDrawScreen(
                arguments.getString(NetworkingListingDestinations.NETWORKING_LISTING_SCREEN_ROUTE_POSTFIX),
                navController
            )
        }
    }
}

@Composable
fun NetworkingListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: NetworkingListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${NetworkingListingDestinations.NETWORKING_LISTING_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(stringResource(id = title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Text(
                text = "Networking Samples",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                enumValues<NetworkingListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}

@Composable
fun ChildDrawScreen(onClickButtonTitle: String?, navController: NavHostController) {
    enumValues<NetworkingListingEnumType>().first { it.buttonTitle.toString() == onClickButtonTitle }.func.invoke(
        navController
    )
}