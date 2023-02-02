@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.parthdesai1208.compose.view.uicomponents


import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.parthdesai1208.compose.utils.ConditionalModifier
import com.parthdesai1208.compose.view.animation.physicsbasedanimation.GravitySensor
import com.parthdesai1208.compose.view.animation.physicsbasedanimation.PhysicsLayout
import com.parthdesai1208.compose.view.animation.physicsbasedanimation.PhysicsLayoutScope
import com.parthdesai1208.compose.view.animation.physicsbasedanimation.rememberSimulation
import kotlinx.coroutines.delay

@Composable
fun Tooltip(
    expanded: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    timeoutMillis: Long = TooltipTimeout,
    backgroundColor: Color = MaterialTheme.colors.onSurface,
    properties: PopupProperties = TooltipPopupProperties,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = expanded.value

    if (expandedStates.currentState || expandedStates.targetState) {
        if (expandedStates.isIdle) {
            LaunchedEffect(timeoutMillis, expanded) {
                delay(timeoutMillis)
                //expanded.value = false
            }
        }
        Box {
            Popup(
                onDismissRequest = { expanded.value = false },
                properties = properties,
                alignment = Alignment.TopCenter,
            ) {
                Box(
                    // Add space for elevation shadow
                    modifier = Modifier.padding(top = 40.dp),
                ) {
                    TooltipContent(expandedStates, backgroundColor, modifier, content)
                }
            }
        }
    }
}

/** @see androidx.compose.material.DropdownMenuContent */
@Composable
private fun TooltipContent(
    expandedStates: MutableTransitionState<Boolean>,
    backgroundColor: Color,
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    // Tooltip open/close animation.
    val transition = updateTransition(expandedStates, "Tooltip")

    val alpha by transition.animateFloat(label = "alpha", transitionSpec = {
        if (false isTransitioningTo true) {
            // Dismissed to expanded
            tween(durationMillis = InTransitionDuration)
        } else {
            // Expanded to dismissed.
            tween(durationMillis = OutTransitionDuration)
        }
    }) { if (it) 1f else 0f }

    Column {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            tint = backgroundColor.copy(alpha = 0.75f),
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
        Card(
            backgroundColor = backgroundColor.copy(alpha = 0.75f),
            contentColor = MaterialTheme.colors.contentColorFor(backgroundColor)
                .takeOrElse { backgroundColor.onColor() },
            modifier = Modifier.alpha(alpha),
            elevation = 16.dp,
        ) {
            Column(
                modifier = modifier
                    .padding(all = 16.dp)
                    //.padding(start = p, top = p * 0.5f, end = p, bottom = p * 0.7f)
                    .verticalScroll(rememberScrollState()),
                content = content,
            )
        }
    }
}

private val TooltipPopupProperties = PopupProperties(focusable = true)

// Tooltip open/close animation duration.
private const val InTransitionDuration = 64
private const val OutTransitionDuration = 240

// Default timeout before tooltip close
private const val TooltipTimeout = 2_000L - OutTransitionDuration


// Color helpers

/**
 * Calculates an 'on' color for this color.
 *
 * @return [Color.Black] or [Color.White], depending on [isLightColor].
 */
fun Color.onColor(): Color {
    return if (isLightColor()) Color.Black else Color.White
}

/**
 * Calculates if this color is considered light.
 *
 * @return true or false, depending on the higher contrast between [Color.Black] and [Color.White].
 */
fun Color.isLightColor(): Boolean {
    val contrastForBlack = calculateContrastFor(foreground = Color.Black)
    val contrastForWhite = calculateContrastFor(foreground = Color.White)
    return contrastForBlack > contrastForWhite
}

fun Color.calculateContrastFor(foreground: Color): Double {
    return androidx.core.graphics.ColorUtils.calculateContrast(foreground.toArgb(), toArgb())
}


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun TooltipOnLongClickExample() {
    // Commonly a Tooltip can be placed in a Box with a sibling
    // that will be used as the 'anchor' for positioning.
    val showTooltip = rememberSaveable { mutableStateOf(false) }
    var boxWidth by rememberSaveable { mutableStateOf(0f) }
    var boxHeight by rememberSaveable { mutableStateOf(0f) }
    val density = LocalDensity.current

    val animateWidth by rememberInfiniteTransition().animateValue(
        initialValue = boxWidth,
        targetValue = boxWidth + 8f,
        typeConverter = Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val animateHeight by rememberInfiniteTransition().animateValue(
        initialValue = boxHeight,
        targetValue = boxHeight + 8f,
        typeConverter = Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val simulation = rememberSimulation()
    GravitySensor {
        simulation.setGravity(it.copy(x = -it.x).times(3f))
    }

    PhysicsLayout(
        modifier = Modifier.background(
            color = if (showTooltip.value) MaterialTheme.colors.onSurface.copy(
                alpha = 0.20f
            ) else MaterialTheme.colors.surface
        ), simulation = simulation
    ) {
        ColumnWithPhysicsLayoutScope(
            showTooltip,
            boxWidth,
            density,
            boxHeight,
            animateWidth,
            animateHeight
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PhysicsLayoutScope.ColumnWithPhysicsLayoutScope(
    showTooltip: MutableState<Boolean>,
    boxWidth: Float,
    density: Density,
    boxHeight: Float,
    animateWidth: Float,
    animateHeight: Float
) {
    var boxWidth1 = boxWidth
    var boxHeight1 = boxHeight
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .body(isStatic = true)
    ) {

        // Buttons and Surfaces don't support onLongClick out of the box,
        // so use a simple Box with combinedClickable
        Box(
            modifier = Modifier
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClickLabel = "Button action description",
                    role = Role.Button,
                    onClick = { },
                    onLongClick = { showTooltip.value = true },
                )
                .ConditionalModifier(showTooltip.value) {
                    border(
                        width = 2.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colors.onSurface
                    )
                }
                .background(
                    color = MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp)
                )
                .ConditionalModifier(!showTooltip.value) {
                    onGloballyPositioned {
                        boxWidth1 = it.size.width.toFloat() / density.density
                        boxHeight1 = it.size.height.toFloat() / density.density
                    }
                }
                .ConditionalModifier(showTooltip.value) {
                    size(
                        width = animateWidth.dp,
                        height = animateHeight.dp
                    )
                }
        ) {
            Text(
                "Click Me (will show tooltip on long click)",
                modifier = Modifier.padding(all = 10.dp),
                color = MaterialTheme.colors.onSurface
            )
        }

        Tooltip(
            expanded = showTooltip
        ) {
            // Tooltip content goes here.
            Text("Tooltip Text!!", textAlign = TextAlign.Center)
        }
    }
}