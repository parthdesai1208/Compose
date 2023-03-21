package com.parthdesai1208.compose.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.remember
import com.parthdesai1208.compose.BuildConfig

class RecompositionCounter(var value: Int)

@Composable
fun LogCompositions(tag: String, msg: String) {
    if (BuildConfig.DEBUG) {
        val recompositionCounter = remember { RecompositionCounter(0) }

        Log.d(tag, "$msg RC Counter = ${recompositionCounter.value} $currentRecomposeScope")
        recompositionCounter.value++
    }
}