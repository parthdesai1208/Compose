package com.parthdesai1208.compose.view.custom

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.core.os.ConfigurationCompat
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import java.util.*

//region Custom Modifier listing screen
enum class CustomLayoutListingEnumType(
    val buttonTitle: String,
    val func: @Composable () -> Unit
) {
    BottomBarCustomCompose("Bottom Bar", { BottomBarCustomCompose() }),
    MyOwnColumnFun("Column", { MyOwnColumnFun() }),
}

object CustomLayoutDestinations {
    const val CUSTOM_LAYOUT_MAIN_SCREEN = "CUSTOM_LAYOUT_MAIN_SCREEN"
    const val CUSTOM_LAYOUT_ROUTE_PREFIX = "CUSTOM_LAYOUT_ROUTE_PREFIX"
    const val CUSTOM_LAYOUT_ROUTE_POSTFIX = "CUSTOM_LAYOUT_ROUTE_POSTFIX"
}

@Composable
fun CustomLayoutNavGraph(startDestination: String = CustomLayoutDestinations.CUSTOM_LAYOUT_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = CustomLayoutDestinations.CUSTOM_LAYOUT_MAIN_SCREEN) {
            CustomLayoutListingScreen(navController = navController)
        }

        composable(
            route = "${CustomLayoutDestinations.CUSTOM_LAYOUT_ROUTE_PREFIX}/{${CustomLayoutDestinations.CUSTOM_LAYOUT_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(CustomLayoutDestinations.CUSTOM_LAYOUT_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildCustomLayoutScreen(arguments.getString(CustomLayoutDestinations.CUSTOM_LAYOUT_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun CustomLayoutListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: CustomLayoutListingEnumType
    ) {
        Button(
            onClick = { navController.navigate("${CustomLayoutDestinations.CUSTOM_LAYOUT_ROUTE_PREFIX}/${title.buttonTitle}") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(title.buttonTitle, textAlign = TextAlign.Center)
        }
    }
    Surface {
        Column {
            Text(
                text = "Custom Layout Samples",
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
                enumValues<CustomLayoutListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}

@Composable
fun ChildCustomLayoutScreen(onClickButtonTitle: String?) {
    enumValues<CustomLayoutListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}
//endregion

//region custom compose bottom bar
object BottomBarCustomComposeDestinations {
    const val HOME = "home"
}

enum class BottomBarCustomComposeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    FEED(R.string.home_feed, Icons.Outlined.Home, "home/feed"),
    SEARCH(R.string.home_search, Icons.Outlined.Search, "home/search"),
    CART(R.string.home_cart, Icons.Outlined.ShoppingCart, "home/cart"),
    PROFILE(R.string.home_profile, Icons.Outlined.AccountCircle, "home/profile")
}

@Composable
fun BottomBarCustomCompose() {
    val appState = rememberBottomBarCustomComposeAppState()
    Scaffold(bottomBar = {
        if (appState.shouldShowBottomBar) {
            BottomBarCustomComposeFun(
                tabs = appState.bottomBarTabs,
                currentRoute = appState.currentRoute!!,
                navigateToRoute = appState::navigateToBottomBarRoute
            )
        }
    }) { innerPaddingModifier ->
        NavHost(
            navController = appState.navController,
            startDestination = BottomBarCustomComposeDestinations.HOME,
            modifier = Modifier.padding(innerPaddingModifier)
        ) {
            navigation(
                route = BottomBarCustomComposeDestinations.HOME,
                startDestination = BottomBarCustomComposeSections.FEED.route
            ) {
                composable(route = BottomBarCustomComposeSections.FEED.route) {
                    CommonScreenForBottomBarCustomCompose(screenName = "Feed")
                }
                composable(route = BottomBarCustomComposeSections.SEARCH.route) {
                    CommonScreenForBottomBarCustomCompose(screenName = "Search")
                }
                composable(route = BottomBarCustomComposeSections.CART.route) {
                    CommonScreenForBottomBarCustomCompose(screenName = "Cart")
                }
                composable(route = BottomBarCustomComposeSections.PROFILE.route) {
                    CommonScreenForBottomBarCustomCompose(screenName = "Profile")
                }
            }
        }

    }
}

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

@Composable
fun CommonScreenForBottomBarCustomCompose(modifier: Modifier = Modifier, screenName: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Text(text = screenName)
    }
}

@Composable
fun BottomBarCustomComposeFun(
    tabs: Array<BottomBarCustomComposeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }

    val springSpec = SpringSpec<Float>(
        // Determined experimentally
        stiffness = 800f,
        dampingRatio = 0.8f
    )
    BottomBarCustomComposeNavLayout(
        selectedIndex = currentSection.ordinal,
        itemCount = routes.size,
        indicator = { BottomBarCustomComposeNavIndicator() },
        animSpec = springSpec,
        modifier = Modifier.navigationBarsPadding()
    ) {
        val configuration = LocalConfiguration.current
        val currentLocale: Locale =
            ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()

        tabs.forEach { section ->
            val selected = section == currentSection
            val tint by animateColorAsState(
                if (selected) {
                    MaterialTheme.colors.primaryVariant
                } else {
                    MaterialTheme.colors.onSurface
                }
            )

            val text = stringResource(section.title).uppercase(currentLocale)

            BottomBarCustomComposeNavigationItem(
                icon = {
                    Icon(
                        imageVector = section.icon,
                        tint = tint,
                        contentDescription = text
                    )
                },
                text = {
                    Text(
                        text = text,
                        color = tint,
                        style = MaterialTheme.typography.button,
                        maxLines = 1
                    )
                },
                selected = selected,
                onSelected = { navigateToRoute(section.route) },
                animSpec = springSpec,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(percent = 50))
            )
        }
    }
}

