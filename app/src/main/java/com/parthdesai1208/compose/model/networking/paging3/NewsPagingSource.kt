package com.parthdesai1208.compose.model.networking.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.parthdesai1208.compose.BuildConfig
import com.parthdesai1208.compose.utils.Constant.API_RESPONSE_REMOVED_KEYWORD
import com.parthdesai1208.compose.utils.Constant.PAGE_LIMIT
import com.parthdesai1208.compose.utils.Constant.PAGING_ITEM_SIZE
import kotlinx.coroutines.delay

class NewsPagingSource(
    private val newsApiService: NewsListApiService,
    private val query: String,
) :
    PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val newsKey = BuildConfig.NEWS_API_KEY
            delay(3000)
            val response = newsApiService.getNews(query, page, newsKey, PAGING_ITEM_SIZE)
            val filterList =
                response.articles.filterNot { it.url?.contains(API_RESPONSE_REMOVED_KEYWORD) == true }

            LoadResult.Page(
                data = filterList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.articles.isEmpty() || page == PAGE_LIMIT) null else page.plus(
                    1
                )
                //here we restrict to 10 page because in developer mode news API don't allow more pages
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}