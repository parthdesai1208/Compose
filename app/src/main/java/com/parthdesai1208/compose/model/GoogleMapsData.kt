package com.parthdesai1208.compose.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.uicomponents.bottomsheet.GoogleMapsLikeBottomSheet

data class GoogleMapsImageModel(
    @DrawableRes val drawable: Int,
    val isIconVisible: Boolean,
    val isTextVisible: Boolean,
    val icon: ImageVector,
    val text: String
)

val GoogleMapsImageList = listOf(
    GoogleMapsImageModel(R.drawable.gi1, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi2, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi3, isIconVisible = false, isTextVisible = true,Icons.Default.Settings,"10 days ago"),
    GoogleMapsImageModel(R.drawable.gi4, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi5, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi6, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi7, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi8, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi1, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi10, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi11, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi12, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi13, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi14, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi15, isIconVisible = true, isTextVisible = true,Icons.Default.Image,"See all"),
)