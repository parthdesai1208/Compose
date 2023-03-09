package com.parthdesai1208.compose.viewmodel.networking

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.parthdesai1208.compose.model.networking.paging3withroom.*
import kotlinx.coroutines.flow.Flow


const val PAGE_SIZE = 10

class Paging3WithRoomDBVM(
    private val newsApiService: NewsListApiService,
    private val paging3WithRoomDataBase: Paging3WithRoomDataBase,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getArticle(query: String, sortBy: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 4,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao().getArticles()
            },
            remoteMediator = Paging3WithRoomDataBasePagingSource(
                newsApiService = newsApiService,
                paging3WithRoomDataBase = paging3WithRoomDataBase,
                query = query,
                sortBy = sortBy
            )
        ).flow
}