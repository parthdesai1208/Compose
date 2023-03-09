package com.parthdesai1208.compose.model.networking.paging3withroom

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Paging3WithRoomDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<Article>)

    @Query("select * from Article where title LIKE '%' || :search || '%' or description LIKE '%' || :search || '%' or content LIKE '%' || :search || '%'")
    suspend fun searchArticle(search: String): List<Article>

    @Query("Select * From Article Order By page")
    fun getArticles(): PagingSource<Int, Article>

    @Query("delete from Article")
    suspend fun clearArticle()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKey: List<RemoteKeys>)

    @Query("Select * From remote_key Where articleUrl = :id")
    suspend fun getRemoteKeyByMovieID(id: String): RemoteKeys?

    @Query("Delete From remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}