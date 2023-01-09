package com.parthdesai1208.compose.view.networking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.parthdesai1208.compose.model.networking.MoviesList
import com.parthdesai1208.compose.viewmodel.networking.MoviesListVM

@Composable
fun MoviesListScreen(vm: MoviesListVM) {
    val list = vm.uiState.collectAsState().value
    when {
        list.movies.isNotEmpty() -> {
            MoviesList(list.movies)
        }
        list.error?.isNotBlank()!! -> {
            //show error
        }
        list.loading -> {
            //show loading
        }
    }
}

@Composable
fun MoviesList(moviesList: List<MoviesList>) {
    LazyColumn(content = {
        itemsIndexed(items = moviesList) { _, item ->
            MovieItem(movie = item)
        }
    })
}

@Composable
fun MovieItem(movie: MoviesList) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(data = movie.imageUrl)
                        .apply(block = {
                            scale(Scale.FILL)
                            transformations(CircleCropTransformation())
                        })
                ), contentDescription = movie.desc,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = movie.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movie.category,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(4.dp)
                )
                Text(
                    text = movie.desc,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}