@file:OptIn(ExperimentalAnimationApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.SearchBarViewModel
import kotlinx.coroutines.delay


@Composable
fun SearchBar(vm: SearchBarViewModel) {
    SearchBarForVMInit(vm.searchBarItems, onSearch = { vm.search(it) })
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
private fun SearchBarForVMInit(list: List<String>, onSearch: (String) -> Unit) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        TextField(
            value = searchQuery, onValueChange = {
                searchQuery = it
                onSearch(searchQuery)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }, /*colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),*/textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            placeholder = {
                AnimatedPlaceholder(
                    hints = listOf(
                        "Search",
                        "Type 'India'",
                        "Type 'Italy'",
                        "Search by country name"
                    )
                )
                /*Text(
          text = stringResource(id = R.string.placeholder_search),
          color = MaterialTheme.colors.onSurface
      )*/
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
                .padding(all = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(searchQuery)
                keyboardController?.hide()
            })
        )

        LazyColumn(contentPadding = PaddingValues(all = 16.dp), modifier = Modifier.fillMaxSize()) {
            items(items = list) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(5.dp),
                    color = MaterialTheme.colors.onSurface
                )
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
@Composable
fun PreViewSearchBar() {
    ComposeTheme {
        SearchBar(androidx.lifecycle.viewmodel.compose.viewModel())
    }
}

@Composable
fun AnimatedPlaceholder(
    hints: List<String>,
    textColor: Color = MaterialTheme.colors.onSurface,
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
            text = str,
            color = textColor,
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