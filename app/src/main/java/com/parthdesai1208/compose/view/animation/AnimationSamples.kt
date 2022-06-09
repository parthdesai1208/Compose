@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED", "SameParameterValue")

package com.parthdesai1208.compose.view.animation

import android.animation.TimeInterpolator
import android.util.DisplayMetrics
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parthdesai1208.compose.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

//region default screen & Navigation
enum class AnimationScreenEnumType(val buttonTitle: String, val func: @Composable () -> Unit) {
    AnimatedVisibilityWithoutParams(
        "AnimatedVisibility - without params",
        { AnimatedVisibilityWithoutParams() }),
    AnimatedVisibilityWithParams(
        "AnimatedVisibility - with params",
        { AnimatedVisibilityWithParams() }),
    AnimateVisibilityState("AnimatedVisibility - with state", { AnimateVisibilityState() }),
    AnimateEnterExitChild("enter exit visibility animation", { AnimateEnterExitChild() }),
    CrossFade("CrossFade", { CrossFade() }),
    AnimatableOnly("AnimatableOnly", { AnimatableOnly() }),
    AnimatedContentSimple("AnimatedContentSimple", { AnimatedContentSimple() }),
    AnimatedContentWithTransitionSpec1("AnimatedContent - with targetState, transitionSpec ex-1",
        { AnimatedContentWithTransitionSpec1() }),
    AnimatedContentWithTransitionSpec2(
        "AnimatedContent - with targetState, transitionSpec ex-2",
        { AnimatedContentWithTransitionSpec2() }),
    AnimatedContentWithTransitionSpec3(
        "AnimatedContent - with targetState, transitionSpec ex-3",
        { AnimatedContentWithTransitionSpec3() }),
    AnimatedContentSize("AnimatedContentSize", { AnimatedContentSize() }),
    AnimatedContentSizeTransform(
        "AnimatedContentSizeTransform",
        { AnimatedContentSizeTransform() }),
    AnimateFloatAsState("AnimateFloatAsState", { AnimateFloatAsState() }),
    AnimateColorAsState("AnimateColorAsState", { AnimateColorAsState() }),
    AnimateDpAsState("animateDpAsState", { AnimateDpAsState() }),
    AnimateSizeAsState("AnimateSizeAsState", { AnimateSizeAsState() }),
    UpdateTransition1("updateTransition-1", { UpdateTransitionBasic1() }),
    UpdateTransition2("updateTransition-2", { UpdateTransitionBasic2() }),
    UpdateTransitionChild("UpdateTransitionChild", { UpdateTransitionChild() }),
    UpdateTransitionExtension("multiple anim updateTransition", { UpdateTransitionExtension() }),
    MultipleAnimCoroutineAnimateTo("multiple anim Coroutine animateTo()\n(Rotate & color change)",
        { MultipleAnimCoroutineAnimateTo() }),
    InfiniteAnimation("InfiniteAnimation", { InfiniteAnimation() }),
    TargetBasedAnimation("TargetBasedAnimation", { TargetBasedAnimationFun() }),
    Spring("spring", { SpringFun() }),
    Tween("tween", { TweenFun() }),
    Keyframes("keyframes", { KeyFramesFun() }),
    Repeatable("repeatable", { RepeatableFun() }),
    InfiniteRepeatable("InfiniteRepeatable", { InfiniteRepeatableFun() }),
    Snap("snap", { SnapFun() }),
    AnimationVector("AnimationVector - TypeConverter,Coroutine", { AnimationVectorFun() }),
    AnimationEx1("AnimationEx1", { AnimationEx1() }),
    BoxWithIconUpDownAnimation("Icon Up-down animation",{ BoxWithIconUpDownAnimation() }),
}

object AnimationDestinations {
    const val ANIMATION_MAIN_SCREEN = "animationMainScreen"
    const val ANIMATION_SCREEN_ROUTE_PREFIX = "ANIMATION_SCREEN_ROUTE_PREFIX"
    const val ANIMATION_SCREEN_ROUTE_POSTFIX = "ANIMATION_SCREEN_ROUTE_POSTFIX"
}

