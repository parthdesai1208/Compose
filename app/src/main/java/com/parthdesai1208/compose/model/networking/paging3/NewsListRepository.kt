package com.parthdesai1208.compose.model.networking.paging3

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.parthdesai1208.compose.view.networking.PageSize

class NewsListRepository {

    fun getNews() = Pager(config = PagingConfig(pageSize = PageSize),
        pagingSourceFactory = {
            val newsApiService = NewsListApiService.getInstance()
            NewsPagingSource(newsApiService)
        }).flow
}