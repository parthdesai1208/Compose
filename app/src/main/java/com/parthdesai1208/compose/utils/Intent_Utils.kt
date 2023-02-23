package com.parthdesai1208.compose.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.openInChromeCustomTab(url: String) {
    val builder = CustomTabsIntent.Builder().build()
    builder.launchUrl(this, Uri.parse(url))
}