@Composable
fun AnimationScreen(navController: NavHostController) {
    @Composable
    fun MyButton(
        title: AnimationScreenEnumType
    ) {
        Button(
            onClick = { navController.navigate("${AnimationDestinations.ANIMATION_SCREEN_ROUTE_PREFIX}/${title.buttonTitle}") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text(title.buttonTitle, textAlign = TextAlign.Center)
        }
    }

    Column {
        Text(
            text = "Animation Samples",
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            enumValues<AnimationScreenEnumType>().forEach {
                MyButton(it)
            }
        }
    }
}

@Composable
fun AnimationNavGraph(startDestination: String = AnimationDestinations.ANIMATION_MAIN_SCREEN) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = AnimationDestinations.ANIMATION_MAIN_SCREEN) {
            AnimationScreen(navController = navController)
        }

        composable(
            route = "${AnimationDestinations.ANIMATION_SCREEN_ROUTE_PREFIX}/{${AnimationDestinations.ANIMATION_SCREEN_ROUTE_POSTFIX}}",
            arguments = listOf(navArgument(AnimationDestinations.ANIMATION_SCREEN_ROUTE_POSTFIX) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildAnimationScreen(arguments.getString(AnimationDestinations.ANIMATION_SCREEN_ROUTE_POSTFIX))
        }
    }
}

@Composable
fun ChildAnimationScreen(onClickButtonTitle: String?) {
    enumValues<AnimationScreenEnumType>().first { it.buttonTitle == onClickButtonTitle }.func.invoke()
}
//endregion

/*
*********************************************************************************************************************************
AnimatedVisibility - without params
**********************************************************************************************************************************/
@Preview(showSystemUi = true)
@Composable
fun AnimatedVisibilityWithoutParams() {
    var visibility by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visibility) {
            Text(text = "Hello, world!", color = MaterialTheme.colors.onSurface)
        }

        Button(onClick = { visibility = !visibility }) {
            Text(text = "Click Me")
        }
    }
}

/*
*********************************************************************************************************************************
AnimatedVisibility - with params
**********************************************************************************************************************************/
@Preview(showSystemUi = true)
@Composable
fun AnimatedVisibilityWithParams() {
    var visible by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,
            //execute when view is visible
            enter = fadeIn(tween(4000)) + expandVertically(
                animationSpec = tween(
                    4000,
                    easing = android.view.animation.BounceInterpolator().toEasing()
                )
            ),
            //execute when view is gone
            exit = fadeOut(tween(4000)) + shrinkVertically(
                animationSpec = tween(
                    4000,
                    easing = android.view.animation.BounceInterpolator().toEasing()
                )
            )
        ) {
            Text(text = "Hello, world!", Modifier.background(MaterialTheme.colors.secondary))
        }
        Button(onClick = { visible = !visible }) {
            Text("Click Me")
        }
    }
}

fun TimeInterpolator.toEasing() = Easing { x -> getInterpolation(x) }

/**********************************************************************************************************************************
AnimatedVisibility - with state
 **********************************************************************************************************************************/
@Preview(showSystemUi = true)
@Composable
fun AnimateVisibilityState() {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visibleState = state,
            //execute when view is visible
            enter = fadeIn(tween(4000)) + expandVertically(
                animationSpec = tween(
                    4000,
                    easing = android.view.animation.BounceInterpolator().toEasing()
                )
            ),
            //execute when view is gone
            exit = fadeOut(tween(4000)) + shrinkVertically(
                animationSpec = tween(
                    4000,
                    easing = android.view.animation.BounceInterpolator().toEasing()
                )
            )
        ) {
            // Use the MutableTransitionState to know the current animation state
            // of the AnimatedVisibility.
            Text(
                text = when {
                    state.isIdle && state.currentState -> "Hello, World!"
                    !state.isIdle && state.currentState -> "Disappearing"
                    state.isIdle && !state.currentState -> ""
                    else -> "Appearing"
                }, color = MaterialTheme.colors.onSurface
            )
        }
        Button(onClick = { state.targetState = !state.targetState }) {
            Text("Click Me")
        }
    }
}

/**********************************************************************************************************************************
enter exit visibility animation
 **********************************************************************************************************************************/
@Preview(showSystemUi = true)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateEnterExitChild() {
    var visible by remember {
        mutableStateOf(true)
    }

    var color by remember {
        mutableStateOf(Color.Black)
    }
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Hide" else "Show")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color)
        )
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            val background by transition.animateColor(label = "") { state ->
                when (state) {
                    EnterExitState.PreEnter -> Red
                    EnterExitState.PostExit -> Color.Green
                    EnterExitState.Visible -> Color.Blue
                }
            }

            color = background

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(background)
            ) {
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .animateEnterExit(
                            // Slide in/out the inner box.
                            enter = slideInVertically(
                                animationSpec = tween(
                                    durationMillis = 3000,
                                    easing = LinearOutSlowInEasing
                                )
                            ),
                            exit = slideOutVertically(
                                animationSpec = tween(
                                    durationMillis = 3000,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                        )
                        .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                        .background(Red)
                ) {
                    // Content of the notification…
                }
            }
        }
    }
}

