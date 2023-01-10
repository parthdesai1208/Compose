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

val moviesPreviewList = listOf(
    MoviesList(
        name = "Coco",
        imageUrl = "https://howtodoandroid.com/images/coco.jpg",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        category = "Latest"
    ),MoviesList(
        name = "Coco",
        imageUrl = "https://howtodoandroid.com/images/coco.jpg",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        category = "Latest"
    ),MoviesList(
        name = "Coco",
        imageUrl = "https://howtodoandroid.com/images/coco.jpg",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        category = "Latest"
    ),MoviesList(
        name = "Coco",
        imageUrl = "https://howtodoandroid.com/images/coco.jpg",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        category = "Latest"
    ),MoviesList(
        name = "Coco",
        imageUrl = "https://howtodoandroid.com/images/coco.jpg",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        category = "Latest"
    ),MoviesList(
        name = "Coco",
        imageUrl = "https://howtodoandroid.com/images/coco.jpg",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        category = "Latest"
    ),
    MoviesList(
        name = "Coco",
        imageUrl = "https://howtodoandroid.com/images/coco.jpg",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar",
        category = "Latest"
    )
)