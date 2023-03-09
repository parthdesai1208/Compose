package com.parthdesai1208.compose.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent

fun Context.openInChromeCustomTab(url: String) {
    val builder = CustomTabsIntent.Builder().build()
    builder.launchUrl(this, Uri.parse(url))
}

fun AppCompatActivity.openActivityForResult(
    intent: Intent,
    activityResult: (ActivityResult) -> Unit
) {
    openActivityForResult(activityResult = activityResult).launch(intent)
}

private fun AppCompatActivity.openActivityForResult(activityResult: (ActivityResult) -> Unit) =
    this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        activityResult.invoke(it)
    }