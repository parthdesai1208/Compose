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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.networking.paging3.Article
import com.parthdesai1208.compose.model.networking.paging3.Source
import com.parthdesai1208.compose.utils.BuildTopBarWithScreen
import com.parthdesai1208.compose.utils.Constant.BLANK_SPACE
import com.parthdesai1208.compose.utils.Constant.DASH
import com.parthdesai1208.compose.utils.Constant.DATE_TIME_INPUT
import com.parthdesai1208.compose.utils.Constant.DATE_TIME_OUTPUT
import com.parthdesai1208.compose.utils.Constant.PAGING_API_FIRST_QUERY
import com.parthdesai1208.compose.utils.convertToAnotherDateFormat
import com.parthdesai1208.compose.utils.isOnline
import com.parthdesai1208.compose.utils.openInChromeCustomTab
import com.parthdesai1208.compose.viewmodel.networking.paging3.NewsListViewModel
import kotlinx.coroutines.launch


@Composable
private fun NewsListUsingPaging3Preview() {
    NewsListUsingPaging3(viewModel = viewModel(), rememberNavController())
}

@Composable
fun NewsListUsingPaging3(viewModel: NewsListViewModel, navHostController: NavHostController) {
    val context = LocalContext.current
    val articles = viewModel.getNews(PAGING_API_FIRST_QUERY, context).collectAsLazyPagingItems()
    var isExpandedArticle by rememberSaveable { mutableStateOf<String?>("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var isSnackBarDisplayed by rememberSaveable { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

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
                items(
                    count = articles.itemCount,
                    key = articles.itemKey { it.url!! }) { index ->
                    val item = articles[index]
                    item?.let { article ->
                        article.url?.let { url ->
                            ItemNews(article, onClick = {
                                isExpandedArticle = if (isExpandedArticle == url) null else url
                            }, isExpandedArticle = isExpandedArticle == url,
                                onNewsSourceClick = {
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
                                    },
                                        successBlock = {
                                            context.openInChromeCustomTab(url)
                                        })
                                })
                        }
                    }
                }

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
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = stringResource(R.string.first_time_loading)
                                )

                                CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
                            }
                        }
                    }

                    else -> {}
                }

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

class FakeArticle : PreviewParameterProvider<Article> {
    override val values: Sequence<Article>
        get() = sequenceOf(
            Article(
                author = "Khamosh Pathak",
                content = "asdas",
                description = "A horizontal list of tabs worked a decade ago, when we weren’t all living in our web browsers. These days, however, the browser has turned into its own mini-OS, with most of our work and play coming through this window to the internet. Companies like Google a…",
                publishedAt = "2023-02-07T17:00:00Z",
                source = Source(id = "0", name = "Lifehacker.com"),
                title = "This Mac-Only Web Browser Turns the Internet Into Slack",
                url = "https://lifehacker.com/this-mac-only-web-browser-turns-the-internet-into-slack-1850081492",
                urlToImage = "https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/4d6e35d7d5a4a081925b9b83c668fd18.png"
            )
        )
}

@Preview
@Composable
private fun ItemNewsPreview() {
    ItemNews(it = Article(
        author = "Khamosh Pathak".repeat(1),
        content = "asdas",
        description = "A horizontal list of tabs worked a decade ago, when we weren’t all living in our web browsers. These days, however, the browser has turned into its own mini-OS, with most of our work and play coming through this window to the internet. Companies like Google a…",
        publishedAt = "2023-02-07T17:00:00Z",
        source = Source(id = "0", name = "Lifehacker.com"),
        title = "This Mac-Only Web Browser Turns the Internet Into Slack".repeat(2),
        url = "https://lifehacker.com/this-mac-only-web-browser-turns-the-internet-into-slack-1850081492",
        urlToImage = "https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/4d6e35d7d5a4a081925b9b83c668fd18.png"
    ), onClick = {}, isExpandedArticle = true, onNewsSourceClick = {})
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemNews(
    it: Article,
    onClick: () -> Unit,
    isExpandedArticle: Boolean,
    onNewsSourceClick: () -> Unit
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

        ConstraintLayout {
            val (image, name, title, description, authorName, publishDate) = createRefs()
            //region image
            GlideImage(
                model = it.urlToImage,
                contentDescription = stringResource(
                    R.string.image_for_the_article,
                    it.title.orEmpty()
                ),
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start, margin = 16.dp)
                        linkTo(
                            top = parent.top,
                            topMargin = 32.dp,
                            bottom = parent.bottom,
                            bottomMargin = 16.dp,
                            bias = 0.0f,
                        )
                    }
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.cloudy),
                failure = placeholder(R.drawable.placeholder_1_1),
            )
            //endregion
            //region top-right clickable link
            it.url?.let { _ ->
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
                    it.source?.let { source ->
                        source.name?.let { name ->
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
                }
            }
            //endregion
            //region title
            it.title?.let { _ ->
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
                            top.linkTo(name.bottom, margin = 8.dp, goneMargin = 8.dp)
                            width = Dimension.fillToConstraints
                        },
                    text = it.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = if (isExpandedArticle) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            //endregion
            //region description
            it.description?.let { _ ->
                Text(
                    text = it.description,
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(description) {
                            linkTo(
                                start = image.end,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 5.dp,
                                bias = 0.0f
                            )
                            top.linkTo(
                                if (it.title.isNullOrEmpty()) name.bottom else title.bottom,
                                margin = 8.dp,
                                goneMargin = 8.dp
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
                        if (it.author.isNullOrEmpty()) stringResource(id = R.string.unknown) else it.author,
                modifier = Modifier
                    .constrainAs(authorName) {
                        linkTo(
                            start = parent.start,
                            startMargin = 8.dp,
                            end = publishDate.start,
                            endMargin = 8.dp,
                            bias = 1.0f,
                        )
                        linkTo(
                            top = if (it.description.isNullOrEmpty()) title.bottom else description.bottom,
                            topMargin = 8.dp,
                            bottom = parent.bottom,
                            bottomMargin = 16.dp,
                            bias = 1.0f
                        )
                    },
                style = MaterialTheme.typography.caption,
                maxLines = 2,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
            )
            //endregion
            //region published date
            it.publishedAt?.let { _ ->
                Text(
                    text = DASH + BLANK_SPACE + it.publishedAt.convertToAnotherDateFormat(
                        DATE_TIME_INPUT,
                        DATE_TIME_OUTPUT
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .constrainAs(publishDate) {
                            end.linkTo(parent.end, margin = 16.dp)
                            linkTo(
                                top = if (it.description.isNullOrEmpty()) title.bottom else description.bottom,
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