/**********************************************************************************************************************************
trigger animation when any state changes using crossfade()
 **********************************************************************************************************************************/
@Composable
fun CrossFade() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var currentPage by remember { mutableStateOf(0) }
        Crossfade(
            targetState = currentPage,
            animationSpec = tween(durationMillis = 1000)
        ) { screen ->
            ColorBox(screen)
        }
        Button(onClick = {
            currentPage = (0..0xFFFFFF).random()
        }) {
            Text("Click Me")
        }
    }
}

@Composable
fun ColorBox(screen: Int) {
    Box(
        Modifier
            .size(100.dp)
            .background(Color(screen + 0xFF000000)),
        contentAlignment = Alignment.Center
    ) {
        Text(get6DigitHex(screen), color = contrastColor(screen))
    }
}

fun get6DigitHex(value: Int): String {
    return "0x" + "%x".format(value).padStart(6, '0').toUpperCase(Locale.current)
}

fun contrastColor(color: Int): Color {
    return if (ColorUtils.calculateLuminance(color) < 0.5)
        Color.White
    else
        Color.Black
}

/**********************************************************************************************************************************
trigger animation when any state changes using animateTo()
 **********************************************************************************************************************************/
@Composable
fun AnimatableOnly() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //var enabled by remember { mutableStateOf(true) }
        var currentPage by remember { mutableStateOf(0) }
        val color = remember { Animatable(Color.Gray) }
        LaunchedEffect(currentPage) {  //animateTo() is suspend function
            color.animateTo(
                //target value must be color because we use color.animateTo()
                targetValue = Color(currentPage + 0xFF000000),
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing
                )
            )
        }
        Box(
            Modifier
                .size(100.dp)
                .background(color.value)
        )
        Button(onClick = { currentPage = (0..0xFFFFFF).random() }) {
            Text("Click Me")
        }
    }
}

