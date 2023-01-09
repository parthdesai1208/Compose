package com.parthdesai1208.compose.model.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MoviesListApiService {

    @GET("movielist.json")
    suspend fun getMovies(): List<MoviesList>

    companion object {
        var apiService: MoviesListApiService? = null
        fun getInstance(): MoviesListApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://www.howtodoandroid.com/apis/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(MoviesListApiService::class.java)
            }
            return apiService!!
        }
    }
}