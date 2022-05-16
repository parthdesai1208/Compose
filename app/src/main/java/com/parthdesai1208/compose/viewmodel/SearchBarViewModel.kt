package com.parthdesai1208.compose.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.*


class SearchBarViewModel : ViewModel() {

    private var searchBarList = emptyList<String>()

    var searchBarItems = mutableStateListOf<String>()
        private set

    init {
        searchBarList = listOf(
            "India",
            "US",
            "UK",
            "Canada",
            "Germany",
            "France",
            "UAE",
            "Japan",
            "Australia",
            "Ireland",
            "Russia",
            "Singapore",
            "Malaysia",
            "China",
            "Cambodia",
            "Saudi Arabia",
            "Iran",
            "Iraq",
            "Kuwait",
            "South Africa",
            "Congo",
            "Turkey",
            "Spain",
            "Portugal",
            "Italy",
            "Switzerland",
            "Nepal",
            "Myanmar",
            "Bangladesh",
            "Scotland",
            "Finland",
            "Sweden",
            "Poland",
            "Norway", "Greenland", "Iceland", "Ukraine", "Netherlands", "Belgium", "Austria"
        ).sorted()
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