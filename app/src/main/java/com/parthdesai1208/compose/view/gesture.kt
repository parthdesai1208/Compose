package com.parthdesai1208.compose.view

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(modifier = cardModifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    Toast.makeText(context, "tap detected", Toast.LENGTH_SHORT).show()
                },
                    onPress = {
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

        }
    }
}