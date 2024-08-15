package com.parthdesai1208.compose.viewmodel.networking

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.parthdesai1208.compose.model.networking.paging3withroom.Article
import com.parthdesai1208.compose.model.networking.paging3withroom.NewsListApiService
import com.parthdesai1208.compose.model.networking.paging3withroom.Paging3WithRoomDataBase
import com.parthdesai1208.compose.model.networking.paging3withroom.Paging3WithRoomDataBasePagingSource
import com.parthdesai1208.compose.utils.Constant.ITEM_PREFETCH_DISTANCE
import com.parthdesai1208.compose.utils.Constant.PAGING_ITEM_SIZE
import kotlinx.coroutines.flow.Flow

class Paging3WithRoomDBVM(
    private val newsApiService: NewsListApiService,
    private val paging3WithRoomDataBase: Paging3WithRoomDataBase,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getArticle(query: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_ITEM_SIZE,
                prefetchDistance = ITEM_PREFETCH_DISTANCE,
                initialLoadSize = PAGING_ITEM_SIZE
            ),
            pagingSourceFactory = {
                paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao().getArticles()
            },
            remoteMediator = Paging3WithRoomDataBasePagingSource(
                newsApiService = newsApiService,
                paging3WithRoomDataBase = paging3WithRoomDataBase,
                query = query,
            )
        ).flow
}