package com.parthdesai1208.compose.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.*

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
    GoogleMapsImageModel(R.drawable.gi6, isIconVisible = false, isTextVisible = true,Icons.Default.Settings,"30 days ago"),
    GoogleMapsImageModel(R.drawable.gi7, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi8, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi1, isIconVisible = false, isTextVisible = true,Icons.Default.Settings,"45 days ago"),
    GoogleMapsImageModel(R.drawable.gi10, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi11, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi12, isIconVisible = false, isTextVisible = true,Icons.Default.Settings,"5 days ago"),
    GoogleMapsImageModel(R.drawable.gi13, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi14, isIconVisible = false, isTextVisible = false,Icons.Default.Settings,""),
    GoogleMapsImageModel(R.drawable.gi15, isIconVisible = true, isTextVisible = true,Icons.Default.Image,"See all"),
)

data class CategoryData(
    val icon : ImageVector,
    val categoryName : String,
    val tintColor : Color
)

val categoryList = listOf(
    CategoryData(icon = Icons.Default.Restaurant, categoryName = "Restaurant", tintColor = restaurant_gmap),
    CategoryData(icon = Icons.Default.ShoppingCart, categoryName = "Groceries", tintColor = groceries_gmap),
    CategoryData(icon = Icons.Default.ChargingStation, categoryName = "Petrol", tintColor = petrol_gmap),
    CategoryData(icon = Icons.Default.Attractions, categoryName = "Attractions", tintColor = attractions_gmap),
    CategoryData(icon = Icons.Default.Hotel, categoryName = "Hotels", tintColor = hotel_gmap),
    CategoryData(icon = Icons.Default.ShoppingBag, categoryName = "Shopping", tintColor = shopping_gmap),
    CategoryData(icon = Icons.Default.Coffee, categoryName = "Coffee", tintColor = coffee_gmap),
    CategoryData(icon = Icons.Default.More, categoryName = "More", tintColor = more_gmap)
)