/**********************************************************************************************************************************
AnimatedContent - with targetState
 **********************************************************************************************************************************/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSimple() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        AnimatedContent(targetState = count) { targetCount ->
            Text(
                text = "Count: $targetCount",
                modifier = Modifier.padding(start = 8.dp),
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

/**********************************************************************************************************************************
AnimatedContent - with targetState, transitionSpec ex-1
 **********************************************************************************************************************************/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentWithTransitionSpec1() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var currentPage by remember { mutableStateOf(0) }

        Box {
            Crossfade(
                targetState = currentPage,
                animationSpec = animationSpec()
            ) { screen -> ColorBoxOnly(screen) }

            AnimatedContent(targetState = currentPage,
                transitionSpec = {
                    if (targetState > initialState) {
                        upColorTransition()
                    } else {
                        downColorTransition()
                    } //display animation outside the box
                        .using(SizeTransform(clip = false))
                }
            ) { screen ->
                ColorBoxTextOnly(screen)
            }
        }
        Button(onClick = {
            currentPage = (0..0xFFFFFF).random()
        }) {
            Text("Click Me")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun downColorTransition() =
    slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = animationSpec()
    ) + fadeIn(
        animationSpec = animationSpec()
    ) with slideOutVertically(
        targetOffsetY = { fullHeight -> fullHeight },
        animationSpec = animationSpec()
    ) + fadeOut(animationSpec = animationSpec())

@OptIn(ExperimentalAnimationApi::class)
private fun upColorTransition() =
    slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = animationSpec()
    ) + fadeIn(
        animationSpec = animationSpec()
    ) with slideOutVertically(
        targetOffsetY = { fullHeight -> -fullHeight },
        animationSpec = animationSpec()
    ) + fadeOut(animationSpec = animationSpec())

fun <T> animationSpec() = tween<T>(
    durationMillis = 3000,
    easing = LinearOutSlowInEasing
)

@Composable
fun ColorBoxOnly(screen: Int) {
    Box(
        Modifier
            .size(100.dp)
            .background(Color(screen + 0xFF000000))
    )
}

@Composable
fun ColorBoxTextOnly(screen: Int) {
    Box(
        Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(get6DigitHex(screen), color = contrastColor(screen))
    }
}

/**********************************************************************************************************************************
AnimatedContent - with targetState, transitionSpec ex-2
 **********************************************************************************************************************************/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentWithTransitionSpec2() {

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        var count by remember { mutableStateOf(0) }

        Button(onClick = { count++ }) {
            Text(text = "Add")
        }

        Button(onClick = { count-- }, modifier = Modifier.padding(start = 8.dp)) {
            Text(text = "Minus")
        }

        AnimatedContent(
            targetState = count,
            transitionSpec = {
                if (targetState > initialState) {
                    // If the target number is larger than old value
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    // If the target number is smaller than old value
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    //for adding effect on slide up-down animation
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            Text(
                text = "$targetCount",
                modifier = Modifier.padding(start = 8.dp),
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

/**********************************************************************************************************************************
AnimatedContent - with targetState, transitionSpec ex-3 between 2 content
 **********************************************************************************************************************************/
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AnimatedContentWithTransitionSpec3() {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colors.primary,
        onClick = { expanded = !expanded }
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                Expanded()
            } else {
                ContentIcon()
            }
        }
    }
}

@Composable
fun Expanded() {
    Text(
        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun ContentIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_baseline_call_24),
        contentDescription = "call icon",
        modifier = Modifier.padding(10.dp)
    )
}

/**********************************************************************************************************************************
animateContentSize
 **********************************************************************************************************************************/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSize() {
    Column {
        var expanded by remember {
            mutableStateOf(false)
        }

        Image(
            painter = painterResource(
                id = if (expanded)
                    R.drawable.download
                else
                    R.drawable.ic_launcher_background
            ),
            contentDescription = "",
            modifier = Modifier
                .background(Yellow)
                .animateContentSize(tween(1500))
        )

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Hide" else "Show")
        }
    }
}

/**********************************************************************************************************************************
Animate Content Size Transform
 **********************************************************************************************************************************/
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSizeTransform() {
    val time = 500
    Column {
        var expanded by remember {
            mutableStateOf(false)
        }

        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                if (targetState) {
                    expandFading(time) using expandSizing(time)
                } else {
                    shrinkFading(time) using shrinkSizing(time)
                }

            }
        ) { targetExpanded ->
            Image(
                painter = painterResource(
                    id = if (targetExpanded)
                        R.drawable.download
                    else
                        R.drawable.ic_launcher_background
                ),
                contentDescription = "",
                modifier = Modifier.background(Yellow)
            )
        }

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Hide" else "Show")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun shrinkSizing(time: Int) =
    SizeTransform { initialSize, targetSize ->
        keyframes {
            // Shrink to target height first
            IntSize(initialSize.width, targetSize.height) at time
            // Then shrink to target width
            durationMillis = time * 3
        }
    }

@OptIn(ExperimentalAnimationApi::class)
private fun shrinkFading(time: Int) =
    fadeIn(animationSpec = tween(time, time * 2)) with
            fadeOut(animationSpec = tween(time * 3))

@OptIn(ExperimentalAnimationApi::class)
private fun expandSizing(time: Int) =
    SizeTransform { initialSize, targetSize ->
        keyframes {
            // Expand to target width first
            IntSize(targetSize.width, initialSize.height) at time
            // Then expand to target height
            durationMillis = time * 3
        }
    }

@OptIn(ExperimentalAnimationApi::class)
private fun expandFading(time: Int) =
    fadeIn(animationSpec = tween(time * 3)) with
            fadeOut(animationSpec = tween(time))

/**********************************************************************************************************************************
animateFloatAsState
 **********************************************************************************************************************************/
@Composable
fun AnimateFloatAsState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var enabled by remember { mutableStateOf(true) }
        val alpha: Float by animateFloatAsState(
            if (enabled) 1f else 0.2f,
            animationSpec = tween(
                durationMillis = 3000,
                easing = LinearOutSlowInEasing
            )
        )
        Box(
            Modifier
                .size(100.dp)
                .graphicsLayer(alpha = alpha)
                .background(Red)
        )
        Button(onClick = { enabled = !enabled }) {
            Text("Click Me")
        }
    }
}

/**********************************************************************************************************************************
animateColorAsState
 **********************************************************************************************************************************/
@Composable
fun AnimateColorAsState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var currentPage by remember { mutableStateOf(0) }

        val color: Color by animateColorAsState(targetValue = Color(currentPage + 0xFF000000))
        Box(
            Modifier
                .size(100.dp)
                .background(color)
        )
        Button(onClick = { currentPage = (0..0xFFFFFF).random() }) {
            Text("Click Me")
        }
    }
}

/**********************************************************************************************************************************
animateDpAsState
 **********************************************************************************************************************************/
enum class BikePosition {
    Start, Finish
}

@Preview
@Composable
fun AnimateDpAsState() {
    var bikeState by remember { mutableStateOf(BikePosition.Start) }

    val offsetAnimation: Dp by animateDpAsState(
        if (bikeState == BikePosition.Start) 5.dp else 300.dp,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .height(90.dp)
                .absoluteOffset(x = offsetAnimation)
        )
        Button(
            onClick = {
                bikeState = when (bikeState) {
                    BikePosition.Start -> BikePosition.Finish
                    BikePosition.Finish -> BikePosition.Start
                }
            }, modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(text = "Ride")
        }
    }
}

