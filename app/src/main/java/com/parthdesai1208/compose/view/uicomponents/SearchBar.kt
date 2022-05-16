package com.parthdesai1208.compose.view.uicomponents

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.view.theme.ComposeTheme
import com.parthdesai1208.compose.viewmodel.SearchBarViewModel


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
                Text(
                    text = stringResource(id = R.string.placeholder_search),
                    color = MaterialTheme.colors.onSurface
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = searchQuery != "", enter = scaleIn(),exit = scaleOut()){
                    IconButton(onClick = {
                        searchQuery = ""
                        onSearch(searchQuery)
                        keyboardController?.hide()
                    }){
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

