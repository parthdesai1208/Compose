package com.parthdesai1208.compose.utils


import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.text.TextLayoutResult

fun TextLayoutResult.getBoundingBoxesForRange(start: Int, end: Int): List<Rect> {
    var prevRect: Rect? = null
    var firstLineCharRect: Rect? = null
    val boundingBoxes = mutableListOf<Rect>()
    for (i in start..end) {
        val rect = getBoundingBox(i)
        val isLastRect = i == end

        // single char case
        if (isLastRect && firstLineCharRect == null) {
            firstLineCharRect = rect
            prevRect = rect
        }

        // `rect.right` is zero for the last space in each line
        // looks like an issue to me, reported: https://issuetracker.google.com/issues/197146630
        if (!isLastRect && rect.right == 0f) continue

        if (firstLineCharRect == null) {
            firstLineCharRect = rect
        } else if (prevRect != null) {
            if (prevRect.bottom != rect.bottom || isLastRect) {
                boundingBoxes.add(
                    firstLineCharRect.copy(right = prevRect.right)
                )
                firstLineCharRect = rect
            }
        }
        prevRect = rect
    }
    return boundingBoxes
}