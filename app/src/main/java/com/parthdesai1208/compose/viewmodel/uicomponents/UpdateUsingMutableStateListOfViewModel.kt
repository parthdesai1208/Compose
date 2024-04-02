package com.parthdesai1208.compose.viewmodel.uicomponents

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.parthdesai1208.compose.model.uicomponents.UpdateUsingMutableStateListOfList
import com.parthdesai1208.compose.model.uicomponents.UpdateUsingMutableStateListOfModel

class UpdateUsingMutableStateListOfViewModel : ViewModel() {

    var updateUsingMutableStateListOfModelList =
        mutableStateListOf<UpdateUsingMutableStateListOfModel>()
        private set

    init {
        updateUsingMutableStateListOfModelList.addAll(UpdateUsingMutableStateListOfList)
    }

    fun onClick(index: Int, bool: Boolean) {
        updateUsingMutableStateListOfModelList[index] = UpdateUsingMutableStateListOfModel(
            UpdateUsingMutableStateListOfList[index].name, bool
        )
    }
}