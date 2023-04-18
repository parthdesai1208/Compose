package com.parthdesai1208.compose.view.uicomponents

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.parthdesai1208.compose.utils.asTwoDigit
import com.parthdesai1208.compose.utils.currentDateTime12HourFormat
import com.parthdesai1208.compose.utils.currentDateTime24HourFormat
import com.parthdesai1208.compose.utils.currentDateTimeWithTimeZone24HourFormat
import com.parthdesai1208.compose.utils.currentTimeWithAnimationWith12Hour
import com.parthdesai1208.compose.utils.dateFormatterDDMMYYYY
import com.parthdesai1208.compose.utils.getTimeWithMeridiem
import kotlinx.coroutines.delay

@Composable
fun DateTimeCompose() {
    var selectedDateText by rememberSaveable { mutableStateOf("") }
    var selectedTimeText12H by rememberSaveable { mutableStateOf("") }
    var selectedTimeText24H by rememberSaveable { mutableStateOf("") }
    var selectedDateRangeText by rememberSaveable { mutableStateOf("") }
    var varCurrentDateTimeWithTimeZone24HourFormat by remember { mutableStateOf("") }
    var varCurrentDateTime24HourFormat by remember { mutableStateOf("") }
    var varCurrentDateTime12HourFormat by remember { mutableStateOf("") }
    var varCurrentTime12HourFormat by remember { mutableStateOf("") }
    var animatedHour by remember { mutableStateOf(0) }
    var animatedMinute by remember { mutableStateOf(0) }
    var animatedSecond by remember { mutableStateOf(0) }
    var animatedAMPM by remember { mutableStateOf("") }

    val activity = LocalContext.current as AppCompatActivity

    LaunchedEffect(key1 = 0, block = {
        while (true) {
            varCurrentDateTimeWithTimeZone24HourFormat =
                currentDateTimeWithTimeZone24HourFormat() ?: ""
            delay(1000)
        }
    })

    LaunchedEffect(key1 = 0, block = {
        while (true) {
            varCurrentDateTime24HourFormat =
                currentDateTime24HourFormat() ?: ""
            delay(1000)
        }
    })

    LaunchedEffect(key1 = 0, block = {
        while (true) {
            varCurrentDateTime12HourFormat =
                currentDateTime12HourFormat() ?: ""
            delay(1000)
        }
    })

    LaunchedEffect(key1 = 0, block = {
        while (true) {
            varCurrentTime12HourFormat =
                currentTimeWithAnimationWith12Hour() ?: ""
            animatedAMPM = varCurrentTime12HourFormat.split(" ")[1]
            val time = varCurrentTime12HourFormat.split(" ")[0]
            animatedHour = time.split(":")[0].toInt()
            animatedMinute = time.split(":")[1].toInt()
            animatedSecond = time.split(":")[2].toInt()
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

            Text(
                text = "Current Date & time with timezone 24 hour:\n $varCurrentDateTimeWithTimeZone24HourFormat",
                textAlign = TextAlign.Center
            )

            Text(
                text = "Current Date & time 24 hour:\n $varCurrentDateTime24HourFormat",
                textAlign = TextAlign.Center
            )

            Text(
                text = "Current Date & time 12 hour:\n $varCurrentDateTime12HourFormat",
                textAlign = TextAlign.Center
            )

            Row(horizontalArrangement = Arrangement.Center) {
                AnimatedContent(
                    targetState = animatedHour,
                    transitionSpec = { currentTimeAnimation() }) {
                    Text(
                        text = it.asTwoDigit() + ":",
                        textAlign = TextAlign.Center
                    )
                }
                AnimatedContent(targetState = animatedMinute,
                    transitionSpec = { currentTimeAnimation() }) {
                    Text(
                        text = it.asTwoDigit() + ":",
                        textAlign = TextAlign.Center
                    )
                }
                AnimatedContent(targetState = animatedSecond,
                    transitionSpec = { currentTimeAnimation() }) {
                    Text(
                        text = it.asTwoDigit(),
                        textAlign = TextAlign.Center
                    )
                }
                AnimatedContent(targetState = animatedAMPM) {
                    Text(
                        text = " $it",
                        textAlign = TextAlign.Center
                    )
                }
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

            Text(
                text = if (selectedDateRangeText.isNotEmpty()) {
                    "Select any DateRange: $selectedDateRangeText"
                } else {
                    "you can pick any date range"
                },
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onPress = {
                            showDateRangePicker(
                                activity,
                                updateDate = { it1, it2 ->
                                    selectedDateRangeText =
                                        it1.dateFormatterDDMMYYYY() + " to " + it2.dateFormatterDDMMYYYY()
                                })
                        })
                    }, textAlign = TextAlign.Center
            )

            Text(
                text = if (selectedTimeText12H.isNotEmpty()) {
                    "Select any time(12H): $selectedTimeText12H"
                } else {
                    "you can pick any time(12H)"
                },
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onPress = {
                            showTimePicker12H(activity, updateTime = { it1, it2 ->
                                it1.getTimeWithMeridiem { hour, meridiem ->
                                    selectedTimeText12H =
                                        "${hour.asTwoDigit()}:${it2.asTwoDigit()} $meridiem"
                                }
                            })
                        })
                    }
            )

            Text(
                text = if (selectedTimeText24H.isNotEmpty()) {
                    "Select any time(24H): $selectedTimeText24H"
                } else {
                    "you can pick any time(24H)"
                },
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onPress = {
                            showTimePicker24H(activity, updateTime = { it1, it2 ->
                                it1.getTimeWithMeridiem { hour, meridiem ->
                                    selectedTimeText24H =
                                        "${hour.asTwoDigit()}:${it2.asTwoDigit()} $meridiem"
                                }
                            })
                        })
                    }
            )
        }
    }
}

fun AnimatedContentTransitionScope<Int>.currentTimeAnimation(): ContentTransform {
    return if (targetState > initialState) {
        // If the target number is larger than old value
        slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
    } else {
        // If the target number is smaller than old value
        slideInVertically { height -> -height } + fadeIn() with slideOutVertically { height -> height } + fadeOut()
    }.using(
        //for adding effect on slide up-down animation
        SizeTransform(clip = false)
    )
}

private fun showDatePicker(activity: AppCompatActivity, updateDate: (Long?) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.tag)
    picker.addOnPositiveButtonClickListener {
        updateDate(it)
    }
}

private fun showDateRangePicker(activity: AppCompatActivity, updateDate: (Long?, Long?) -> Unit) {
    val picker = MaterialDatePicker.Builder.dateRangePicker().build()
    picker.show(activity.supportFragmentManager, picker.tag)
    picker.addOnPositiveButtonClickListener {
        updateDate(it.first, it.second)
    }
}

private fun showTimePicker12H(activity: AppCompatActivity, updateTime: (Int, Int) -> Unit) {
    val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).build()
    timePicker.show(activity.supportFragmentManager, timePicker.tag)
    timePicker.addOnPositiveButtonClickListener {
        updateTime(timePicker.hour, timePicker.minute)
    }
}

private fun showTimePicker24H(activity: AppCompatActivity, updateTime: (Int, Int) -> Unit) {
    val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
    timePicker.show(activity.supportFragmentManager, timePicker.tag)
    timePicker.addOnPositiveButtonClickListener {
        updateTime(timePicker.hour, timePicker.minute)
    }
}

