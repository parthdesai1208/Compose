package com.parthdesai1208.compose.viewmodel.networking.paging3

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.parthdesai1208.compose.model.networking.paging3.NewsListRepository

class NewsListViewModel(private val repository: NewsListRepository = NewsListRepository()) :
    ViewModel() {

    fun getNews(query: String, context: Context) =
        repository.getNews(query, context).cachedIn(viewModelScope)

}