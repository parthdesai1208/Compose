package com.parthdesai1208.compose.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Constant.BLANK_SPACE

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun GestureScreen(navHostController: NavHostController) {
    val cardModifier = Modifier.padding(all = 16.dp)
    val cardShape = RoundedCornerShape(size = 8.dp)
    val cardElevation = 8.dp
    val iconModifier = Modifier.size(24.dp)
    val rowModifier = Modifier.padding(all = 8.dp)
    val rowHorizontalArrangement =
        Arrangement.spacedBy(space = 10.dp, alignment = Alignment.CenterHorizontally)
    val context = LocalContext.current

    BuildTopBarWithScreen(
        title = stringResource(id = R.string.gesture),
        screen = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //region tap & on press
                Card(modifier = cardModifier.pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.tap_detected), Toast.LENGTH_SHORT
                        ).show()
                    }, onPress = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.on_press_detected), Toast.LENGTH_SHORT
                        ).show()
                    })
                }, shape = cardShape, elevation = cardElevation) {
                    Row(
                        horizontalArrangement = rowHorizontalArrangement, modifier = rowModifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.tap_gesture),
                            contentDescription = stringResource(id = R.string.tap_gesture_acc),
                            modifier = iconModifier,
                            tint = Color.Unspecified
                        )
                        Text(text = stringResource(R.string.tap_gesture))
                    }
                }
                //endregion
                //region double tap detected
                Card(modifier = cardModifier.pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.double_tap_detected), Toast.LENGTH_SHORT
                        ).show()
                    })
                }, shape = cardShape, elevation = cardElevation) {
                    Row(
                        horizontalArrangement = rowHorizontalArrangement, modifier = rowModifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.double_tap_gesture),
                            contentDescription = stringResource(id = R.string.double_tap_gesture_acc),
                            modifier = iconModifier,
                            tint = Color.Unspecified
                        )
                        Text(text = stringResource(R.string.double_tap_gesture))
                    }
                }
                //endregion
                //region long press
                Card(modifier = cardModifier.pointerInput(Unit) {
                    detectTapGestures(onLongPress = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.long_press_detected), Toast.LENGTH_SHORT
                        ).show()
                    })
                }, shape = cardShape, elevation = cardElevation) {
                    Row(
                        horizontalArrangement = rowHorizontalArrangement, modifier = rowModifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.long_press_gesture),
                            contentDescription = stringResource(id = R.string.longpressgestureacc),
                            modifier = iconModifier
                        )
                        Text(text = stringResource(R.string.longpressgesture))
                    }
                }
                //endregion
                //region swipe to right
                var isVisibleBackground by remember { mutableStateOf(true) }
                val dismissState = rememberDismissState(confirmStateChange = {
                    when (it) {
                        DismissValue.Default -> {}

                        DismissValue.DismissedToStart -> {}

                        DismissValue.DismissedToEnd -> {
                            isVisibleBackground = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.dismiss_to_end_detected),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    true
                })
                SwipeToDismiss(state = dismissState, //listen to state changes
                    dismissThresholds = { FractionalThreshold(0.5f) },//row must be over 50% of the screen before it is dismissed.
                    directions = setOf(DismissDirection.StartToEnd), //swipe direction
                    //upper layout
                    dismissContent = {
                        Card(
                            modifier = cardModifier,
                            shape = cardShape,
                            elevation = cardElevation
                        ) {
                            Row(
                                horizontalArrangement = rowHorizontalArrangement,
                                modifier = rowModifier
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.swipe_left_to_right_gesture),
                                    contentDescription = stringResource(id = R.string.swipelefttorightgestureacc),
                                    modifier = iconModifier,
                                    tint = Color.Unspecified
                                )
                                Text(text = stringResource(R.string.swipelefttorightgesture))
                            }
                        }
                    },
                    //lower layout
                    background = {
                        SwipeToRightBackground(
                            dismissState = dismissState,
                            isVisibleBackground = isVisibleBackground,
                            rowHorizontalArrangement = rowHorizontalArrangement,
                            modifier = rowModifier,
                            iconModifier = iconModifier
                        )
                    })
                //endregion
                //region swipe to left
                var isVisibleBackground1 by remember { mutableStateOf(true) }
                val dismissState1 = rememberDismissState(confirmStateChange = {
                    when (it) {
                        DismissValue.Default -> {}

                        DismissValue.DismissedToStart -> {
                            isVisibleBackground1 = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.dismiss_to_start_detected),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        DismissValue.DismissedToEnd -> {}
                    }
                    true
                })
                SwipeToDismiss(state = dismissState1, //listen to state changes
                    dismissThresholds = { FractionalThreshold(0.5f) },//row must be over 50% of the screen before it is dismissed.
                    directions = setOf(DismissDirection.EndToStart), //swipe direction
                    //upper layout
                    dismissContent = {
                        Card(
                            modifier = cardModifier,
                            shape = cardShape,
                            elevation = cardElevation
                        ) {
                            Row(
                                horizontalArrangement = rowHorizontalArrangement,
                                modifier = rowModifier
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.swipe_right_to_left_gesture),
                                    contentDescription = stringResource(id = R.string.swiperighttoleftgestureacc),
                                    modifier = iconModifier
                                )
                                Text(text = stringResource(R.string.swiperighttoleftgesture))
                            }
                        }
                    },
                    //lower layout
                    background = {
                        SwipeToLeftBackground(
                            dismissState = dismissState1,
                            isVisibleBackground1 = isVisibleBackground1,
                            rowHorizontalArrangement = rowHorizontalArrangement,
                            modifier = rowModifier,
                            iconModifier = iconModifier
                        )
                    })
                //endregion
                //region swipe to right & left
                var isVisibleBackground2 by remember { mutableStateOf(true) }
                val dismissState2 = rememberDismissState(confirmStateChange = {
                    when (it) {
                        DismissValue.Default -> {}

                        DismissValue.DismissedToStart -> {
                            isVisibleBackground2 = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.dismiss_to_start_detected),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        DismissValue.DismissedToEnd -> {
                            isVisibleBackground2 = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.dismiss_to_end_detected),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    true
                })
                SwipeToDismiss(state = dismissState2, //listen to state changes
                    dismissThresholds = { FractionalThreshold(0.5f) },//row must be over 50% of the screen before it is dismissed.
                    directions = setOf(
                        DismissDirection.StartToEnd, DismissDirection.EndToStart
                    ), //swipe direction
                    //upper layout
                    dismissContent = {
                        Card(
                            modifier = cardModifier,
                            shape = cardShape,
                            elevation = cardElevation
                        ) {
                            Row(
                                horizontalArrangement = rowHorizontalArrangement,
                                modifier = rowModifier
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.swipe_right_to_left_gesture),
                                    contentDescription = stringResource(id = R.string.swipetorightleftgestureacc),
                                    modifier = iconModifier
                                )
                                Text(text = stringResource(R.string.swipetorightleftgesture))
                            }
                        }
                    },
                    //lower layout
                    background = {
                        SwipeToRightLeftBackground(
                            dismissState = dismissState2,
                            isVisibleBackground2 = isVisibleBackground2,
                            rowHorizontalArrangement = rowHorizontalArrangement,
                            modifier = rowModifier,
                            iconModifier = iconModifier
                        )
                    })
                //endregion
                //region swipe to right & left with different view
                var isVisibleBackground3 by remember { mutableStateOf(true) }
                val dismissState3 = rememberDismissState(confirmStateChange = {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            isVisibleBackground3 = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.dismiss_to_start_detected),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        DismissValue.DismissedToEnd -> {
                            isVisibleBackground3 = false
                            Toast.makeText(
                                context,
                                context.getString(R.string.dismiss_to_end_detected),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        DismissValue.Default -> {}
                    }
                    true
                })
                SwipeToDismiss(state = dismissState3, //listen to state changes
                    dismissThresholds = { FractionalThreshold(0.5f) },//row must be over 50% of the screen before it is dismissed.
                    directions = setOf(
                        DismissDirection.StartToEnd, DismissDirection.EndToStart
                    ), //swipe direction
                    //upper layout
                    dismissContent = {
                        Card(
                            modifier = cardModifier,
                            shape = cardShape,
                            elevation = cardElevation
                        ) {
                            Row(
                                horizontalArrangement = rowHorizontalArrangement,
                                modifier = rowModifier
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.swipe_right_to_left_gesture),
                                    contentDescription = stringResource(id = R.string.swipetorightleftgesturewithdifferentcomposeacc),
                                    modifier = iconModifier
                                )
                                Text(text = stringResource(R.string.swipetorightleftgesturewithdifferentcompose))
                            }
                        }
                    },
                    //lower layout
                    background = {

                        SwipeRightLeftWithDifferentCompose(
                            dismissState3,
                            isVisibleBackground3,
                            cardModifier,
                            cardShape,
                            iconModifier
                        )
                    })
                //endregion
            }
        },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun RowScope.SwipeRightLeftWithDifferentCompose(
    dismissState3: DismissState,
    isVisibleBackground3: Boolean,
    modifier: Modifier,
    cardShape: RoundedCornerShape,
    iconModifier: Modifier
) {
    val direction = dismissState3.dismissDirection
        ?: return //no rendering when there’s no Swipe To Dismiss happening

    val backgroundColor by animateColorAsState(
        targetValue = when (dismissState3.targetValue) {
            DismissValue.Default -> Color.Green
            DismissValue.DismissedToEnd -> Color.Red
            DismissValue.DismissedToStart -> Color.Blue
        }
    )
    val bgIconScale by animateFloatAsState(targetValue = if (dismissState3.targetValue == DismissValue.Default) 0.75f else 1f)
    AnimatedVisibility(
        visible = isVisibleBackground3, enter = fadeIn(), exit = fadeOut()
    ) {
        Card(
            modifier = modifier.fillMaxSize(),
            shape = cardShape,
            backgroundColor = backgroundColor
        ) {
            Row(
                horizontalArrangement = when (direction) {
                    DismissDirection.StartToEnd -> Arrangement.Start
                    DismissDirection.EndToStart -> Arrangement.End
                    else -> Arrangement.Center
                },
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                when (direction) {
                    DismissDirection.StartToEnd -> {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.swipetodelete),
                            modifier = iconModifier.scale(scale = bgIconScale)
                        )
                        Text(text = stringResource(R.string.swipetodelete))
                    }

                    DismissDirection.EndToStart -> {
                        Text(
                            text = stringResource(R.string.swipetodelete).split(BLANK_SPACE)
                                .reversed().joinToString(BLANK_SPACE)
                        )
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.swipetodelete),
                            modifier = iconModifier.scale(scale = bgIconScale)
                        )
                    }

                    else -> {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.swipetodelete),
                            modifier = iconModifier.scale(scale = bgIconScale)
                        )
                        Text(text = stringResource(R.string.swipetodelete))
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RowScope.SwipeToRightLeftBackground(
    dismissState: DismissState,
    isVisibleBackground2: Boolean,
    rowHorizontalArrangement: Arrangement.Horizontal,
    modifier: Modifier,
    iconModifier: Modifier
) {
    dismissState.dismissDirection
        ?: return  //no rendering when there’s no Swipe To Dismiss happening

    AnimatedVisibility(
        visible = isVisibleBackground2, enter = fadeIn(), exit = fadeOut()
    ) {
        Row(
            horizontalArrangement = rowHorizontalArrangement,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.swipetodelete),
                modifier = iconModifier
            )
            Text(text = stringResource(R.string.swipetodelete))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RowScope.SwipeToLeftBackground(
    dismissState: DismissState,
    isVisibleBackground1: Boolean,
    rowHorizontalArrangement: Arrangement.Horizontal,
    modifier: Modifier,
    iconModifier: Modifier
) {
    dismissState.dismissDirection
        ?: return  //no rendering when there’s no Swipe To Dismiss happening

    AnimatedVisibility(
        visible = isVisibleBackground1, enter = fadeIn(), exit = fadeOut()
    ) {
        Row(
            horizontalArrangement = rowHorizontalArrangement,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.End)
        ) {
            Text(
                text = stringResource(R.string.swipetodelete).split(BLANK_SPACE).reversed()
                    .joinToString(BLANK_SPACE)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.swipetodelete),
                modifier = iconModifier
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToRightBackground(
    dismissState: DismissState,
    isVisibleBackground: Boolean,
    rowHorizontalArrangement: Arrangement.Horizontal,
    modifier: Modifier,
    iconModifier: Modifier
) {
    dismissState.dismissDirection
        ?: return   //no rendering when there’s no Swipe To Dismiss happening

    AnimatedVisibility(
        visible = isVisibleBackground, enter = fadeIn(), exit = fadeOut()
    ) {
        Row(
            horizontalArrangement = rowHorizontalArrangement,
            modifier = modifier.padding(all = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.swipetodelete),
                modifier = iconModifier
            )
            Text(text = stringResource(R.string.swipetodelete))
        }
    }
}