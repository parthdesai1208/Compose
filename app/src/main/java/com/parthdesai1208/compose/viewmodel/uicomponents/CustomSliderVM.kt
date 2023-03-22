package com.parthdesai1208.compose.viewmodel.uicomponents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomSliderVM : ViewModel() {
    private val _selected = MutableLiveData(0)
    val selected: LiveData<Int> = _selected

    fun onValueChanged(selected: Int) {
        _selected.value = selected
    }
}