package com.parthdesai1208.compose.model.networking.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.parthdesai1208.compose.BuildConfig
import com.parthdesai1208.compose.view.networking.PageSize

class NewsPagingSource(private val newsApiService: NewsListApiService) :
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
            val response = newsApiService.getNews(page, newsKey, PageSize)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.articles.isEmpty() || page == 10) null else page.plus(1)
                //here we restrict to 10 page because in developer mode news API don't allow more pages
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}