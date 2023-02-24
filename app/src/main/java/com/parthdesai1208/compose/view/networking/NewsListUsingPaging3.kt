package com.parthdesai1208.compose.view.networking

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.model.networking.paging3.Article
import com.parthdesai1208.compose.model.networking.paging3.Source
import com.parthdesai1208.compose.utils.convertToAnotherDateFormat
import com.parthdesai1208.compose.utils.openInChromeCustomTab
import com.parthdesai1208.compose.viewmodel.networking.paging3.NewsListViewModel
import kotlinx.coroutines.launch

const val PageSize = 10

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NewsListUsingPaging3(viewModel: NewsListViewModel) {
    val articles = viewModel.getNews().collectAsLazyPagingItems()
    var isExpandedArticle by rememberSaveable { mutableStateOf<String?>("") }
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val tapToUpVisibility by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 2 && !listState.isScrollInProgress
        }
    }

    Surface {
        Box {
            LazyColumn(state = listState) {
                items(items = articles, key = { it.url }) {
                    it?.let {
                        ItemNews(it, onClick = {
                            isExpandedArticle = if (isExpandedArticle == it.url) null else it.url
                        }, isExpandedArticle = isExpandedArticle == it.url,
                            onNewsSourceClick = {
                                context.openInChromeCustomTab(it.url)
                            })
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
                        contentDescription = "Go Up",
                        tint = MaterialTheme.colors.surface
                    )
                }
            }
        }
    }
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemNews(
    @PreviewParameter(FakeArticle::class) it: Article,
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

            com.bumptech.glide.integration.compose.GlideImage(
                model = it.urlToImage, contentDescription = null,
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start, margin = 16.dp)
                        top.linkTo(name.bottom, margin = 8.dp)
                    }
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )


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
                    text = it.source.name,
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
                text = it.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = if (isExpandedArticle) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = it.description,
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
                text = "- " + it.author,
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
                text = "- " + it.publishedAt.convertToAnotherDateFormat(
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
