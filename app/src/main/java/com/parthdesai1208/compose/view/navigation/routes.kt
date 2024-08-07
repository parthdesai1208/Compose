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

@Serializable
object HorizontalAdaptiveListScreen

@Serializable
data class BottomsheetListingScreenPath(val pathPostFix: Int)

@Serializable
data class DrawListingScreenPath(val pathPostFix: Int)

@Serializable
data class AnyScreenListingScreenPath(val pathPostFix: Int)

@Serializable
data class NetworkListingScreenPath(val pathPostFix: Int)

@Serializable
data class PermissionListingScreenPath(val pathPostFix: Int)

@Serializable
data class SecurityListingScreenPath(val pathPostFix: Int)