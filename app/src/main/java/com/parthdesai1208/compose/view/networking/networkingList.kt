package com.parthdesai1208.compose.view.networking

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.view.navigation.NetworkListingScreenPath
import com.parthdesai1208.compose.view.networking.internetConnectivity.InternetConnectivityDemo

enum class NetworkingListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    MoviesList(R.string.moviesList, { MoviesListScreen(viewModel(), navHostController = it) }),

    NewsList(R.string.newListUsingPaging3, { NewsListUsingPaging3(viewModel = viewModel(), it) }),
    Paging3Room(R.string.Paging3Room, { Paging3Room(it) }),
    InternetConnectivity(R.string.internetConnectivityDemo, { InternetConnectivityDemo(it) }),
}

@Composable
fun NetworkingListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: NetworkingListingEnumType
    ) {
        Button(
            onClick = { navController.navigate(NetworkListingScreenPath(title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(stringResource(id = title.buttonTitle), textAlign = TextAlign.Center)
        }
    }
    BuildTopBarWithScreen(
        title = stringResource(id = R.string.networking),
        screen = {
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
        },
        onBackIconClick = {
            navController.popBackStack()
        })
}

@Composable
fun ChildNetworkListingScreen(onClickButtonTitle: Int?, navController: NavHostController) {
    enumValues<NetworkingListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navController
    )
}