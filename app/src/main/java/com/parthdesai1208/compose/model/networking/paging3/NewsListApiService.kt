package com.parthdesai1208.compose.model.networking.paging3


import android.content.Context
import com.parthdesai1208.compose.utils.Constant.PAGING_API_BASE_URL
import com.parthdesai1208.compose.utils.getOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListApiService {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int
    ): paging3Response


    companion object {
        private var apiService: NewsListApiService? = null
        fun getInstance(context: Context): NewsListApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(PAGING_API_BASE_URL)
                    .client(context.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(NewsListApiService::class.java)
            }
            return apiService!!
        }
    }
}