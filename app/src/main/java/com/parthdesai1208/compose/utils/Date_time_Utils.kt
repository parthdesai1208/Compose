package com.parthdesai1208.compose.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long?.dateFormatterDDMMYYYY(): String? {
    this?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
    return null
}

fun currentDateTimeWithTimeZone24HourFormat(): String? {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss z", Locale.getDefault())
    return formatter.format(Date())
}

fun currentDateTime24HourFormat(): String? {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}

fun currentDateTime12HourFormat(): String? {
    val formatter = SimpleDateFormat("dd/MM/yyyy KK:mm:ss aa", Locale.getDefault())
    return formatter.format(Date())
}

fun currentTimeWithAnimationWith12Hour(): String? {
    val formatter = SimpleDateFormat("KK:mm:ss aa", Locale.getDefault())
    return formatter.format(Date())
}

fun Int.getTimeWithMeridiem(hourWithMeriDiem: (Int, String) -> Unit) {
    val meridiem: String?
    var hour = this
    if (this > 12) {
        hour = this - 12
        meridiem = "pm"
    } else if (this == 0) {
        hour = 12
        meridiem = "am"
    } else if (this == 12) {
        meridiem = "pm"
    } else {
        meridiem = "am"
    }
    hourWithMeriDiem(hour, meridiem)
}