package com.parthdesai1208.compose.view.uicomponents

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.parthdesai1208.compose.utils.currentDateTime12HourFormat
import com.parthdesai1208.compose.utils.currentDateTime24HourFormat
import com.parthdesai1208.compose.utils.currentDateTimeWithTimeZone24HourFormat
import com.parthdesai1208.compose.utils.dateFormatterDDMMYYYY
import kotlinx.coroutines.delay

@Composable
fun DateTimeCompose() {
    var selectedDateText by rememberSaveable { mutableStateOf("") }
    var selectedDateRangeText by rememberSaveable { mutableStateOf("") }
    var varCurrentDateTimeWithTimeZone24HourFormat by remember { mutableStateOf("") }
    var varCurrentDateTime24HourFormat by remember { mutableStateOf("") }
    var varCurrentDateTime12HourFormat by remember { mutableStateOf("") }

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

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Current Date & time with timezone 24 hour: $varCurrentDateTimeWithTimeZone24HourFormat",
                textAlign = TextAlign.Center
            )

            Text(
                text = "Current Date & time 24 hour: $varCurrentDateTime24HourFormat",
                textAlign = TextAlign.Center
            )

            Text(
                text = "Current Date & time 12 hour: $varCurrentDateTime12HourFormat",
                textAlign = TextAlign.Center
            )

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

private fun showDateRangePicker(activity: AppCompatActivity, updateDate: (Long?, Long?) -> Unit) {
    val picker = MaterialDatePicker.Builder.dateRangePicker().build()
    picker.show(activity.supportFragmentManager, picker.tag)
    picker.addOnPositiveButtonClickListener {
        updateDate(it.first, it.second)
    }
}