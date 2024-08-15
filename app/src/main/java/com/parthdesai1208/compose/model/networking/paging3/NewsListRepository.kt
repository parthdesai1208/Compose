package com.parthdesai1208.compose.model.networking.paging3

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.parthdesai1208.compose.utils.Constant.PAGING_ITEM_SIZE

class NewsListRepository {

    fun getNews(query: String, context: Context) = Pager(
        config = PagingConfig(pageSize = PAGING_ITEM_SIZE),
        pagingSourceFactory = {
            val newsApiService = NewsListApiService.getInstance(context)
            NewsPagingSource(newsApiService, query)
        }).flow
}