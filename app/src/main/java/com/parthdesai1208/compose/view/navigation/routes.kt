package com.parthdesai1208.compose.view.navigation

import kotlinx.serialization.Serializable

@Serializable
object ComposeSamplesScreen

@Serializable
data class ComposeSampleChildrenScreen(val pathPostFix: Int)

@Serializable
data class UIComponentsListingScreen(val pathPostFix: Int)

@Serializable
data class StateListingScreen(val pathPostFix: Int)

@Serializable
data class CustomModifierScreen(val pathPostFix: Int)

@Serializable
data class CustomLayoutScreen(val pathPostFix: Int)

@Serializable
data class AnimationListingScreen(val pathPostFix: Int)

@Serializable
data class ColumnListingScreenPath(val pathPostFix: Int)

@Serializable
data class RowListingScreenPath(val pathPostFix: Int)

@Serializable
data class BoxListingScreenPath(val pathPostFix: Int)

@Serializable
data class VerticalListingScreenPath(val pathPostFix: Int)