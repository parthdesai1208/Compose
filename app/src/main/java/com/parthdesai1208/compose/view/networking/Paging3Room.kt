package com.parthdesai1208.compose.view.networking

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.networking.paging3withroom.Article
import com.parthdesai1208.compose.model.networking.paging3withroom.NewsListApiService
import com.parthdesai1208.compose.model.networking.paging3withroom.Paging3WithRoomDataBase
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Constant.BLANK_SPACE
import com.parthdesai1208.compose.utils.Constant.DASH
import com.parthdesai1208.compose.utils.Constant.DATE_TIME_INPUT
import com.parthdesai1208.compose.utils.Constant.DATE_TIME_OUTPUT
import com.parthdesai1208.compose.utils.Constant.PAGING_API_FIRST_QUERY
import com.parthdesai1208.compose.utils.convertToAnotherDateFormat
import com.parthdesai1208.compose.utils.isOnline
import com.parthdesai1208.compose.utils.openInChromeCustomTab
import com.parthdesai1208.compose.viewmodel.networking.Paging3WithRoomDBVM
import kotlinx.coroutines.launch

@Composable
fun Paging3Room(navHostController: NavHostController) {
    val context = LocalContext.current
    val viewModel = Paging3WithRoomDBVM(
        newsApiService = NewsListApiService.getInstance(context),
        paging3WithRoomDataBase = Paging3WithRoomDataBase.getDatabase(context)
    )
    Paging3RoomWrapper(viewModel, navHostController)
}

@Composable
private fun Paging3RoomWrapper(
    viewModel: Paging3WithRoomDBVM,
    navHostController: NavHostController
) {
    val articles = viewModel.getArticle(query = PAGING_API_FIRST_QUERY)
        .collectAsLazyPagingItems()
    var isExpandedArticle by rememberSaveable { mutableStateOf<String?>("") }
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var isSnackBarDisplayed by rememberSaveable { mutableStateOf(false) }

    val tapToUpVisibility by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 2 && !listState.isScrollInProgress
        }
    }

    val launcherForInternetConnectivity =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {})

    BuildTopBarWithScreen(screen = {
        Box {
            LazyColumn(state = listState) {
                items(count = articles.itemCount) { index ->
                    val item = articles[index]
                    item?.let { article ->
                        article.url?.let { url ->
                            ItemCompose(article = article, onClick = {
                                isExpandedArticle = if (isExpandedArticle == url) null else url
                            }, onNewsSourceClick = {
                                context.isOnline(failBlock = {
                                    if (!isSnackBarDisplayed) {
                                        isSnackBarDisplayed = true
                                        coroutineScope.launch {
                                            val snack = snackBarHostState.showSnackbar(
                                                message = context.getString(R.string.no_internet_available),
                                                actionLabel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) context.getString(
                                                    R.string.check
                                                ) else null,
                                                duration = SnackbarDuration.Long,
                                            )
                                            if (snack == SnackbarResult.Dismissed) isSnackBarDisplayed =
                                                false
                                            if (snack == SnackbarResult.ActionPerformed) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                                    launcherForInternetConnectivity.launch(
                                                        Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                                                    )
                                                    isSnackBarDisplayed = false
                                                }
                                            }
                                        }
                                    }
                                }, successBlock = {
                                    context.openInChromeCustomTab(url)
                                })
                            }, isExpandedArticle = isExpandedArticle == url)
                        }
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
                                    text = stringResource(id = R.string.first_time_loading)
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
                                Text(text = stringResource(R.string.pagination_loading))

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
            AnimatedVisibility(
                visible = tapToUpVisibility,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(all = 10.dp),
                enter = scaleIn(), exit = scaleOut()
            ) {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }, backgroundColor = MaterialTheme.colors.onSurface) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_up),
                        contentDescription = stringResource(R.string.go_on_top_of_the_list),
                        tint = MaterialTheme.colors.surface
                    )
                }
            }
        }
    },
        onBackIconClick = {
            navHostController.popBackStack()
        })
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
            //region image
            Box(
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(name.bottom, margin = 8.dp)
                    }, contentAlignment = Alignment.Center
            ) {
                if (isImageLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(all = 8.dp),
                        color = MaterialTheme.colors.onSurface,
                    )
                }
                Image(
                    painter = painter, contentDescription = stringResource(
                        R.string.image_for_the_article,
                        article.title.orEmpty()
                    ), modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )
            }
            //endregion
            //region  top-right clickable link
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
                article.source?.name?.let { name ->
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = name,
                        color = MaterialTheme.colors.surface,
                        style = MaterialTheme.typography.caption
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = stringResource(R.string.click_on_link, name),
                        tint = MaterialTheme.colors.surface
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
            //endregion
            //region title
            article.title?.let {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .constrainAs(title) {
                            linkTo(
                                start = image.end,
                                startMargin = 8.dp,
                                end = parent.end,
                                endMargin = 5.dp,
                                bias = 0.0f,
                            )
                            top.linkTo(image.top)
                            width = Dimension.fillToConstraints
                        },
                    text = article.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = if (isExpandedArticle) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            //endregion
            //region description
            article.description?.let { _ ->
                Text(
                    text = article.description,
                    modifier = Modifier.constrainAs(description) {
                        linkTo(
                            start = title.start, end = parent.end,
                            startMargin = 8.dp,
                            endMargin = 5.dp, bias = 0.0f
                        )
                        top.linkTo(
                            if (article.title.isNullOrEmpty()) name.bottom else title.bottom,
                            margin = 8.dp
                        )
                        width = Dimension.fillToConstraints
                    },
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = if (isExpandedArticle) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            //endregion
            //region author
            Text(
                text = DASH + BLANK_SPACE +
                        if (article.author.isNullOrEmpty()) stringResource(id = R.string.unknown) else article.author,
                modifier = Modifier.constrainAs(authorName) {
                    linkTo(
                        start = parent.start,
                        startMargin = 16.dp,
                        end = publishDate.start,
                        endMargin = 16.dp,
                        bias = 1.0f,
                    )
                    linkTo(
                        top = if (article.description.isNullOrEmpty()) title.bottom else description.bottom,
                        topMargin = 8.dp,
                        bottom = parent.bottom,
                        bottomMargin = 16.dp,
                        bias = 1.0f,
                    )
                },
                style = MaterialTheme.typography.caption,
                maxLines = 2,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
            )
            //endregion
            //region publish Date
            article.publishedAt?.let { at ->
                Text(
                    text = DASH + BLANK_SPACE + at.convertToAnotherDateFormat(
                        DATE_TIME_INPUT,
                        DATE_TIME_OUTPUT
                    ),
                    modifier = Modifier.constrainAs(publishDate) {
                        end.linkTo(parent.end, margin = 16.dp)
                        linkTo(
                            top = if (article.description.isNullOrEmpty()) title.bottom else description.bottom,
                            topMargin = 8.dp,
                            bottom = parent.bottom,
                            bottomMargin = 16.dp,
                            bias = 1.0f,
                        )
                    },
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                )
            }
            //endregion
        }


    }
}