package com.parthdesai1208.compose.model.networking.paging3withroom

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.parthdesai1208.compose.BuildConfig
import com.parthdesai1208.compose.utils.Constant.API_RESPONSE_REMOVED_KEYWORD
import com.parthdesai1208.compose.utils.Constant.PAGING_ITEM_SIZE
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class Paging3WithRoomDataBasePagingSource(
    private val newsApiService: NewsListApiService,
    private val paging3WithRoomDataBase: Paging3WithRoomDataBase,
    private val query: String,
) :
    RemoteMediator<Int, Article>() {

    /**
     * In cases where the local data needs to be fully refreshed, initialize() should return InitializeAction.LAUNCH_INITIAL_REFRESH.
     * This causes the RemoteMediator to perform a remote refresh to fully reload the data.
     *
     * In cases where the local data doesn't need to be refreshed, initialize() should return InitializeAction.SKIP_INITIAL_REFRESH.
     * This causes the RemoteMediator to skip the remote refresh and load the cached data.
     */
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao()
                .getCreationTime() ?: 0) < cacheTimeout
        ) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    /** LoadType.Prepend
     * When we need to load data at the beginning of the currently loaded data set, the load parameter is LoadType.PREPEND
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { article ->
            paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao()
                .getRemoteKeyByMovieID(article.url)
        }
    }

    /** LoadType.Append
     * When we need to load data at the end of the currently loaded data set, the load parameter is LoadType.APPEND
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { article ->
            paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao()
                .getRemoteKeyByMovieID(article.url)
        }
    }

    /** LoadType.REFRESH
     * Gets called when it's the first time we're loading data, or when PagingDataAdapter.refresh() is called;
     * so now the point of reference for loading our data is the state.anchorPosition.
     * If this is the first load, then the anchorPosition is null.
     * When PagingDataAdapter.refresh() is called, the anchorPosition is the first visible position in the displayed list, so we will need to load the page that contains that specific item.
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Article>): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { id ->
                paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                //New Query so clear the DB
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val key = BuildConfig.NEWS_API_KEY
            val apiResponse = newsApiService.getNews(
                query = query,
                page = page,
                apiKey = key,
                pageSize = PAGING_ITEM_SIZE,
            )

            delay(3500) //for simulate progress bar

            val articles =
                apiResponse.articles.filterNot { it.url.contains(API_RESPONSE_REMOVED_KEYWORD) }
            val endOfPaginationReached = articles.isEmpty()

            paging3WithRoomDataBase.withTransaction { //block will execute atomically, run query serially inside block
                if (loadType == LoadType.REFRESH) {
                    paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao().clearRemoteKeys()
                    paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao().clearArticle()
                }

                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = articles.map {
                    RemoteKeys(
                        articleUrl = it.url,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao().insertRemoteKeys(remoteKeys)
                paging3WithRoomDataBase.getPaging3WithRoomDataBaseDao()
                    .insertArticle(articles.onEachIndexed { _, article -> article.page = page })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}


