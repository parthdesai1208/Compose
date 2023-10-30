package com.parthdesai1208.compose.view.networking

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.parthdesai1208.compose.model.networking.paging3withroom.Article
import com.parthdesai1208.compose.model.networking.paging3withroom.NewsListApiService
import com.parthdesai1208.compose.model.networking.paging3withroom.Paging3WithRoomDataBase
import com.parthdesai1208.compose.utils.convertToAnotherDateFormat
import com.parthdesai1208.compose.utils.isOnline
import com.parthdesai1208.compose.utils.openInChromeCustomTab
import com.parthdesai1208.compose.viewmodel.networking.Paging3WithRoomDBVM
import kotlinx.coroutines.launch

enum class ArticleSortBy(sortBy: String) {
    Popularity("popularity"), PublishedAt("publishedAt")
}

@Composable
fun Paging3Room() {
    val context = LocalContext.current
    val viewModel = Paging3WithRoomDBVM(
        newsApiService = NewsListApiService.getInstance(),
        paging3WithRoomDataBase = Paging3WithRoomDataBase.getDatabase(context)
    )
    Paging3RoomWrapper(viewModel)
}

@Composable
private fun Paging3RoomWrapper(viewModel: Paging3WithRoomDBVM) {
    val articles = viewModel.getArticle(query = "apple", sortBy = ArticleSortBy.Popularity.name)
        .collectAsLazyPagingItems()
    var isExpandedArticle by rememberSaveable { mutableStateOf<String?>("") }
    val context = LocalContext.current
    val activity = LocalContext.current as AppCompatActivity
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var isSnackBarDisplayed by rememberSaveable { mutableStateOf(false) }

    Surface {
        Box {
            LazyColumn {
                items(count = articles.itemCount) { index ->
                    val item = articles[index]
                    item?.let {
                        ItemCompose(article = it, onClick = {
                            isExpandedArticle = if (isExpandedArticle == it.url) null else it.url
                        }, onNewsSourceClick = {
                            context.isOnline(failBlock = {
                                if (!isSnackBarDisplayed) {
                                    isSnackBarDisplayed = true
                                    coroutineScope.launch {
                                        val snack = snackBarHostState.showSnackbar(
                                            message = "No internet available",
                                            // actionLabel = "check",
                                            duration = SnackbarDuration.Long
                                        )
                                        if (snack == SnackbarResult.Dismissed) isSnackBarDisplayed =
                                            false
                                        if (snack == SnackbarResult.ActionPerformed) {
                                            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                                activity.openActivityForResult(intent = Intent(
                                                    Settings.Panel.ACTION_INTERNET_CONNECTIVITY
                                                ),
                                                    activityResult = {
                                                        //after user action on settings panel
                                                    })
                                            }*/
                                        }
                                    }
                                }
                            }, successBlock = {
                                context.openInChromeCustomTab(it.url)
                            })
                        }, isExpandedArticle = isExpandedArticle == it.url)
                    }
                }

                //execute on first time or user trigger refresh
                when (articles.loadState.refresh) {
                    is LoadState.Error -> {
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                (articles.loadState.refresh as LoadState.Error).error.localizedMessage?.let {
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp),
                                        text = it,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                    is LoadState.Loading -> { //Loading UI
                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = "First time loading"
                                )

                                CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
                            }
                        }
                    }
                    else -> {}
                }
                //execute on pagination while scrolling up
                when (articles.loadState.append) { //pagination
                    is LoadState.Error -> {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                (articles.loadState.append as LoadState.Error).error.localizedMessage?.let {
                                    Text(
                                        text = it,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                    is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Pagination Loading")

                                CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
                            }
                        }
                    }
                    else -> {}
                }
            }
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(horizontal = 8.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
fun ItemCompose(
    article: Article,
    onClick: () -> Unit,
    onNewsSourceClick: () -> Unit,
    isExpandedArticle: Boolean
) {
    val cornerShapeRadius = 16.dp
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .animateContentSize() //provide animation while content size changes
        ,
        elevation = 8.dp,
        shape = RoundedCornerShape(cornerShapeRadius)
    ) {
        var isImageLoading by remember { mutableStateOf(false) }

        val painter = rememberAsyncImagePainter(
            model = article.urlToImage
        )

        isImageLoading = when (painter.state) {
            is AsyncImagePainter.State.Loading -> true
            else -> false
        }

        ConstraintLayout {
            val (image, name, title, description, authorName, publishDate) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(name.bottom, margin = 8.dp)
                    }, contentAlignment = Alignment.Center
            ) {
                if (isImageLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(all = 8.dp),
                        color = MaterialTheme.colors.primary,
                    )
                }
                Image(
                    painter = painter, contentDescription = null, modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )
            }

            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onSurface,
                        shape = RoundedCornerShape(cornerShapeRadius)
                    )
                    .padding(5.dp)
                    .constrainAs(name) {
                        end.linkTo(parent.end, margin = 5.dp)
                        top.linkTo(parent.top, margin = 5.dp)
                    }
                    .clickable(onClick = onNewsSourceClick),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = article.source?.name.orEmpty(),
                    color = MaterialTheme.colors.surface,
                    style = MaterialTheme.typography.caption
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = "news source link",
                    tint = MaterialTheme.colors.surface
                )
                Spacer(modifier = Modifier.width(5.dp))

            }


            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .constrainAs(title) {
                        linkTo(
                            start = image.end,
                            startMargin = 8.dp,
                            end = parent.end,
                            endMargin = 5.dp,
                            bias = 0.0f
                        )
                        top.linkTo(image.top)
                        width = Dimension.fillToConstraints
                    },
                text = article.title.orEmpty(),
                style = MaterialTheme.typography.subtitle1,
                maxLines = if (isExpandedArticle) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = article.description.orEmpty(),
                modifier = Modifier.constrainAs(description) {
                    linkTo(start = title.start, end = parent.end, endMargin = 5.dp, bias = 0.0f)
                    top.linkTo(title.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
                style = MaterialTheme.typography.subtitle2,
                maxLines = if (isExpandedArticle) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "- " + article.author,
                modifier = Modifier.constrainAs(authorName) {
                    linkTo(
                        start = parent.start,
                        startMargin = 16.dp,
                        end = publishDate.start,
                        endMargin = 16.dp,
                        bias = 1.0f
                    )
                    linkTo(
                        top = description.bottom,
                        topMargin = 8.dp,
                        bottom = parent.bottom,
                        bottomMargin = 16.dp,
                        bias = 1.0f
                    )
                },
                style = MaterialTheme.typography.caption
            )

            Text(
                text = "- " + article.publishedAt?.convertToAnotherDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    "EEE, MMM d, ''yy"
                ),
                modifier = Modifier.constrainAs(publishDate) {
                    end.linkTo(parent.end, margin = 16.dp)
                    linkTo(
                        top = description.bottom,
                        topMargin = 8.dp,
                        bottom = parent.bottom,
                        bottomMargin = 16.dp,
                        bias = 1.0f
                    )
                },
                style = MaterialTheme.typography.caption
            )

        }


    }
}