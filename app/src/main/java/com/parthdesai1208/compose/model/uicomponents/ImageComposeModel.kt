package com.parthdesai1208.compose.model.uicomponents

import androidx.annotation.DrawableRes
import com.parthdesai1208.compose.R

data class CuttingEdgeAvatars(@DrawableRes val avatar: Int, val description: String)

val listOfCuttingEdgeAvatars = listOf(
    CuttingEdgeAvatars(R.drawable.avatar_0, "Tracy"),
    CuttingEdgeAvatars(R.drawable.avatar_1, "Allison"),
    CuttingEdgeAvatars(R.drawable.avatar_2, "Ali"),
    CuttingEdgeAvatars(R.drawable.avatar_3, "Alberto"),
    CuttingEdgeAvatars(R.drawable.avatar_4, "Kim"),
    CuttingEdgeAvatars(R.drawable.avatar_5, "Google"),
    CuttingEdgeAvatars(R.drawable.avatar_6, "Sandra"),
    CuttingEdgeAvatars(R.drawable.avatar_7, "Trevor"),
    CuttingEdgeAvatars(R.drawable.avatar_8, "Sean"),
    CuttingEdgeAvatars(R.drawable.avatar_9, "Frank"),
    CuttingEdgeAvatars(R.drawable.avatar_10, "John"),
)