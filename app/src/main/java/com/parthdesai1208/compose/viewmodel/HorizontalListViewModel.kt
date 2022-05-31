package com.parthdesai1208.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parthdesai1208.compose.model.StaggeredGridListDataClass
import com.parthdesai1208.compose.model.staggeredGridListData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HorizontalListViewModel : ViewModel() {

    private val _staggeredGridItems = MutableStateFlow(emptyList<StaggeredGridListDataClass>())

    var staggeredGridItems : StateFlow<List<StaggeredGridListDataClass>>
        get() = _staggeredGridItems

    /*var StaggeredGridItems = mutableStateListOf<StaggeredGridListDataClass>()
        private set*/

    init {
        staggeredGridItems = MutableStateFlow(emptyList())
        _staggeredGridItems.value = staggeredGridListData
    }

    fun onChipClicked(index : Int,isAdded : Boolean){
        viewModelScope.launch {
            _staggeredGridItems.value.get(index = index).isAdded = isAdded
        }
    }
}