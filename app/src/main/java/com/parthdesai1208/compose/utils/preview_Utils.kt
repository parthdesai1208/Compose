package com.parthdesai1208.compose.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(group = "phone", name = "light", device = Devices.PIXEL_7, showSystemUi = true)
@Preview(
    group = "phone",
    name = "dark",
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_7,
    showSystemUi = true,
    showBackground = true
)
annotation class Phone

@Preview(
    group = "phone",
    name = "land-light",
    showSystemUi = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 760,
    heightDp = 360//"spec:width=760dp,height=360dp,dpi=533.94,isRound=false,chinSize=0dp,orientation=landscape"
)
@Preview(
    group = "phone",
    name = "land-dark",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 760,
    heightDp = 360//"spec:width=2026.67dp,height=960dp,dpi=537,isRound=false,chinSize=0dp,orientation=landscape"
)
annotation class PhoneInLandscape


@Preview(group = "tablet", name = "light", device = Devices.PIXEL_C)
@Preview(group = "tablet", name = "dark", uiMode = UI_MODE_NIGHT_YES, device = Devices.PIXEL_C)
annotation class Tablet

@Preview(
    group = "tablet",
    name = "land-light",
    showSystemUi = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 900,
    heightDp = 1280
)
@Preview(
    group = "tablet",
    name = "land-dark",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 900,
    heightDp = 1280
)
annotation class TabletInPortrait

@Preview(group = "foldable", name = "light", device = Devices.FOLDABLE)
@Preview(group = "foldable", name = "dark", uiMode = UI_MODE_NIGHT_YES, device = Devices.FOLDABLE)
annotation class Foldable

@Preview(group = "desktop",name = "light", device = Devices.DESKTOP)
@Preview(group = "desktop",name = "dark", uiMode = UI_MODE_NIGHT_YES, device = Devices.DESKTOP)
annotation class Desktop

@Phone
@PhoneInLandscape
@Tablet
@TabletInPortrait
@Foldable
@Desktop
annotation class AllDevices