@file:OptIn(ExperimentalAnimationApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parthdesai1208.compose.utils.Phone
import com.parthdesai1208.compose.utils.ToolBarWithIconAndTitle
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.SearchBarViewModel
import kotlinx.coroutines.delay


@Composable
fun SearchBar(navHostController: NavHostController, vm: SearchBarViewModel) {
    SearchBarForVMInit(navHostController, vm.searchBarItems, onSearch = { vm.search(it) })
}

@Composable
private fun SearchBarForVMInit(
    navHostController: NavHostController,
    list: List<String>,
    onSearch: (String) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Surface {
        Column {
            ToolBarWithIconAndTitle(
                screenTitle = "",
                onBackArrowClick = { navHostController.popBackStack() }
            )
            Column {
                TextField(
                    value = searchQuery, onValueChange = {
                        searchQuery = it
                        onSearch(searchQuery)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    placeholder = {
                        AnimatedPlaceholder(
                            hints = listOf(
                                "Search",
                                "Type 'India'",
                                "Type 'Italy'",
                                "Search by country name"
                            )
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = searchQuery != "",
                            enter = scaleIn(),
                            exit = scaleOut()
                        ) {
                            IconButton(onClick = {
                                searchQuery = ""
                                onSearch(searchQuery)
                                keyboardController?.hide()
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.Top)
                        .heightIn(min = 56.dp)
                        .padding(all = 16.dp)
                        .focusRequester(focusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearch(searchQuery)
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    })
                )

                LazyColumn(
                    contentPadding = PaddingValues(all = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = list) { item ->
                        Text(
                            text = item,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(name = "Light", showSystemUi = true)
@Preview(name = "Dark", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(
    name = "landscape",
    showSystemUi = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 1024,
    heightDp = 360
)

@Phone
@Composable
fun PreViewSearchBar() {
    ComposeTheme {
        SearchBar(rememberNavController(), androidx.lifecycle.viewmodel.compose.viewModel())
    }
}

@Composable
fun AnimatedPlaceholder(
    hints: List<String>
) {
    val iterator = hints.listIterator()

    val target by produceState(initialValue = hints.first()) {
        //doWhenHasNextOrPrevious = for infinite loop
        iterator.doWhenHasNextOrPrevious {
            value = it
        }
    }

    AnimatedContent(
        targetState = target,
        transitionSpec = { ScrollAnimation() }
    ) { str ->
        Text(
            text = str
        )
    }
}

suspend fun <T> ListIterator<T>.doWhenHasNextOrPrevious(
    delayMills: Long = 3000,
    doWork: suspend (T) -> Unit
) {
    while (hasNext() || hasPrevious()) {
        while (hasNext()) {
            delay(delayMills)
            doWork(next())
        }
        while (hasPrevious()) {
            delay(delayMills)
            doWork(previous())
        }
    }
}

object ScrollAnimation {
    operator fun invoke(): ContentTransform {
        return slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = tween()
        ) + fadeIn() with slideOutVertically(
            targetOffsetY = { -50 },
            animationSpec = tween()
        ) + fadeOut()
    }
}