/**********************************************************************************************************************************
AnimateSizeAsState
 **********************************************************************************************************************************/
@Composable
fun AnimateSizeAsState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isOn by remember { mutableStateOf(true) }
        val size1: Size by animateSizeAsState(
            targetValue = if (isOn) Size(100f, 100f) else Size(
                200f,
                200f
            )
        )
        Box(
            Modifier
                .background(MaterialTheme.colors.onSurface)
                .size(width = size1.width.dp, height = size1.height.dp)
        )
        Button(onClick = { isOn = !isOn }) {
            Text("Click Me")
        }
    }
}

/**********************************************************************************************************************************
updateTransition-1
 **********************************************************************************************************************************/
@Composable
fun UpdateTransitionBasic1() {

    //change in boxState will invoke the animation
    var boxState by remember { mutableStateOf(BoxState1.Small) }

    //create updateTransition object
    val transition = updateTransition(targetState = boxState, label = "Box transition")

    val color by transition.animateColor(
        label = "Color",
        transitionSpec = { tween(2000, easing = FastOutSlowInEasing) }
    ) {
        when (it) {
            BoxState1.Small -> Red
            BoxState1.Large -> Yellow
        }
    }

    val size by transition.animateDp(label = "size",
        transitionSpec = { tween(2000, easing = LinearOutSlowInEasing) }) {
        when (it) {
            BoxState1.Small -> 32.dp
            BoxState1.Large -> 128.dp
        }
    }

    Column {
        Button(onClick = {
            boxState = if (boxState == BoxState1.Small) BoxState1.Large else BoxState1.Small
        }) {
            Text(text = "Toggle")
        }

        Box(
            Modifier
                .background(color = color)
                .size(size)
        )
    }

}

private enum class BoxState1 {
    Small, Large
}

/**********************************************************************************************************************************
updateTransition-2
 **********************************************************************************************************************************/
enum class BoxState {
    Collapsed,
    Expanded
}

@Composable
fun UpdateTransitionBasic2() {
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(targetState = currentState, label = "")

    val rect by transition.animateRect(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
        }
    }

    val color by transition.animateColor(
        transitionSpec = transitioningSpec(),
        label = ""
    ) { state ->
        when (state) {
            BoxState.Collapsed -> MaterialTheme.colors.primary
            BoxState.Expanded -> MaterialTheme.colors.secondary
        }
    }

    val borderWidth by transition.animateDp(
        transitionSpec = transitioningSpec(),
        label = ""
    ) { state ->
        when (state) {
            BoxState.Collapsed -> 5.dp
            BoxState.Expanded -> 20.dp
        }
    }

    Column {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .border(BorderStroke(borderWidth, Color.Green))
        ) {
            drawPath(Path().apply { addRect(rect) }, color)
        }
        Button(onClick = {
            currentState = if (currentState == BoxState.Expanded)
                BoxState.Collapsed else BoxState.Expanded
        }) {
            Text("Click Me")
        }
    }
}

@Composable
fun <T> transitioningSpec(): @Composable (Transition.Segment<BoxState>.() -> FiniteAnimationSpec<T>) =
    {
        when {
            BoxState.Expanded isTransitioningTo BoxState.Collapsed ->
                spring(stiffness = 20f, dampingRatio = 0.25f)
            else ->
                tween(durationMillis = 3000)
        }
    }

/**********************************************************************************************************************************
UpdateTransitionChild
 **********************************************************************************************************************************/
@OptIn(ExperimentalTransitionApi::class)
@Composable
fun UpdateTransitionChild() {
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(currentState, label = "")

    val rect by transition.animateRect(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
        }
    }

    Column {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(BorderStroke(1.dp, Color.Green))
        ) {
            drawPath(Path().apply { addRect(rect) }, Red)
        }
        Child(transition.createChildTransition { currentState })
        Button(onClick = {
            currentState =
                if (currentState == BoxState.Expanded) BoxState.Collapsed
                else BoxState.Expanded
        }) {
            Text("Click Me")
        }
    }

//for launch as expanded by default for the first time
    LaunchedEffect(Unit) {
        currentState = BoxState.Expanded
    }
}

@Composable
fun Child(transition: Transition<BoxState>) {
    val rect by transition.animateRect(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
        }
    }

    Column {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(BorderStroke(1.dp, Color.Green))
        ) {
            drawPath(Path().apply { addRect(rect) }, Red)
        }
    }
}

