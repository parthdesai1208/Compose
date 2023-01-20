package com.parthdesai1208.compose.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun GestureScreen() {
    val cardModifier = Modifier.padding(all = 16.dp)
    val cardShape = RoundedCornerShape(size = 8.dp)
    val cardElevation = 8.dp
    val iconModifier = Modifier.size(24.dp)
    val rowModifier = Modifier.padding(all = 8.dp)
    val rowHorizontalArrangement =
        Arrangement.spacedBy(space = 10.dp, alignment = Alignment.CenterHorizontally)
    val context = LocalContext.current

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //region tap & on press
            Card(modifier = cardModifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    Toast.makeText(context, "tap detected", Toast.LENGTH_SHORT).show()
                }, onPress = {
                    Toast.makeText(context, "on press detected", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(context, "double tap detected", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(context, "Long press detected", Toast.LENGTH_SHORT).show()
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
                    DismissValue.Default -> {
//                        Toast.makeText(context, "default state", Toast.LENGTH_SHORT).show()
                    }
                    DismissValue.DismissedToStart -> {
                        /*Toast.makeText(
                            context, "user dragging to default position", Toast.LENGTH_SHORT
                        ).show()*/
                    }
                    DismissValue.DismissedToEnd -> {
                        isVisibleBackground = false
                        Toast.makeText(context, "dismiss to end detected", Toast.LENGTH_SHORT)
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
                    Card(modifier = cardModifier, shape = cardShape, elevation = cardElevation) {
                        Row(
                            horizontalArrangement = rowHorizontalArrangement, modifier = rowModifier
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
                    AnimatedVisibility(
                        visible = isVisibleBackground, enter = fadeIn(), exit = fadeOut()
                    ) {
                        Row(
                            horizontalArrangement = rowHorizontalArrangement,
                            modifier = rowModifier.padding(all = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.swipetodelete),
                                modifier = iconModifier
                            )
                            Text(text = stringResource(R.string.swipetodelete))
                        }
                    }
                })
            //endregion
            //region swipe to left
            var isVisibleBackground1 by remember { mutableStateOf(true) }
            val dismissState1 = rememberDismissState(confirmStateChange = {
                when (it) {
                    DismissValue.Default -> {
//                        Toast.makeText(context, "default state", Toast.LENGTH_SHORT).show()
                    }
                    DismissValue.DismissedToStart -> {
                        isVisibleBackground1 = false
                        Toast.makeText(
                            context, "dismiss to start detected", Toast.LENGTH_SHORT
                        ).show()
                    }
                    DismissValue.DismissedToEnd -> {
                        /* Toast.makeText(context, "dismiss to end detected", Toast.LENGTH_SHORT)
                             .show()*/
                    }
                }
                true
            })
            SwipeToDismiss(state = dismissState1, //listen to state changes
                dismissThresholds = { FractionalThreshold(0.5f) },//row must be over 50% of the screen before it is dismissed.
                directions = setOf(DismissDirection.EndToStart), //swipe direction
                //upper layout
                dismissContent = {
                    Card(modifier = cardModifier, shape = cardShape, elevation = cardElevation) {
                        Row(
                            horizontalArrangement = rowHorizontalArrangement, modifier = rowModifier
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
                    AnimatedVisibility(
                        visible = isVisibleBackground1, enter = fadeIn(), exit = fadeOut()
                    ) {
                        Row(
                            horizontalArrangement = rowHorizontalArrangement,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = rowModifier
                                .padding(all = 16.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.End)
                        ) {
                            Text(
                                text = stringResource(R.string.swipetodelete).split(" ").reversed()
                                    .joinToString(" ")
                            )
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.swipetodelete),
                                modifier = iconModifier
                            )
                        }
                    }
                })
            //endregion
            //region swipe to right & left
            var isVisibleBackground2 by remember { mutableStateOf(true) }
            val dismissState2 = rememberDismissState(confirmStateChange = {
                when (it) {
                    DismissValue.Default -> {
//                        Toast.makeText(context, "default state", Toast.LENGTH_SHORT).show()
                    }
                    DismissValue.DismissedToStart -> {
                        isVisibleBackground2 = false
                        Toast.makeText(
                            context, "dismiss to start detected", Toast.LENGTH_SHORT
                        ).show()
                    }
                    DismissValue.DismissedToEnd -> {
                        isVisibleBackground2 = false
                        Toast.makeText(context, "dismiss to end detected", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                true
            })
            SwipeToDismiss(state = dismissState2, //listen to state changes
                dismissThresholds = { FractionalThreshold(0.5f) },//row must be over 50% of the screen before it is dismissed.
                directions = setOf(
                    DismissDirection.StartToEnd,
                    DismissDirection.EndToStart
                ), //swipe direction
                //upper layout
                dismissContent = {
                    Card(modifier = cardModifier, shape = cardShape, elevation = cardElevation) {
                        Row(
                            horizontalArrangement = rowHorizontalArrangement, modifier = rowModifier
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
                    AnimatedVisibility(
                        visible = isVisibleBackground2, enter = fadeIn(), exit = fadeOut()
                    ) {
                        Row(
                            horizontalArrangement = rowHorizontalArrangement,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = rowModifier
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
                })
            //endregion
        }
    }
}