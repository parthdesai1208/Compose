package com.parthdesai1208.compose.view.custom

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.core.os.ConfigurationCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Constant.CUSTOM_LAYOUT_ANIMATE_COLOR_AS_STATE
import com.parthdesai1208.compose.utils.Constant.CUSTOM_LAYOUT_ANIMATE_FLOAT_AS_STATE
import com.parthdesai1208.compose.view.custom.BottomBarCustomComposeDestinations.ICON
import com.parthdesai1208.compose.view.custom.BottomBarCustomComposeDestinations.INDICATOR
import com.parthdesai1208.compose.view.custom.BottomBarCustomComposeDestinations.TEXT
import com.parthdesai1208.compose.view.navigation.CustomLayoutScreen
import java.util.Locale

//region Custom Modifier listing screen
enum class CustomLayoutListingEnumType(
    val buttonTitle: Int,
    val func: @Composable (NavHostController) -> Unit
) {
    BottomBarCustomCompose(R.string.bottom_bar, { BottomBarCustomCompose(it) }),
    MyOwnColumnFun(R.string.column, { MyOwnColumnFun(it) }),
    BannerSample(R.string.banner, { BannerSampleScreen(it) }),
}

@Composable
fun CustomLayoutListingScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: CustomLayoutListingEnumType
    ) {
        val context = LocalContext.current
        Button(
            onClick = { navController.navigate(CustomLayoutScreen(pathPostFix = title.buttonTitle)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(context.getString(title.buttonTitle), textAlign = TextAlign.Center)
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
                    text = stringResource(R.string.custom_layout_samples),
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
                enumValues<CustomLayoutListingEnumType>().forEach {
                    MyButton(it)
                }
            }
        }
    }
}
//endregion

@Composable
fun ChildCustomLayoutScreen(onClickButtonTitle: Int?, navHostController: NavHostController) {
    enumValues<CustomLayoutListingEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke(
        navHostController
    )
}

//region custom compose bottom bar
object BottomBarCustomComposeDestinations {
    const val HOME = "home"
    const val INDICATOR = "indicator"
    const val ICON = "icon"
    const val TEXT = "text"
}

enum class BottomBarCustomLayoutScreens(
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
fun BottomBarCustomCompose(navHostController: NavHostController) {
    val appState = rememberBottomBarCustomComposeAppState()
    BuildTopBarWithScreen(screen = {
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
                    startDestination = BottomBarCustomLayoutScreens.FEED.route
                ) {
                    composable(route = BottomBarCustomLayoutScreens.FEED.route) {
                        CommonScreenForBottomBarCustomCompose(screenName = "Feed")
                    }
                    composable(route = BottomBarCustomLayoutScreens.SEARCH.route) {
                        CommonScreenForBottomBarCustomCompose(screenName = "Search")
                    }
                    composable(route = BottomBarCustomLayoutScreens.CART.route) {
                        CommonScreenForBottomBarCustomCompose(screenName = "Cart")
                    }
                    composable(route = BottomBarCustomLayoutScreens.PROFILE.route) {
                        CommonScreenForBottomBarCustomCompose(screenName = "Profile")
                    }
                }
            }
        }
    }) {
        navHostController.popBackStack()
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
    tabs: Array<BottomBarCustomLayoutScreens>,
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
                }, label = CUSTOM_LAYOUT_ANIMATE_COLOR_AS_STATE
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
            Box(Modifier.layoutId(INDICATOR), content = indicator)
        }
    ) { measurables, constraints ->
        check(itemCount == (measurables.size - 1)) // account for indicator

        // Divide the width into n+1 slots and give the selected item 2 slots
        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = 2 * unselectedWidth
        val indicatorMeasurable = measurables.first { it.layoutId == INDICATOR }

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
        val animationProgress by animateFloatAsState(
            if (selected) 1f else 0f,
            animSpec,
            label = CUSTOM_LAYOUT_ANIMATE_FLOAT_AS_STATE
        )
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
                    .layoutId(ICON)
                    .padding(horizontal = 2.dp),
                content = icon
            )
            val scale = lerp(0.6f, 1f, animationProgress)
            Box(
                modifier = Modifier
                    .layoutId(TEXT)
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
        val iconPlaceable = measurables.first { it.layoutId == ICON }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == TEXT }.measure(constraints)

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

    val bottomBarTabs = BottomBarCustomLayoutScreens.entries.toTypedArray()
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
fun MyOwnColumnFun(navHostController: NavHostController, modifier: Modifier = Modifier) {
    BuildTopBarWithScreen(title = stringResource(id = R.string.column), screen = {
        MyOwnColumn(modifier.padding(8.dp)) {
            Text(stringResource(R.string.myowncolumn))
            Text(stringResource(R.string.places_items))
            Text(stringResource(R.string.vertically))
            Text(stringResource(R.string.we_ve_done_it_by_hand))
        }
    }) {
        navHostController.popBackStack()
    }
}
//endregion

//region banner
@Composable
fun BannerSampleScreen(navHostController: NavHostController) {
    var isVisibleBanner by rememberSaveable { mutableStateOf(false) }
    BuildTopBarWithScreen(title = stringResource(id = R.string.banner), screen = {
        Box(modifier = Modifier.fillMaxSize()) {
            Banner(bannerText = stringResource(R.string.this_is_banner_text),
                visibility = isVisibleBanner,
                actionButton1Text = stringResource(R.string.dismiss),
                onClickActionButton1 = { isVisibleBanner = false })
            Button(
                enabled = !isVisibleBanner,
                modifier = Modifier.align(Alignment.Center),
                onClick = { isVisibleBanner = true }) {
                Text(text = stringResource(R.string.show_banner))
            }
        }
    }) {
        navHostController.popBackStack()
    }
}

@Composable
fun Banner(
    bannerText: String,
    visibility: Boolean = true,
    actionButton1Text: String? = null,
    actionButton2Text: String? = null,
    onClickActionButton1: () -> Unit = {},
    onClickActionButton2: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(),
        exit = slideOutVertically(),
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = bannerText)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    actionButton1Text?.let {
                        TextButton(onClick = onClickActionButton1) {
                            Text(text = actionButton1Text)
                        }
                    }
                    actionButton2Text?.let {
                        TextButton(onClick = onClickActionButton2) {
                            Text(text = actionButton2Text)
                        }
                    }
                }
                Divider()
            }
        }
    }

}
//endregion