/**********************************************************************************************************************************
multiple animation using updateTransition
 **********************************************************************************************************************************/
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun UpdateTransitionExtension() {
    var selected by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = selected, label = "")
    val borderColor by transition.animateColor(label = "") { isSelected ->
        if (isSelected) Color.Magenta else Color.White
    }
    val elevation by transition.animateDp(label = "") { isSelected ->
        if (isSelected) 10.dp else 2.dp
    }

    Surface(
        onClick = { selected = !selected },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor),
        elevation = elevation,
        modifier = Modifier.padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Hello, world!")
            // AnimatedVisibility as a part of the transition.
            transition.AnimatedVisibility(
                visible = { targetSelected -> targetSelected },
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(text = "It is fine today.")
            }
            // AnimatedContent as a part of the transition.
            transition.AnimatedContent { targetState ->
                if (targetState) {
                    Text(text = "Selected")
                } else {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone"
                    )
                }
            }
        }
    }
}

/**********************************************************************************************************************************
Multiple Animation using animateTo() backed by coroutines
 **********************************************************************************************************************************/
@Composable
fun MultipleAnimCoroutineAnimateTo() {
    //tasks here
    //1. rotation
    //2. change color
    val angle = remember { Animatable(initialValue = 0f) }
    var angleChanger by remember { mutableStateOf(360f) } //here we use 360f because we use angleChanger as targetValue
    val color = remember {
        Animatable(
            initialValue = Color.Green,
            typeConverter = Color.VectorConverter(ColorSpaces.LinearSrgb)
            //VectorConverter = transforms between a Color and a 4 part AnimationVector storing the red, green, blue and alpha components of the color
        )
    }
    val colorChanger =
        remember { mutableStateOf(Color.Blue) } //here we use Blue because we use colorChanger as targetValue

    LaunchedEffect(key1 = angleChanger, key2 = colorChanger) {
        //if you want animation one time then you can pass angle & color as key1,key2
        //but if you want animation multiple times by user actions then you have to use angleChanger & colorChanger

        //Note : here we use two separate launch{} because we want to execute it parallel
        //for serial execution, we can use single launch{} block
        launch {
            angle.animateTo(targetValue = angleChanger, animationSpec = tween(3000))
        }
        launch {
            color.animateTo(targetValue = colorChanger.value, animationSpec = tween(3000))
        }
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .wrapContentHeight(align = Alignment.CenterVertically)
        .size(200.dp)
        .clickable {
            angleChanger = if (angleChanger == 0f) 360f else 0f
            colorChanger.value = if (colorChanger.value == Color.Green) Color.Blue else Color.Green
        }, onDraw = {
        rotate(degrees = angle.value) {
            drawRoundRect(
                color = color.value,
                cornerRadius = CornerRadius(16.dp.toPx())
            )
        }
    })
}

/**********************************************************************************************************************************
InfiniteAnimation
 **********************************************************************************************************************************/
@Composable
fun InfiniteAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
    )
}

/**********************************************************************************************************************************
TargetBasedAnimation
 **********************************************************************************************************************************/
@Composable
fun TargetBasedAnimationFun() {
    var state by remember {
        mutableStateOf(0)
    }
    val anim = remember {
        TargetBasedAnimation(
            animationSpec = tween(2000),
            typeConverter = Float.VectorConverter,
            initialValue = 100f,
            targetValue = 300f
        )
    }
    var playTime by remember { mutableStateOf(0L) }
    var animationValue by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(state) {
        val startTime = withFrameNanos { it }
        do {
            playTime = withFrameNanos { it } - startTime
            animationValue = anim.getValueFromNanos(playTime).toInt()
        } while (!anim.isFinishedFromNanos(playTime))

    }
    Box(modifier = Modifier.fillMaxSize(1f), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(animationValue.dp)
                .background(Red, shape = RoundedCornerShape(animationValue / 5))
                .clickable {
                    state++
                }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = animationValue.toString(),
                style = TextStyle(color = Color.White, fontSize = (animationValue / 5).sp)
            )
        }
    }
}

/**********************************************************************************************************************************
spring
 **********************************************************************************************************************************/
