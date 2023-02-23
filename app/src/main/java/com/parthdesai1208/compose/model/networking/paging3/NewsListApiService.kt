package com.parthdesai1208.compose.model.networking.paging3


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListApiService {

    @GET("everything?q=apple&sortBy=popularity")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int
    ): paging3Response


    companion object {
        var apiService: NewsListApiService? = null
        fun getInstance(): NewsListApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(NewsListApiService::class.java)
            }
            return apiService!!
        }
    }
}