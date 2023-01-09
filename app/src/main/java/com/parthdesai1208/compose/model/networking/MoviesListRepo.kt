package com.parthdesai1208.compose.model.networking

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MoviesListRepo {
    fun getMovies(): Flow<List<MoviesList>>
}

class MoviesListRepoImpl : MoviesListRepo {
    override fun getMovies(): Flow<List<MoviesList>> = flow {
        val apiService = MoviesListApiService.getInstance()
        emit(apiService.getMovies())
    }

}