@Preview
@Composable
fun SpringFun() {
    var bikeState by rememberSaveable { mutableStateOf(BikePosition.Start) }

    //100.dp = 90.dp is width of the image & 10.dp is for padding
    val targetValue = if (bikeState == BikePosition.Start) 5.dp else getScreenWidth().dp - 100.dp

    val offsetAnimationStiffnessLow: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val offsetAnimationStiffnessVeryLow: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
    )
    val offsetAnimationStiffnessMediumLow: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )
    val offsetAnimationStiffnessMedium: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
    val offsetAnimationStiffnessHigh: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(stiffness = Spring.StiffnessHigh)
    )
    val offsetAnimationDampingRatioNoBouncy: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy)
    )
    val offsetAnimationDampingRatioLowBouncy: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )
    val offsetAnimationDampingRatioMediumBouncy: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    val offsetAnimationDampingRatioHighBouncy: Dp by animateDpAsState(
        targetValue,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "StiffnessLow", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationStiffnessLow)
            Text(text = "StiffnessVeryLow", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationStiffnessVeryLow)
            Text(text = "StiffnessMediumLow", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationStiffnessMediumLow)
            Text(text = "StiffnessMedium", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationStiffnessMedium)
            Text(text = "StiffnessHigh", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationStiffnessHigh)
            Text(text = "DampingRatioNoBouncy", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationDampingRatioNoBouncy)
            Text(text = "DampingRatioLowBouncy", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationDampingRatioLowBouncy)
            Text(text = "DampingRatioMediumBouncy", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationDampingRatioMediumBouncy)
            Text(text = "DampingRatioHighBouncy", color = MaterialTheme.colors.onSurface)
            DrawImage(offsetAnimationDampingRatioHighBouncy)
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

        }

        ExtendedFloatingActionButton(
            onClick = {
                bikeState = when (bikeState) {
                    BikePosition.Start -> BikePosition.Finish
                    BikePosition.Finish -> BikePosition.Start
                }
            }, modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp), text = { Text(text = "Ride") }
        )
    }
}

@Composable
fun DrawImage(stiffness: Dp) {
    Image(
        painter = painterResource(R.drawable.cycle),
        contentDescription = null,
        modifier = Modifier
            .width(90.dp)
            .height(90.dp)
            .absoluteOffset(x = stiffness)
    )
}

@Composable
fun getScreenWidth(): Float {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}

/**********************************************************************************************************************************
tween
 **********************************************************************************************************************************/
@Preview
@Composable
fun TweenFun() {
    var bikeState by rememberSaveable { mutableStateOf(BikePosition.Start) }

    //100.dp = 90.dp is width of the image & 10.dp is for padding
    val targetValue = if (bikeState == BikePosition.Start) 5.dp else getScreenWidth().dp - 100.dp

    val easingLinearEasing: Dp by animateDpAsState(
        targetValue,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )
    val easingLinearOutSlowInEasing: Dp by animateDpAsState(
        targetValue,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
    )
    val easingFastOutLinearInEasing: Dp by animateDpAsState(
        targetValue,
        animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing)
    )
    val easingFastOutSlowInEasing: Dp by animateDpAsState(
        targetValue,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )
    val withDelay: Dp by animateDpAsState(
        targetValue,
        animationSpec = tween(durationMillis = 1000, delayMillis = 1000)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "LinearEasing", color = MaterialTheme.colors.onSurface)
            DrawImage(easingLinearEasing)
            Text(text = "LinearOutSlowInEasing", color = MaterialTheme.colors.onSurface)
            DrawImage(easingLinearOutSlowInEasing)
            Text(text = "FastOutLinearInEasing", color = MaterialTheme.colors.onSurface)
            DrawImage(easingFastOutLinearInEasing)
            Text(text = "FastOutLinearInEasing", color = MaterialTheme.colors.onSurface)
            DrawImage(easingFastOutSlowInEasing)
            Text(text = "withDelay", color = MaterialTheme.colors.onSurface)
            DrawImage(withDelay)
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

        }

        ExtendedFloatingActionButton(
            onClick = {
                bikeState = when (bikeState) {
                    BikePosition.Start -> BikePosition.Finish
                    BikePosition.Finish -> BikePosition.Start
                }
            }, modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp), text = { Text(text = "Ride") }
        )
    }
}

/**********************************************************************************************************************************
keyframes
 **********************************************************************************************************************************/
@Preview
@Composable
fun KeyFramesFun() {
    var bikeState by rememberSaveable { mutableStateOf(BikePosition.Start) }

    //100.dp = 90.dp is width of the image & 10.dp is for padding
    val targetValue = if (bikeState == BikePosition.Start) 5.dp else getScreenWidth().dp - 100.dp

    val keyframesAnimation: Dp by animateDpAsState(
        targetValue,
        animationSpec = keyframes {
            durationMillis = 1000
            50.dp at 400 with LinearOutSlowInEasing // for 0-400 ms
            70.dp at 800 with FastOutLinearInEasing // for 400-800 ms
        }
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "keyframes", color = MaterialTheme.colors.onSurface)
            DrawImage(keyframesAnimation)

            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
        }

        ExtendedFloatingActionButton(
            onClick = {
                bikeState = when (bikeState) {
                    BikePosition.Start -> BikePosition.Finish
                    BikePosition.Finish -> BikePosition.Start
                }
            }, modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp), text = { Text(text = "Ride") }
        )
    }
}

