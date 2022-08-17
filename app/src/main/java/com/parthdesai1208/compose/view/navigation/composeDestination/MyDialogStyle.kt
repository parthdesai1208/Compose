package com.parthdesai1208.compose.view.navigation.composeDestination

import androidx.compose.ui.window.DialogProperties
import com.ramcosta.composedestinations.spec.DestinationStyle

object MyDialogStyle : DestinationStyle.Dialog {
    override val properties: DialogProperties
        get() = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
}