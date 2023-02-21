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