/**********************************************************************************************************************************
repeatable
 **********************************************************************************************************************************/
@Preview
@Composable
fun RepeatableFun() {
    var bikeState by rememberSaveable { mutableStateOf(BikePosition.Start) }

    //100.dp = 90.dp is width of the image & 10.dp is for padding
    val targetValue = if (bikeState == BikePosition.Start) 5.dp else getScreenWidth().dp - 100.dp

    val repeatableRestartAnimation: Dp by animateDpAsState(
        targetValue,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(),
            repeatMode = RepeatMode.Restart
        )
        //will restart the animation from the start value to the end value.
    )
    val repeatableReverseAnimation: Dp by animateDpAsState(
        targetValue,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(),
            repeatMode = RepeatMode.Reverse
        )
        //will reverse the last iteration as the animation repeats.
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "repeatable Restart", color = MaterialTheme.colors.onSurface)
            DrawImage(repeatableRestartAnimation)

            Spacer(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Text(text = "repeatable Reverse", color = MaterialTheme.colors.onSurface)
            DrawImage(repeatableReverseAnimation)
        }

        ExtendedFloatingActionButton(
            onClick = {
                bikeState = when (bikeState) {
                    BikePosition.Start -> BikePosition.Finish
                    BikePosition.Finish -> BikePosition.Start
                }
            }, modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp), text = { Text(text = "Ride") }
        )
    }
}

/**********************************************************************************************************************************
infiniteRepeatable
 **********************************************************************************************************************************/
@Preview
@Composable
fun InfiniteRepeatableFun() {
    var bikeState by rememberSaveable { mutableStateOf(BikePosition.Start) }

    //100.dp = 90.dp is width of the image & 10.dp is for padding
    val targetValue = if (bikeState == BikePosition.Start) 5.dp else getScreenWidth().dp - 100.dp

    val infiniteRepeatableRestartAnimation: Dp by animateDpAsState(
        targetValue,
        animationSpec = infiniteRepeatable(animation = tween(), repeatMode = RepeatMode.Restart)
        //will restart the animation from the start value to the end value.
    )
    val infiniteRepeatableReverseAnimation: Dp by animateDpAsState(
        targetValue,
        animationSpec = infiniteRepeatable(animation = tween(), repeatMode = RepeatMode.Reverse)
        //will reverse the last iteration as the animation repeats.
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "infinite repeatable Restart", color = MaterialTheme.colors.onSurface)
            DrawImage(infiniteRepeatableRestartAnimation)

            Spacer(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Text(text = "infinite repeatable Reverse", color = MaterialTheme.colors.onSurface)
            DrawImage(infiniteRepeatableReverseAnimation)
        }

        ExtendedFloatingActionButton(
            onClick = {
                bikeState = when (bikeState) {
                    BikePosition.Start -> BikePosition.Finish
                    BikePosition.Finish -> BikePosition.Start
                }
            }, modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp), text = { Text(text = "Ride") }
        )
    }
}

/**********************************************************************************************************************************
snap
 **********************************************************************************************************************************/
@Preview
@Composable
fun SnapFun() {
    var bikeState by rememberSaveable { mutableStateOf(BikePosition.Start) }

    //100.dp = 90.dp is width of the image & 10.dp is for padding
    val targetValue = if (bikeState == BikePosition.Start) 5.dp else getScreenWidth().dp - 100.dp

    val snapAnimation: Dp by animateDpAsState(
        targetValue,
        animationSpec = snap(delayMillis = 500)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "snap animation", color = MaterialTheme.colors.onSurface)
            DrawImage(snapAnimation)

            Spacer(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
        }

        ExtendedFloatingActionButton(
            onClick = {
                bikeState = when (bikeState) {
                    BikePosition.Start -> BikePosition.Finish
                    BikePosition.Finish -> BikePosition.Start
                }
            }, modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp), text = { Text(text = "Ride") }
        )
    }
}

/**********************************************************************************************************************************
AnimationVector - TypeConverter
 **********************************************************************************************************************************/
//animate circle on touch
@Composable
fun AnimationVectorFun() {
    //here we want to animate circle to touch position
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        val position = awaitPointerEventScope {
                            awaitFirstDown().position //consume tap down event & its position
                        }
                        launch {
                            // Animate to the tap position.
                            offset.animateTo(
                                position,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                        }
                    }
                }
            }
    ) {
        Circle(modifier = Modifier.offset { offset.value.toIntOffset() })
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    color: Color = Red
) {
    Box(
        modifier = modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(color)
    )
}