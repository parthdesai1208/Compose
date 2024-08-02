package com.parthdesai1208.compose.view.networking

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.networking.MoviesList
import com.parthdesai1208.compose.model.networking.moviesPreviewList
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.rememberMutableStateListOf
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.networking.MoviesListVM

@Composable
fun MoviesListScreen(vm: MoviesListVM, navHostController: NavHostController) {
    val uiState = vm.uiState.collectAsState().value
    var isErrorDialogOpened by rememberSaveable { mutableStateOf(true) }

    when {
        uiState.movies.isNotEmpty() -> {
            MoviesList(uiState.movies, navHostController)
        }

        uiState.error?.isNotBlank() == true -> {
            //show error
            MoviesErrorScreen(
                isErrorDialogOpened = isErrorDialogOpened,
                error = uiState.error,
                onGoBackClick = {
                    isErrorDialogOpened = false
                    navHostController.popBackStack()
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
fun MoviesList(moviesList: List<MoviesList>, navHostController: NavHostController) {
    val selectedItemList = rememberMutableStateListOf<Int>()
    BuildTopBarWithScreen(
        title = stringResource(id = R.string.moviesList),
        screen = {
            LazyColumn(content = {
                itemsIndexed(items = moviesList) { index, item ->
                    MovieItem(index, movie = item, selectedList = selectedItemList, onItemClick = {
                        if (!selectedItemList.contains(it)) {
                            selectedItemList.add(it)
                        } else {
                            selectedItemList.removeAt(selectedItemList.indexOf(it))
                        }
                    })
                }
            })
        },
        onBackIconClick = {
            navHostController.popBackStack()
        })
}

@Composable
fun MovieItem(
    index: Int,
    movie: MoviesList,
    selectedList: SnapshotStateList<Int>,
    onItemClick: (Int) -> Unit
) {
    val context = LocalContext.current

    val backgroundColor = if (selectedList.contains(index)) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.background
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable {
                onItemClick(index)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = backgroundColor
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
        MoviesList(moviesList = moviesPreviewList, rememberNavController())
    }
}

@Preview
@Composable
fun MoviesItemPreview() {
    ComposeTheme {
        Surface {
            moviesPreviewList.take(1).forEach {
                MovieItem(
                    index = 0,
                    movie = it,
                    selectedList = emptyList<Int>() as SnapshotStateList<Int>,
                    onItemClick = {})
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
