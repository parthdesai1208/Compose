package com.parthdesai1208.compose.model.networking

data class MoviesList(
    val name: String,
    val imageUrl: String,
    val desc: String,
    val category: String
)

data class MoviesListScreenUI(
    val movies: List<MoviesList> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)