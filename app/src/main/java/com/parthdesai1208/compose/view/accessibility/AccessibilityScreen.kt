package com.parthdesai1208.compose.view.accessibility

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.parthdesai1208.compose.model.accessibility.AppContainer
import com.parthdesai1208.compose.view.theme.ComposeTheme
import kotlinx.coroutines.launch

@Composable
fun AccessibilityScreen(
    appContainer: AppContainer, navHostController: NavHostController
) {
    ComposeTheme {
        ProvideWindowInsets {

            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            // This top level scaffold contains the app drawer, which needs to be accessible
            // from multiple screens. An event to open the drawer is passed down to each
            // screen that needs it.
            val scaffoldState = rememberScaffoldState()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.HOME_ROUTE
            Scaffold(
                scaffoldState = scaffoldState,
                drawerContent = {
                    AppDrawer(
                        currentRoute = currentRoute,
                        navigateToHome = { navController.navigate(MainDestinations.HOME_ROUTE) },
                        navigateToInterests = { navController.navigate(MainDestinations.INTERESTS_ROUTE) },
                        closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } },
                        onExitClick = { navHostController.popBackStack() }
                    )
                }
            ) {
                JetnewsNavGraph(
                    appContainer = appContainer,
                    modifier = Modifier.padding(it),
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            }
        }
    }
}
