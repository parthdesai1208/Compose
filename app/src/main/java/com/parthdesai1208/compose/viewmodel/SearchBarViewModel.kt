package com.parthdesai1208.compose.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.parthdesai1208.compose.model.searchBarList
import java.util.*


class SearchBarViewModel : ViewModel() {

    var searchBarItems = mutableStateListOf<String>()
        private set

    init {
        searchBarItems = mutableStateListOf()
        addSearch()
    }

    private fun addSearch() {
        searchBarItems.clear()
        searchBarItems.addAll(searchBarList)
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            addSearch()
        } else {
            val tempList = searchBarList.filter {
                it.lowercase(Locale.ENGLISH).contains(query.lowercase(Locale.ENGLISH))
            }
            searchBarItems.clear()
            searchBarItems.addAll(tempList)
        }

    }
}