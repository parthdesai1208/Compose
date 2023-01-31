@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.parthdesai1208.compose.view.uicomponents


import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay

@Composable
fun Tooltip(
    expanded: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    timeoutMillis: Long = TooltipTimeout,
    backgroundColor: Color = Color.Black,
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

        Popup(
            onDismissRequest = { expanded.value = false },
            properties = properties,
            alignment = Alignment.TopCenter
        ) {
            Box(
                // Add space for elevation shadow
                modifier = Modifier.padding(TooltipElevation),
            ) {
                TooltipContent(expandedStates, backgroundColor, modifier, content)
            }
        }
    }
}

/**
 * Simple text version of [Tooltip]
 */
/*@Composable
fun Tooltip(
    expanded: MutableState<Boolean>,
    text: String,
    modifier: Modifier = Modifier,
    timeoutMillis: Long = TooltipTimeout,
    backgroundColor: Color = Color.Black,
    offset: DpOffset = TooltipOffset,
    properties: PopupProperties = TooltipPopupProperties,
) {
    Tooltip(expanded, modifier, timeoutMillis, backgroundColor, offset, properties) {
        Text(text)
    }
}*/


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

    Card(
        backgroundColor = backgroundColor.copy(alpha = 0.75f),
        contentColor = MaterialTheme.colors.contentColorFor(backgroundColor)
            .takeOrElse { backgroundColor.onColor() },
        modifier = Modifier.alpha(alpha),
        elevation = TooltipElevation,
    ) {
        val p = TooltipPadding
        Column(
            modifier = modifier
                .padding(start = p, top = p * 0.5f, end = p, bottom = p * 0.7f)
                .verticalScroll(rememberScrollState()),
            content = content,
        )
    }
}

private val TooltipElevation = 32.dp
private val TooltipPadding = 16.dp

private val TooltipPopupProperties = PopupProperties(focusable = true)
private val TooltipOffset = DpOffset(0.dp, 0.dp)

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
fun TooltipOnLongClickExample(onClick: () -> Unit = {}) {
    // Commonly a Tooltip can be placed in a Box with a sibling
    // that will be used as the 'anchor' for positioning.
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {
            val showTooltip = remember { mutableStateOf(false) }

            // Buttons and Surfaces don't support onLongClick out of the box,
            // so use a simple Box with combinedClickable
            Box(
                modifier = Modifier.combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClickLabel = "Button action description",
                    role = Role.Button,
                    onClick = onClick,
                    onLongClick = { showTooltip.value = true },
                ),
            ) {
                Text("Click Me (will show tooltip on long click)")
            }

            Tooltip(
                expanded = showTooltip
            ) {
                // Tooltip content goes here.
                Text("Tooltip Text!!", textAlign = TextAlign.Center)
            }
        }
    }
}