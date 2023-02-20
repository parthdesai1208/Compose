package com.parthdesai1208.compose.view.uicomponents

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.parthdesai1208.compose.utils.currentDateTime
import com.parthdesai1208.compose.utils.dateFormatterDDMMYYYY
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DateTimeCompose() {
    var selectedDateText by remember { mutableStateOf("") }
    var currentDateTimeVar by remember { mutableStateOf("") }

    val activity = LocalContext.current as AppCompatActivity

    LaunchedEffect(key1 = 0, block = {
        while (true) {
            currentDateTimeVar = currentDateTime() ?: ""
            delay(1000)
        }
    })

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AnimatedContent(targetState = currentDateTimeVar, transitionSpec = {
                if (targetState > initialState) {
                    // If the target number is larger than old value
                    slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                } else {
                    // If the target number is smaller than old value
                    slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
                }.using(
                    //for adding effect on slide up-down animation
                    SizeTransform(clip = false)
                )
            }) {
                Text(
                    text = "Current Date & time: $it",
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = if (selectedDateText.isNotEmpty()) {
                    "Select any Date: $selectedDateText"
                } else {
                    "you can pick any date"
                },
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onPress = {
                            showDatePicker(
                                activity,
                                updateDate = {
                                    selectedDateText = it.dateFormatterDDMMYYYY() ?: ""
                                })
                        })
                    }
            )
        }
    }
}

private fun showDatePicker(activity: AppCompatActivity, updateDate: (Long?) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.tag)
    picker.addOnPositiveButtonClickListener {
        updateDate(it)
    }
}