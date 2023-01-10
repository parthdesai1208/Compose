package com.parthdesai1208.compose.view.networking

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.parthdesai1208.compose.model.networking.MoviesList
import com.parthdesai1208.compose.model.networking.moviesPreviewList
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.networking.MoviesListVM

@Composable
fun MoviesListScreen(vm: MoviesListVM, navHostController: NavHostController) {
    val uiState = vm.uiState.collectAsState().value
    var isErrorDialogOpened by rememberSaveable { mutableStateOf(true) }

    when {
        uiState.movies.isNotEmpty() -> {
            MoviesList(uiState.movies)
        }
        uiState.error?.isNotBlank() == true -> {
            //show error
            MoviesErrorScreen(
                isErrorDialogOpened = isErrorDialogOpened,
                error = uiState.error,
                onGoBackClick = {
                    isErrorDialogOpened = false
                    navHostController.navigateUp()
                })
        }
        uiState.loading -> {
            //show loading
            MoviesLoadingScreen(true)
        }
        else -> {
            //hide loading
            MoviesLoadingScreen(false)
        }
    }
}

@Composable
fun MoviesErrorScreen(isErrorDialogOpened: Boolean, error: String, onGoBackClick: () -> Unit) {
    if (isErrorDialogOpened) {
        AlertDialog(
            onDismissRequest = onGoBackClick,
            title = { Text(text = "Oops!", color = MaterialTheme.colors.onSurface) },
            text = { Text(text = error, color = MaterialTheme.colors.onSurface) },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
            buttons = {
                Button(
                    onClick = onGoBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back arrow"
                        )
                        Text(text = "ok, go back", color = MaterialTheme.colors.surface)
                    }
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }
}

@Composable
fun MoviesLoadingScreen(isLoading: Boolean) {
    Surface {
        AnimatedVisibility(visible = isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center),
                color = MaterialTheme.colors.onSurface
            )
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
            .height(120.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context).data(data = movie.imageUrl).apply(block = {
                        scale(Scale.FILL)
                        transformations(CircleCropTransformation())
                    }).build()
                ), contentDescription = movie.desc, modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp, alignment = Alignment.CenterVertically
                ), modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = movie.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
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
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
fun MoviesListPreview() {
    ComposeTheme {
        MoviesList(moviesList = moviesPreviewList)
    }
}

@Preview
@Composable
fun MoviesItemPreview() {
    ComposeTheme {
        Surface {
            moviesPreviewList.take(1).forEach {
                MovieItem(movie = it)
            }
        }
    }
}

@Preview
@Composable
fun MoviesLoadingScreenPreview() {
    ComposeTheme {
        MoviesLoadingScreen(true)
    }
}

@Preview
@Composable
fun MoviesErrorScreenPreview() {
    ComposeTheme {
        Surface {
            MoviesErrorScreen(isErrorDialogOpened = true,
                error = "Error occurred while try to connect to server",
                onGoBackClick = { })
        }
    }
}