@Composable
fun BottomBarCustomComposeNavLayout(
    selectedIndex: Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Track how "selected" each item is [0, 1]
    val selectionFractions = remember(itemCount) {
        List(itemCount) { i ->
            Animatable(if (i == selectedIndex) 1f else 0f)
        }
    }
    selectionFractions.forEachIndexed { index, selectionFraction ->
        val target = if (index == selectedIndex) 1f else 0f
        LaunchedEffect(target, animSpec) {
            selectionFraction.animateTo(target, animSpec)
        }
    }

    // Animate the position of the indicator
    val indicatorIndex = remember { Animatable(0f) }
    val targetIndicatorIndex = selectedIndex.toFloat()
    LaunchedEffect(targetIndicatorIndex) {
        indicatorIndex.animateTo(targetIndicatorIndex, animSpec)
    }

    Layout(
        modifier = modifier.height(56.dp),
        content = {
            content()
            Box(Modifier.layoutId("indicator"), content = indicator)
        }
    ) { measurables, constraints ->
        check(itemCount == (measurables.size - 1)) // account for indicator

        // Divide the width into n+1 slots and give the selected item 2 slots
        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = 2 * unselectedWidth
        val indicatorMeasurable = measurables.first { it.layoutId == "indicator" }

        val itemPlaceables = measurables
            .filterNot { it == indicatorMeasurable }
            .mapIndexed { index, measurable ->
                // Animate item's width based upon the selection amount
                val width = lerp(unselectedWidth, selectedWidth, selectionFractions[index].value)
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width
                    )
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(
                minWidth = selectedWidth,
                maxWidth = selectedWidth
            )
        )

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
        ) {
            val indicatorLeft = indicatorIndex.value * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.placeRelative(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}

@Composable
fun BottomBarCustomComposeNavIndicator(
    strokeWidth: Dp = 2.dp,
    color: Color = MaterialTheme.colors.primaryVariant,
    shape: Shape = RoundedCornerShape(percent = 50)
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            .border(strokeWidth, color, shape)
    )
}

@Composable
fun BottomBarCustomComposeNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelected),
        contentAlignment = Alignment.Center
    ) {
        // Animate the icon/text positions within the item based on selection
        val animationProgress by animateFloatAsState(if (selected) 1f else 0f, animSpec)
        BottomBarCustomComposeItemLayout(
            icon = icon,
            text = text,
            animationProgress = animationProgress
        )
    }
}

@Composable
fun BottomBarCustomComposeItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .layoutId("icon")
                    .padding(horizontal = 2.dp),
                content = icon
            )
            val scale = lerp(0.6f, 1f, animationProgress)
            Box(
                modifier = Modifier
                    .layoutId("text")
                    .padding(horizontal = 2.dp)
                    .graphicsLayer {
                        alpha = animationProgress
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin(0f, 0.5f)
                    },
                content = text
            )
        }
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        val width = constraints.maxWidth
        val height = constraints.maxHeight

        val iconY = (height - iconPlaceable.height) / 2
        val textY = (height - textPlaceable.height) / 2

        val textWidth = textPlaceable.width * animationProgress
        val iconX = (width - textWidth - iconPlaceable.width) / 2
        val textX = iconX + iconPlaceable.width

        return@Layout layout(width, height) {
            iconPlaceable.placeRelative(iconX.toInt(), iconY)
            if (animationProgress != 0f) {
                textPlaceable.placeRelative(textX.toInt(), textY)
            }
        }
    }
}

@Composable
fun rememberBottomBarCustomComposeAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) =
    remember(scaffoldState, navController) {
        BottomBarCustomComposeAppState(scaffoldState, navController)
    }

class BottomBarCustomComposeAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController
) {

    val bottomBarTabs = BottomBarCustomComposeSections.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    // Reading this attribute will cause recompositions when the bottom bar needs shown, or not.
    // Not all routes need to show the bottom bar.
    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    // ----------------------------------------------------------
    // Navigation state source of truth
    // ----------------------------------------------------------

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

}

//endregion

//region my own column
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun MyOwnColumnFun(modifier: Modifier = Modifier) {
    Surface {
        MyOwnColumn(modifier.padding(8.dp)) {
            Text("MyOwnColumn")
            Text("places items")
            Text("vertically.")
            Text("We've done it by hand!")
        }
    }
}
//endregion